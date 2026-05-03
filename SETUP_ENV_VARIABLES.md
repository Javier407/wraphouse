# ⚙️ CONFIGURACIÓN DE VARIABLES DE ENTORNO - WRAPHOUSE CRM

## ✅ ARQUITECTURA IMPLEMENTADA

Se han configurado dos archivos de aplicación para soportar múltiples entornos:

### 1. **application.yml** ✅ ACTUALIZADO
- Configuración con variables de entorno + valores por defecto
- Soporta producción con ambiente variables
- Sintaxis: `${VAR_NAME:default_value}`
- CORS configurado correctamente para Quarkus

### 2. **application-dev.yml** ✅ CREADO
- Configuración específica para desarrollo en IntelliJ
- Contiene credenciales reales para desarrollo local
- Quarkus carga automáticamente en modo dev
- ⚠️ **NUNCA** commitar credenciales reales

### 3. **.gitignore** ✅ CREADO
- Excluye: application-dev.yml, .env, credenciales, etc.

---

## 🚀 CÓMO USAR EN DESARROLLO

### Para Desarrollo Local (IntelliJ)

**Está automático - ¡Sin configuración extra!**

1. Abre IntelliJ
2. Ve a `backend-quarkus/`
3. Ejecuta: `./mvnw quarkus:dev`
4. Quarkus usa `application-dev.yml` automáticamente
5. ✅ Aplicación inicia con credenciales locales

```bash
cd backend-quarkus
./mvnw quarkus:dev

# Esperado ver:
# [Quarkus] WrapHouse CRM API 1.0.0-SNAPSHOT started in XXXms
# [Quarkus] Listening on: http://localhost:8860
```

---

## 🌍 PARA PRODUCCIÓN (Con Variables de Entorno)

Cuando despliegues a producción, establece estas variables en tu servidor:

```bash
# Base de datos
export DB_HOST=tu-servidor-postgres.com
export DB_PORT=5432
export DB_NAME=tu_base_datos
export DB_USERNAME=tu_usuario
export DB_PASSWORD=tu_password_seguro

# CORS - Origins permitidos
export CORS_ORIGINS=https://tudominio.com,https://www.tudominio.com
```

---

## 📋 CHECKLIST

- [x] `application.yml` con variables de entorno
- [x] `application-dev.yml` para desarrollo
- [x] Quarkus CORS correctamente configurado
- [x] Maven compiler plugin arreglado (sin duplicados)
- [ ] Ejecutar `./mvnw quarkus:dev` y verificar que inicia
- [ ] Acceder a http://localhost:8860/kick/q/swagger-ui

---

## ⚠️ SEGURIDAD EN GIT

### ANTES de hacer commit:

```bash
git status

# Verificar que estos archivos NO aparecen:
# ❌ application-dev.yml (NO subir)
# ❌ .env (NO subir)
# ✅ application.yml (sí puede subir - sin credenciales reales)
```

### Si accidentalmente subiste credenciales a GitHub:

```bash
# Regenera credenciales inmediatamente
# Las que se ven en GitHub están comprometidas

# Elimina del historial de Git
git rm --cached application-dev.yml .env
git commit -m "Remove credentials from tracking"
git push
```

---

## 🔄 PARA OTROS DESARROLLADORES

Cuando alguien clone el proyecto:

```bash
# 1. Clonar repo
git clone https://github.com/Javier407/wraphouse.git
cd wraphouse/backend-quarkus

# 2. Crear su propio archivo de desarrollo
cp src/main/resources/application.yml src/main/resources/application-dev.yml

# 3. Editar con SUS credenciales de desarrollo
nano src/main/resources/application-dev.yml

# 4. Listo - ejecutar
./mvnw quarkus:dev
```

---

## 📚 REFERENCIAS

- [Quarkus Configuration Guide](https://quarkus.io/guides/config)
- [Quarkus Profiles](https://quarkus.io/guides/config-reference#profiles)
- [CORS en Quarkus](https://quarkus.io/guides/http-reference#cors)
