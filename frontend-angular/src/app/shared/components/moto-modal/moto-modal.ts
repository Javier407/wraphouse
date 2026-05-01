// src/app/shared/components/moto-modal/moto-modal.component.ts
// ⚠️ ACTUALIZACIÓN: Muestra foto y servicios

import {
  ChangeDetectionStrategy, Component,
  EventEmitter, Input, OnChanges, Output, inject, signal
} from '@angular/core';
import { NgClass, AsyncPipe } from '@angular/common';
import { Moto } from '../../../core/models/moto.model';
import { Servicio } from '../../../core/models/servicio.model';
import { ServicioService } from '../../../core/services/servicio.service';
import { FormatDatePipe } from '../../pipes/format-date.pipe';
import { ColorHashPipe } from '../../pipes/color-hash.pipe';

@Component({
  selector: 'app-moto-modal',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [NgClass, AsyncPipe, FormatDatePipe, ColorHashPipe],
  templateUrl: './moto-modal.component.html',
  styleUrl: './moto-modal.component.css',
})
export class MotoModalComponent implements OnChanges {
  @Input() moto: Moto | null = null;
  @Output() closed = new EventEmitter<void>();

  private readonly servicioSvc = inject(ServicioService);
  readonly servicios = signal<Servicio[]>([]);
  readonly loadingServicios = signal(false);

  ngOnChanges(): void {
    if (this.moto?.id) {
      this.loadingServicios.set(true);
      this.servicioSvc.getServiciosByMoto(this.moto.id).subscribe({
        next: servicios => {
          this.servicios.set(servicios);
          this.loadingServicios.set(false);
        },
        error: () => {
          this.servicios.set([]);
          this.loadingServicios.set(false);
        },
      });
    }
  }

  getTipoLabel(servicio: Servicio): string {
    switch (servicio.tipoServicio) {
      case 'DISENO': return 'Diseño';
      case 'PPF': return 'PPF';
      case 'PROTECTOR': return 'Protector';
      case 'DETAILING': return 'Detailing';
      default: return servicio.tipoServicio;
    }
  }

  getServicioDetalle(servicio: Servicio): string {
    if (servicio.diseno) {
      return servicio.diseno.tipoDiseno || 'Diseño personalizado';
    }
    if (servicio.ppf) {
      return `Kit ${servicio.ppf.kit} - ${servicio.ppf.acabado}`;
    }
    if (servicio.detailing) {
      return servicio.detailing.tipo === 'general' ? 'Completo' : 'Parcial';
    }
    if (servicio.protector) {
      return `Acabado ${servicio.protector.acabado}`;
    }
    return '';
  }
}
