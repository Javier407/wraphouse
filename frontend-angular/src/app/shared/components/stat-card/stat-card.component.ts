// src/app/shared/components/stat-card/stat-card.component.ts

import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-stat-card',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [NgClass],
  template: `
    <div class="stat-card" [ngClass]="{ primary }">
      <div class="stat-icon" [ngClass]="{ muted: !primary }">
        <ng-content select="[icon]" />
      </div>
      <div class="stat-content">
        <span class="stat-label">{{ label }}</span>
        <span class="stat-value" [ngClass]="{ small: smallValue }">{{ value }}</span>
        <span class="stat-trend" [ngClass]="trendClass">{{ trend }}</span>
      </div>
      @if (primary) { <div class="stat-glow"></div> }
    </div>
  `,
  styles: [`
    .stat-card {
      background: var(--bg-card); border: 1px solid var(--border);
      border-radius: var(--radius-lg); padding: 22px;
      display: flex; align-items: flex-start; gap: 16px;
      position: relative; overflow: hidden;
      transition: all 250ms var(--ease);
    }
    .stat-card:hover { border-color: var(--border-mid); transform: translateY(-2px); box-shadow: var(--shadow-md); }
    .stat-card.primary {
      border-color: rgba(34,197,94,0.25);
      background: linear-gradient(135deg, rgba(34,197,94,0.06) 0%, var(--bg-card) 60%);
    }
    .stat-glow {
      position: absolute; top: -20px; right: -20px;
      width: 80px; height: 80px;
      background: radial-gradient(circle, var(--green-glow) 0%, transparent 70%);
      pointer-events: none;
    }
    .stat-icon {
      width: 44px; height: 44px; flex-shrink: 0;
      background: var(--green-glow); border: 1px solid rgba(34,197,94,0.25);
      border-radius: var(--radius-md);
      display: flex; align-items: center; justify-content: center;
      color: var(--green-400);
    }
    .stat-icon.muted { background: var(--bg-elevated); border-color: var(--border); color: var(--text-secondary); }
    :host ::ng-deep .stat-icon svg { width: 22px; height: 22px; }

    .stat-content { display: flex; flex-direction: column; gap: 2px; }
    .stat-label  { font-size: 0.75rem; font-weight: 600; letter-spacing: 0.05em; text-transform: uppercase; color: var(--text-muted); }
    .stat-value  { font-family: 'Syne', sans-serif; font-size: 2rem; font-weight: 800; color: var(--text-primary); line-height: 1; margin: 4px 0; }
    .stat-value.small { font-size: 1.1rem; margin: 6px 0; }
    .stat-trend  { font-size: 0.72rem; color: var(--text-muted); }
    .stat-trend.up { color: var(--green-400); }
  `],
})
export class StatCardComponent {
  @Input() label      = '';
  @Input() value: string | number = '—';
  @Input() trend      = '';
  @Input() trendClass = 'neutral';
  @Input() primary    = false;
  @Input() smallValue = false;
}
