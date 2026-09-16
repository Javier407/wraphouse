#!/usr/bin/env bash
#
# deploy.sh — Publica el sitio estático (Web/website) en producción.
#
# El sitio wraphousedesign.com se sirve con GitHub Pages desde la rama `gh-pages`
# (su raíz = el contenido de Web/website). `main` es la fuente de verdad; `gh-pages`
# es salida GENERADA: nunca se edita a mano, siempre se regenera con este script.
#
# Uso:
#   bash Web/website/scripts/deploy.sh            # publica desde main (por defecto)
#   bash Web/website/scripts/deploy.sh <rama>     # publica desde otra rama (avanzado)
#
set -euo pipefail

ROOT="$(git rev-parse --show-toplevel)"
cd "$ROOT"

SRC_BRANCH="${1:-main}"
PREFIX="Web/website"

echo "→ Actualizando referencias remotas…"
git fetch origin "$SRC_BRANCH" --quiet

# Aviso si hay cambios sin commitear en el sitio (no se publicarían).
if ! git diff --quiet -- "$PREFIX" || ! git diff --cached --quiet -- "$PREFIX"; then
  echo "⚠  Hay cambios sin commitear en $PREFIX. Commitéalos y súbelos a $SRC_BRANCH antes de desplegar."
  exit 1
fi

echo "→ Generando snapshot de $PREFIX desde '$SRC_BRANCH'…"
SPLIT="$(git subtree split --prefix="$PREFIX" "$SRC_BRANCH")"

echo "→ Publicando $SPLIT en la raíz de gh-pages…"
git push origin "$SPLIT:gh-pages" --force

echo ""
echo "✔ Deploy enviado. GitHub Pages actualiza wraphousedesign.com en 1–2 min."
echo "  Si ves la versión anterior, recarga con Ctrl+F5 (caché de CDN)."
