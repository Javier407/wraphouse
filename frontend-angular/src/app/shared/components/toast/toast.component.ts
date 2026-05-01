// src/app/shared/components/toast/toast.component.ts

import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { AsyncPipe, NgClass, NgFor } from '@angular/common';
import { ToastService } from 'src/app/core/services/toast.service';
import { Toast } from 'src/app/core/models/toast.model';

@Component({
  selector: 'app-toast',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [NgFor, NgClass, AsyncPipe],
  template: `
    <div class="toast-container">
      <div
        *ngFor="let t of toastSvc.toasts$ | async; trackBy: trackById"
        class="toast"
        [ngClass]="t.type"
        (click)="toastSvc.dismiss(t.id)"
        role="alert"
      >
        <!-- Icon -->
        <svg class="toast-icon" viewBox="0 0 18 18" fill="none">
          @if (t.type === 'success') {
            <circle cx="9" cy="9" r="7.5" stroke="currentColor" stroke-width="1.3"/>
            <path d="M5.5 9l2.5 2.5 4-4.5" stroke="currentColor" stroke-width="1.3" stroke-linecap="round" stroke-linejoin="round"/>
          } @else if (t.type === 'error') {
            <circle cx="9" cy="9" r="7.5" stroke="currentColor" stroke-width="1.3"/>
            <path d="M6 6l6 6M12 6l-6 6" stroke="currentColor" stroke-width="1.3" stroke-linecap="round"/>
          } @else {
            <circle cx="9" cy="9" r="7.5" stroke="currentColor" stroke-width="1.3"/>
            <path d="M9 8v5M9 6v.01" stroke="currentColor" stroke-width="1.3" stroke-linecap="round"/>
          }
        </svg>
        <!-- Body -->
        <div class="toast-body">
          <p class="toast-title">{{ t.title }}</p>
          <p class="toast-msg">{{ t.message }}</p>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .toast-container {
      position: fixed; bottom: 24px; right: 24px;
      display: flex; flex-direction: column; gap: 10px; z-index: 9999;
      pointer-events: none;
    }
    .toast {
      pointer-events: all;
      display: flex; align-items: flex-start; gap: 12px;
      background: var(--bg-elevated); border: 1px solid var(--border-mid);
      border-left: 3px solid var(--border-mid);
      border-radius: var(--radius-md); padding: 14px 18px;
      min-width: 280px; max-width: 380px;
      box-shadow: var(--shadow-lg); cursor: pointer;
      animation: toastIn 300ms var(--ease) both;
    }
    .toast.success { border-left-color: var(--green-500); }
    .toast.error   { border-left-color: var(--red-500); }
    .toast.info    { border-left-color: var(--amber-400); }

    .toast-icon { width: 18px; height: 18px; flex-shrink: 0; margin-top: 1px; }
    .toast.success .toast-icon { color: var(--green-400); }
    .toast.error   .toast-icon { color: var(--red-500); }
    .toast.info    .toast-icon { color: var(--amber-400); }

    .toast-body { flex: 1; }
    .toast-title { font-size: 0.82rem; font-weight: 600; color: var(--text-primary); }
    .toast-msg   { font-size: 0.75rem; color: var(--text-secondary); margin-top: 2px; }
  `],
})
export class ToastComponent {
  readonly toastSvc = inject(ToastService);
  trackById(_: number, t: Toast): number { return t.id; }
}
