# MotoTrack CRM — Angular 17+

Dashboard CRM profesional para gestión de motocicletas.  
Arquitectura **standalone components**, sin NgModules, con signals y BehaviorSubjects.

## Stack

| Capa | Tecnología |
|---|---|
| Framework | Angular 17 (Standalone) |
| Lenguaje | TypeScript 5.4 strict |
| State | BehaviorSubject + Signals |
| Forms | ReactiveFormsModule |
| HTTP | HttpClient + proxy dev |
| Routing | Lazy loading |
| Estilos | CSS puro con design tokens |

## Requisitos previos

- Node.js 18+
- Backend Quarkus corriendo en `http://localhost:8860`

## Instalación

```bash
npm install
npm start        # http://localhost:4200
```

## Build producción

```bash
npm run build:prod
# → dist/mototrack-crm/
```

## Estructura

```
src/app/
├── core/
│   ├── models/          # Moto, Toast interfaces
│   └── services/        # MotoService, ToastService
├── layout/
│   ├── layout/          # Shell con signal(sidebarOpen)
│   ├── sidebar/         # routerLink + routerLinkActive
│   └── topbar/          # API status + fecha reactiva
├── pages/
│   ├── dashboard/       # Stats + tabla reciente
│   ├── motos/           # CRUD + búsqueda + sort + modal
│   └── clientes/        # Placeholder (próximamente)
└── shared/
    ├── pipes/           # formatDate, colorHash
    └── components/      # Toast, MotoModal, StatCard
```

## API

| Método | Endpoint |
|---|---|
| GET  | `/kick/motos` |
| POST | `/kick/motos` |

El proxy (`proxy.conf.json`) redirige `/kick/*` → `http://localhost:8860` en desarrollo.
