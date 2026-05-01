// src/app/pages/dashboard/dashboard.component.ts

import { ChangeDetectionStrategy, Component, OnInit, inject } from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { Router } from '@angular/router';
import { MotoService } from '../../core/services/moto.service';
import { StatCardComponent } from '../../shared/components/stat-card/stat-card.component';
import { FormatDatePipe } from '../../shared/pipes/format-date.pipe';
import { Moto } from '../../core/models/moto.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [AsyncPipe, StatCardComponent, FormatDatePipe],
  templateUrl: './dashboard.component.html',
  styleUrl:    './dashboard.component.css',
})
export class DashboardComponent implements OnInit {
  private readonly router  = inject(Router);
  readonly motoSvc         = inject(MotoService);
  readonly loading$        = this.motoSvc.loading$;
  readonly error$          = this.motoSvc.error$;

  ngOnInit(): void { this.motoSvc.loadMotos(); }

  goMotos(): void { this.router.navigate(['/motos']); }

  trackById(_: number, m: Moto): number { return m.id; }
}
