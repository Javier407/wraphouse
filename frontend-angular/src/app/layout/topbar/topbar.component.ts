// src/app/layout/topbar/topbar.component.ts

import {
  ChangeDetectionStrategy, Component,
  EventEmitter, OnDestroy, OnInit,
  Output, inject, signal
} from '@angular/core';
import { AsyncPipe, NgClass } from '@angular/common';
import { MotoService } from '../../core/services/moto.service';

@Component({
  selector: 'app-topbar',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [NgClass, AsyncPipe],
  templateUrl: './topbar.component.html',
  styleUrl: './topbar.component.css',
})
export class TopbarComponent implements OnInit, OnDestroy {
  @Output() menuToggled = new EventEmitter<void>();

  private readonly motoSvc = inject(MotoService);

  readonly apiStatus$ = this.motoSvc.apiStatus$;
  readonly dateStr    = signal('');
  readonly spinning   = signal(false);

  private _interval?: ReturnType<typeof setInterval>;

  ngOnInit(): void {
    this._tick();
    this._interval = setInterval(() => this._tick(), 60_000);
  }

  ngOnDestroy(): void {
    clearInterval(this._interval);
  }

  refresh(): void {
    if (this.spinning()) return;
    this.spinning.set(true);
    this.motoSvc.loadMotos();
    setTimeout(() => this.spinning.set(false), 700);
  }

  private _tick(): void {
    this.dateStr.set(
      new Date().toLocaleDateString('es-CO', {
        weekday: 'short', day: '2-digit', month: 'short', year: 'numeric',
      })
    );
  }
}
