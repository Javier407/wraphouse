// src/app/pages/motos/motos.component.ts

import {
  ChangeDetectionStrategy, Component,
  HostListener, OnInit, inject, signal
} from '@angular/core';
import { AsyncPipe, NgClass } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

import { MotoService } from '../../core/services/moto.service';
import { ToastService } from '../../core/services/toast.service';
import { Moto, MotoCreateDto } from '../../core/models/moto.model';
import { FormatDatePipe } from '../../shared/pipes/format-date.pipe';
import { ColorHashPipe } from '../../shared/pipes/color-hash.pipe';
import { MotoModalComponent } from '../../shared/components/moto-modal/moto-modal.component';

@Component({
  selector: 'app-motos',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [
    AsyncPipe, NgClass, ReactiveFormsModule,
    FormatDatePipe, ColorHashPipe, MotoModalComponent,
  ],
  templateUrl: './motos.component.html',
  styleUrl:    './motos.component.css',
})
export class MotosComponent implements OnInit {

  private readonly fb         = inject(FormBuilder);
  private readonly motoSvc    = inject(MotoService);
  private readonly toastSvc   = inject(ToastService);

  /* ── Streams ── */
  readonly motos$    = this.motoSvc.motos$;
  readonly filtered$ = this.motoSvc.filtered$;
  readonly loading$  = this.motoSvc.loading$;
  readonly error$    = this.motoSvc.error$;

  /* ── UI state (signals) ── */
  readonly formOpen    = signal(false);
  readonly submitting  = signal(false);
  readonly searchQuery = signal('');
  readonly selectedMoto = signal<Moto | null>(null);

  /* ── Reactive form ── */
  readonly form = this.fb.nonNullable.group({
    placa: ['', Validators.required],
    marca: ['', Validators.required],
    modelo: ['', Validators.required],
    color: [''],
    cliente: ['', Validators.required],
    fotoUrl: [''], // o luego file input
  });

  ngOnInit(): void { this.motoSvc.loadMotos(); }

  /* ── Form ── */
  toggleForm(): void {
    this.formOpen.update(v => !v);
    if (!this.formOpen()) this._resetForm();
  }

  private _resetForm(): void {
    this.form.reset();
    this.form.markAsUntouched();
  }

  clearForm(): void { this._resetForm(); }

  submitMoto(): void {
    this.form.markAllAsTouched();
    if (this.form.invalid || this.submitting()) return;

    const raw = this.form.getRawValue();
    const dto: MotoCreateDto = {
      placa: raw.placa.toUpperCase().trim(),
      marca: raw.marca.trim(),
      modelo: raw.modelo.trim(),
      color: raw.color?.trim() || null,
      cliente: raw.cliente.trim(),
      fotoUrl: raw.fotoUrl?.trim() || null, //
    };

    this.submitting.set(true);

    this.motoSvc.createMoto(dto).subscribe({
      next: saved => {
        this.toastSvc.success('¡Moto registrada!', `La moto ${saved.placa} fue añadida al sistema.`);
        this._resetForm();
        this.toggleForm();
        this.submitting.set(false);
      },
      error: (err: HttpErrorResponse) => {
        const body = err.error;
        const msg  = body?.detalle ?? body?.mensaje ?? body?.error ?? 'Error desconocido del servidor';
        this.toastSvc.error('No se pudo guardar', msg);
        this.submitting.set(false);
      },
    });
  }

  /* ── Search ── */
  onSearch(event: Event): void {
    const v = (event.target as HTMLInputElement).value;
    this.searchQuery.set(v);
    this.motoSvc.search(v);
  }

  clearSearch(): void {
    this.searchQuery.set('');
    this.motoSvc.search('');
  }

  /* ── Sort ── */
  sort(col: keyof Moto): void { this.motoSvc.sort(col); }

  sortIcon(col: string): string {
    const s = this.motoSvc.sortState;
    if (s.col !== col) return '↕';
    return s.dir === 'asc' ? '↑' : '↓';
  }

  sortCls(col: string): string {
    const s = this.motoSvc.sortState;
    return s.col === col ? s.dir : '';
  }

  /* ── Modal ── */
  openModal(moto: Moto): void { this.selectedMoto.set(moto); }
  closeModal(): void          { this.selectedMoto.set(null); }

  @HostListener('document:keydown.escape')
  onEsc(): void { this.closeModal(); }

  /* ── Delete placeholder ── */
  confirmDelete(id: number, placa: string): void {
    this.toastSvc.info(
      'Función no disponible',
      `El endpoint DELETE /motos/${id} (${placa}) aún no está implementado.`
    );
  }

  /* ── Helpers ── */
  isInvalid(field: string): boolean {
    const c = this.form.get(field);
    return !!(c?.invalid && c.touched);
  }
  hasError(field: string, err = 'required'): boolean {
    const c = this.form.get(field);
    return !!(c?.touched && c.hasError(err));
  }
  trackById(_: number, m: Moto): number { return m.id; }
}
