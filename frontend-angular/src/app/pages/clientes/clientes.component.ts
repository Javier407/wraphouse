// src/app/pages/clientes/clientes.component.ts

import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'app-clientes',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <div class="page-header">
      <div>
        <h1 class="page-title">Clientes</h1>
        <p class="page-subtitle">Gestión de propietarios de motocicletas</p>
      </div>
    </div>

    <div class="coming-card">
      <div class="coming-icon">
        <svg viewBox="0 0 48 48" fill="none">
          <circle cx="24" cy="24" r="20" stroke="currentColor" stroke-width="1.5"/>
          <circle cx="24" cy="18" r="6"  stroke="currentColor" stroke-width="1.5"/>
          <path d="M8 40c0-8 7.163-14 16-14s16 6 16 14"
                stroke="currentColor" stroke-width="1.5" stroke-linecap="round"/>
        </svg>
      </div>
      <h2>Módulo en desarrollo</h2>
      <p>El módulo de clientes estará disponible en la próxima versión del sistema.</p>
      <span class="badge">Próximamente</span>
    </div>
  `,
  styles: [`
    :host { display: contents; }

    .coming-card {
      background: var(--bg-card);
      border: 1px solid var(--border);
      border-radius: var(--radius-xl);
      padding: 64px 40px;
      text-align: center;
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 14px;
    }

    .coming-icon {
      width: 72px; height: 72px;
      color: var(--text-muted);
      margin-bottom: 4px;
    }
    .coming-icon svg { width: 100%; height: 100%; }

    h2 {
      font-family: 'Syne', sans-serif;
      font-size: 1.3rem;
      font-weight: 800;
      color: var(--text-primary);
    }

    p {
      font-size: 0.85rem;
      color: var(--text-secondary);
      max-width: 360px;
      line-height: 1.6;
    }

    .badge {
      background: rgba(251, 191, 36, 0.1);
      border: 1px solid rgba(251, 191, 36, 0.25);
      color: var(--amber-400);
      font-size: 0.7rem;
      font-weight: 700;
      letter-spacing: 0.1em;
      text-transform: uppercase;
      padding: 4px 14px;
      border-radius: 20px;
      margin-top: 4px;
    }
  `],
})
export class ClientesComponent {}
