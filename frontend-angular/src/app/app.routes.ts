// src/app/app.routes.ts

import { Routes } from '@angular/router';
import { LayoutComponent } from './layout/layout.component';

export const APP_ROUTES: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      {
        path: 'dashboard',
        loadComponent: () =>
          import('./pages/dashboard/dashboard.component')
            .then(m => m.DashboardComponent),
      },
      {
        path: 'motos',
        loadComponent: () =>
          import('./pages/motos/motos.component')
            .then(m => m.MotosComponent),
      },
      {
        path: 'clientes',
        loadComponent: () =>
          import('./pages/clientes/clientes.component')
            .then(m => m.ClientesComponent),
      },
    ],
  },
  { path: '**', redirectTo: '' },
];
