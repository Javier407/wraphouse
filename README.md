# Wrap House Design — Sitio web

Sitio **estático** (HTML/CSS/JS, sin build) de Wrap House Design.
Producción: **https://wraphousedesign.com** (GitHub Pages).

---

## 📁 Estructura

```
Web/website/
├── index.html          # Todo el sitio (una sola página; estilos y JS embebidos)
├── designs.json        # Catálogo de diseños por modelo de moto (fuente de la galería)
├── CNAME               # Dominio de GitHub Pages: wraphousedesign.com  (NO borrar)
├── robots.txt          # SEO
├── sitemap.xml         # SEO
├── favicon.svg
├── images/             # Fotos, logos y videos (hero, PPF, diseños por modelo…)
│   ├── <modelo>/       # p. ej. gixxer-sf/, gixxer-naked/, apache-rtr/, dominar/
│   └── ppf/            # fotos de instalación PPF
└── scripts/
    ├── deploy.sh              # Publica el sitio en producción (ver «Despliegue»)
    └── generate-manifest.ps1  # Regenera designs.json escaneando images/<modelo>/
```

> El sitio no tiene framework ni paso de compilación: `index.html` se sirve tal cual.

---

## 🌿 Ramas

| Rama          | Rol                                                                        |
|---------------|----------------------------------------------------------------------------|
| `main`        | **Fuente de verdad.** Aquí vive el código del sitio (`Web/website/`).       |
| `gh-pages`    | **Salida generada** que sirve GitHub Pages. Nunca se edita a mano; se regenera con el script de deploy. |
| `feat/…` `fix/…` `content/…` `chore/…` | Trabajo en curso; se integran a `main` por Pull Request. |

**Nomenclatura de ramas** (kebab-case, prefijo por tipo):

- `feat/<slug>` — nueva funcionalidad o sección (`feat/ppf-detailing-tabs`)
- `fix/<slug>` — corrección (`fix/hero-video-loop`)
- `content/<slug>` — cambios de contenido: diseños, precios, textos, fotos (`content/nuevos-disenos-apache`)
- `chore/<slug>` — mantenimiento, docs, config (`chore/deploy-docs`)

---

## ✍️ Convención de commits (Conventional Commits)

```
<tipo>(web): <resumen en imperativo y minúscula>
```

Tipos: `feat`, `fix`, `content`, `style`, `refactor`, `chore`, `docs`.
El scope `web` distingue estos cambios de los de `backend-quarkus` / `frontend-angular`.

Ejemplos:
- `feat(web): agregar sección PPF & Detailing con pestañas`
- `fix(web): centrar números de los pasos del proceso`
- `content(web): actualizar catálogo Gixxer Naked y quitar precios`

---

## 🔀 Flujo de trabajo (push & merge)

1. Crea una rama desde `main`: `git switch -c feat/mi-cambio`
2. Trabaja y commitea con la convención de arriba.
3. Sube la rama y abre un **Pull Request** a `main`:
   ```bash
   git push -u origin feat/mi-cambio
   ```
4. Revisa y haz **merge** del PR a `main` (título del PR = commit convencional; preferir *squash merge* para mantener el historial de `main` limpio).
5. **Despliega** (ver abajo). Publicar es un paso explícito y aparte del merge.

---

## 🚀 Despliegue a producción

GitHub Pages sirve `wraphousedesign.com` desde la rama **`gh-pages`**, cuya raíz es el
contenido de `Web/website/`. Publicar = copiar el estado actual de `Web/website` (en `main`)
a `gh-pages`. Se hace con un solo comando:

```bash
bash Web/website/scripts/deploy.sh
```

- Publica desde `main` por defecto.
- Regenera `gh-pages` desde cero (no editar esa rama a mano).
- El sitio se actualiza en 1–2 min. Si ves la versión vieja: **Ctrl+F5** (caché del CDN).

> Requisito: haber hecho merge/push de tus cambios a `main` primero.

---

## 🖥️ Correr el sitio en local

No hay servidor de dev. Sirve la carpeta como estática. En Windows (sin Node/Python),
un servidor mínimo en PowerShell:

```powershell
$l = New-Object System.Net.HttpListener; $l.Prefixes.Add('http://localhost:8080/'); $l.Start()
Write-Host 'http://localhost:8080/'
# (usar un servidor estático real para producción; esto es solo para previsualizar)
```

O abre `index.html` con cualquier servidor estático (`live-server`, extensión de VS Code, etc.).

---

## 🎨 Tareas comunes

- **Agregar diseños:** copia el `.png` a `images/<modelo>/`, añade la entrada en `designs.json`
  (o corre `scripts/generate-manifest.ps1`), luego commitea con `content(web): …` y despliega.
- **Videos de TikTok:** se embeben con `https://www.tiktok.com/player/v1/<ID>` (iframe 9:16).
  Solo se reproducen en el sitio real/HTTPS, no en previsualizaciones locales.
- **Tipografía:** display `Saira Condensed`, cuerpo `Sora`, datos/precios `Rajdhani`
  (variables `--display`, `--body`, `--data` en el `:root` de `index.html`).
