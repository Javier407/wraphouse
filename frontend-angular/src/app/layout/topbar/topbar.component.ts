import {
  ChangeDetectionStrategy, Component,
  EventEmitter, OnDestroy, OnInit,
  Output, inject, signal
} from '@angular/core';
import { AsyncPipe, NgClass } from '@angular/common';
import { Subject, timer } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
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
  private readonly destroy$ = new Subject<void>();

  readonly apiStatus$ = this.motoSvc.apiStatus$;
  readonly dateStr    = signal('');
  readonly spinning   = signal(false);

  ngOnInit(): void {
    timer(0, 60_000)
      .pipe(takeUntil(this.destroy$))
      .subscribe(() => this.dateStr.set(this._formatDate()));
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  refresh(): void {
    if (this.spinning()) return;
    this.spinning.set(true);
    this.motoSvc.loadMotos();
    setTimeout(() => this.spinning.set(false), 700);
  }

  private _formatDate(): string {
    return new Date().toLocaleDateString('es-CO', {
      weekday: 'short', day: '2-digit', month: 'short', year: 'numeric',
    });
  }
}
