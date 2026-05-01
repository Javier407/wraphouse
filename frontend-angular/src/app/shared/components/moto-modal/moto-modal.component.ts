// src/app/shared/components/moto-modal/moto-modal.component.ts

import {
  ChangeDetectionStrategy, Component,
  EventEmitter, Input, Output
} from '@angular/core';
import { NgClass } from '@angular/common';
import { Moto } from '../../../core/models/moto.model';
import { FormatDatePipe } from '../../pipes/format-date.pipe';
import { ColorHashPipe } from '../../pipes/color-hash.pipe';

@Component({
  selector: 'app-moto-modal',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [NgClass, FormatDatePipe, ColorHashPipe],
  template: `
    <div
      class="overlay"
      [ngClass]="{ open: !!moto }"
      (click)="closed.emit()"
      role="dialog"
      aria-modal="true"
    >
      <div class="modal" (click)="$event.stopPropagation()">

        <div class="modal-header">
          <h3 class="modal-title">Detalle de Moto</h3>
          <button class="btn-close" (click)="closed.emit()" aria-label="Cerrar">
            <svg viewBox="0 0 16 16" fill="none">
              <path d="M3 3l10 10M13 3L3 13" stroke="currentColor" stroke-width="1.5" stroke-linecap="round"/>
            </svg>
          </button>
        </div>

        @if (moto) {
          <div class="modal-body">
            <div class="detail-item">
              <span class="label">ID</span>
              <span class="value">#{{ moto.id }}</span>
            </div>
            <div class="detail-item">
              <span class="label">Placa</span>
              <span class="value"><span class="placa-badge">{{ moto.placa }}</span></span>
            </div>
            <div class="detail-item">
              <span class="label">Marca</span>
              <span class="value">{{ moto.marca }}</span>
            </div>
            <div class="detail-item">
              <span class="label">Modelo</span>
              <span class="value">{{ moto.modelo }}</span>
            </div>
            <div class="detail-item">
              <span class="label">Color</span>
              <span class="value">
                <span class="color-cell">
                  <span class="color-dot" [style.background]="moto.color | colorHash"></span>
                  {{ moto.color || '—' }}
                </span>
              </span>
            </div>
            <div class="detail-item">
              <span class="label">Registro</span>
              <span class="value">{{ moto.fechaRegistro | formatDate:true }}</span>
            </div>
            <div class="detail-item full">
              <span class="label">Cliente Propietario</span>
              <span class="value">{{ moto.cliente }}</span>
            </div>
          </div>
        }

      </div>
    </div>
  `,
  styles: [`
    .overlay {
      position: fixed; inset: 0;
      background: rgba(0,0,0,0.7);
      backdrop-filter: blur(4px);
      display: flex; align-items: center; justify-content: center;
      z-index: 500; padding: 20px;
      opacity: 0; pointer-events: none;
      transition: opacity 250ms var(--ease);
    }
    .overlay.open { opacity: 1; pointer-events: all; }

    .modal {
      background: var(--bg-elevated);
      border: 1px solid var(--border-mid);
      border-radius: var(--radius-xl);
      width: 100%; max-width: 480px;
      box-shadow: var(--shadow-lg);
      transform: scale(0.95);
      transition: transform 250ms var(--ease);
    }
    .overlay.open .modal { transform: scale(1); }

    .modal-header {
      display: flex; align-items: center; justify-content: space-between;
      padding: 20px 24px; border-bottom: 1px solid var(--border);
    }
    .modal-title { font-family: 'Syne', sans-serif; font-size: 1rem; font-weight: 700; }

    .modal-body {
      padding: 24px;
      display: grid; grid-template-columns: 1fr 1fr; gap: 18px;
    }
    .detail-item { display: flex; flex-direction: column; gap: 4px; }
    .detail-item.full { grid-column: 1 / -1; }

    .label {
      font-size: 0.68rem; font-weight: 700;
      letter-spacing: 0.08em; text-transform: uppercase;
      color: var(--text-muted);
    }
    .value { font-size: 0.9rem; color: var(--text-primary); font-weight: 500; }

    .color-cell  { display: inline-flex; align-items: center; gap: 8px; }
    .color-dot   { width: 10px; height: 10px; border-radius: 50%; border: 1px solid var(--border-mid); }
    .placa-badge {
      display: inline-flex; align-items: center;
      background: var(--green-glow2); border: 1px solid rgba(34,197,94,0.2);
      color: var(--green-300); font-family: 'DM Mono', monospace;
      font-size: 0.78rem; font-weight: 500; padding: 3px 10px;
      border-radius: var(--radius-sm); letter-spacing: 0.08em;
    }
  `],
})
export class MotoModalComponent {
  @Input() moto: Moto | null = null;
  @Output() closed = new EventEmitter<void>();
}
