# =============================================================
# generate-manifest.ps1
# Wrap House Design — Auto-generador de designs.json
# =============================================================
# USO:
#   cd website
#   .\scripts\generate-manifest.ps1
#
# QUE HACE:
#   1. Escanea todas las subcarpetas de images/
#   2. Si el modelo YA existe en designs.json → conserva nombres y tags personalizados
#   3. Si hay archivos NUEVOS → los agrega con nombre limpio generado
#   4. Si hay archivos que YA NO EXISTEN → los elimina automáticamente
#   5. Escribe designs.json actualizado
# =============================================================

$root       = Split-Path $PSScriptRoot -Parent
$imagesDir  = Join-Path $root "images"
$jsonPath   = Join-Path $root "designs.json"

Write-Host "`n🔍 Wrap House — Generando designs.json..." -ForegroundColor Cyan

# ── Cargar JSON existente ──────────────────────────────────
$existing = @{ models = @() }
if (Test-Path $jsonPath) {
  $existing = Get-Content $jsonPath -Raw | ConvertFrom-Json
  Write-Host "   Encontrado designs.json existente (v$($existing.version))" -ForegroundColor DarkGray
}

# Crear diccionario de modelos existentes para lookup rápido
$existingModels = @{}
foreach ($m in $existing.models) {
  $existingModels[$m.id] = $m
}

# ── Mapeo de labels para carpetas conocidas ────────────────
$knownLabels = @{
  "gixxer-sf"     = "GIXXER SF 150"
  "gixxer-naked"  = "GIXXER NAKED 150"
  "apache-rtr"    = "APACHE RTR 200"
  "dominar"       = "DOMINAR 400"
  "yamaha-r15"    = "YAMAHA R15 V3"
}

# ── Función: nombre de display desde filename ──────────────
function Get-DisplayName($filename) {
  $name = [System.IO.Path]::GetFileNameWithoutExtension($filename)
  # Quita prefijos de carpeta (gn01 → gn01, sf03 → sf03)
  # Reemplaza guiones y guiones bajos por espacios
  $name = $name -replace '[-_]', ' '
  # Title case
  $textInfo = (Get-Culture).TextInfo
  return $textInfo.ToTitleCase($name.ToLower())
}

# ── Función: tag automático desde nombre ──────────────────
function Get-AutoTag($name) {
  $n = $name.ToLower()
  if ($n -match 'red.?bull|racing|rtr|ecstar')        { return "Racing series"   }
  if ($n -match 'hayabusa|limited|le\b')               { return "Limited edition" }
  if ($n -match 'alpinestar|suzuki|sport')             { return "Sport series"    }
  if ($n -match 'tron|stealth|dark|shadow|bm\b|black') { return "Stealth series"  }
  if ($n -match 'studio|neon|phantom|center|centro')   { return "Studio series"   }
  return "Custom series"
}

# ── Escanear subcarpetas de images/ ───────────────────────
$imageFolders = Get-ChildItem -Path $imagesDir -Directory | Sort-Object Name
$newModels    = [System.Collections.Generic.List[object]]::new()
$stats        = @{ added = 0; removed = 0; kept = 0; models = 0 }

foreach ($folder in $imageFolders) {
  $modelId = $folder.Name   # ej: "gixxer-sf"

  # Archivos de imagen en la carpeta (png y webp)
  $imageFiles = Get-ChildItem -Path $folder.FullName -File |
    Where-Object { $_.Extension -in @('.png','.webp') } |
    Sort-Object Name

  if ($imageFiles.Count -eq 0) { continue }

  $stats.models++

  # Lookup existente
  $existingModel = $existingModels[$modelId]

  # Label: usa existente o genera desde mapping/nombre de carpeta
  $label = if ($existingModel -and $existingModel.label) {
    $existingModel.label
  } elseif ($knownLabels.ContainsKey($modelId)) {
    $knownLabels[$modelId]
  } else {
    ($modelId -replace '-', ' ').ToUpper()
  }

  # Construir diccionario de diseños existentes → { src → design }
  $existingDesigns = @{}
  if ($existingModel) {
    foreach ($d in $existingModel.designs) {
      $existingDesigns[$d.src] = $d
    }
  }

  # Construir lista de diseños nueva (solo archivos que existen en disco)
  $newDesigns = [System.Collections.Generic.List[object]]::new()
  foreach ($file in $imageFiles) {
    $src = "images/$modelId/$($file.Name)"

    if ($existingDesigns.ContainsKey($src)) {
      # Ya existe → conservar nombre y tag personalizados
      $newDesigns.Add($existingDesigns[$src])
      $stats.kept++
    } else {
      # Nuevo archivo → auto-generar nombre
      $displayName = Get-DisplayName $file.Name
      $tag         = Get-AutoTag $displayName
      $newDesigns.Add([PSCustomObject]@{
        src  = $src
        name = $displayName
        tag  = $tag
      })
      Write-Host "   + NUEVO: $src ($displayName)" -ForegroundColor Green
      $stats.added++
    }
  }

  # Detectar eliminados
  if ($existingModel) {
    foreach ($src in $existingDesigns.Keys) {
      $found = $newDesigns | Where-Object { $_.src -eq $src }
      if (-not $found) {
        Write-Host "   - ELIMINADO: $src" -ForegroundColor Red
        $stats.removed++
      }
    }
  }

  $newModels.Add([PSCustomObject]@{
    id          = $modelId
    label       = $label
    scan_folder = "images/$modelId"
    designs     = $newDesigns.ToArray()
  })
}

# ── También preservar diseños de root (centro1.png, centro2.png) ──
# Estos son manejados manualmente en designs.json y no se tocan
foreach ($m in $existing.models) {
  $matchingNew = $newModels | Where-Object { $_.id -eq $m.id }
  if ($matchingNew) {
    # Agregar entradas con src fuera de scan_folder (ej: images/centro1.png)
    $manualEntries = $m.designs | Where-Object {
      $d = $_
      -not ($d.src -match "^images/$($m.id)/")
    }
    if ($manualEntries) {
      $updatedDesigns = [System.Collections.Generic.List[object]]::new()
      foreach ($e in $manualEntries)   { $updatedDesigns.Add($e) }
      foreach ($e in $matchingNew.designs) { $updatedDesigns.Add($e) }
      $matchingNew.designs = $updatedDesigns.ToArray()
    }
  }
}

# ── Escribir designs.json ──────────────────────────────────
$output = [PSCustomObject]@{
  version = "1.0"
  updated = (Get-Date -Format "yyyy-MM-dd")
  note    = "Para agregar imagenes: copia el .png a images/<modelo>/, corre este script."
  models  = $newModels.ToArray()
}

$json = $output | ConvertTo-Json -Depth 10
[System.IO.File]::WriteAllText($jsonPath, $json, [System.Text.Encoding]::UTF8)

# ── Resumen ────────────────────────────────────────────────
Write-Host "`n✅ designs.json actualizado" -ForegroundColor Green
Write-Host "   Modelos : $($stats.models)"  -ForegroundColor White
Write-Host "   Nuevos  : $($stats.added)"   -ForegroundColor Green
Write-Host "   Eliminados: $($stats.removed)" -ForegroundColor Red
Write-Host "   Sin cambios: $($stats.kept)" -ForegroundColor DarkGray
Write-Host ""
