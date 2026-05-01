// src/app/pages/motos/components/servicio-form/servicio-form.component.ts
import { inject } from '@angular/core';
import {
  ChangeDetectionStrategy, Component,
  EventEmitter, Output, signal
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import {
  TipoServicio, ServicioCreateDto, PARTES_DISPONIBLES
} from 'src/app/core/models/servicio.model';

@Component({
  selector: 'app-servicio-form',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './servicio-form.component.html',
  styleUrl: './servicio-form.component.css',
})
export class ServicioFormComponent {
  @Output() servicioAdded = new EventEmitter<Partial<ServicioCreateDto>>();

  private readonly fb = inject(FormBuilder);

  readonly TipoServicio = TipoServicio;
  readonly partesDisponibles = PARTES_DISPONIBLES;
  readonly tipoSeleccionado = signal<TipoServicio | null>(null);

  readonly form = this.fb.nonNullable.group({
    tipoServicio: ['', Validators.required],

    // Diseño
    tipoDiseno: [''],
    nombreReferencia: [''],
    personalizado: [false],
    partesDiseno: [[] as string[]],

    // PPF
    acabadoPPF: [''],
    kit: [null as number | null],
    partesPPF: [[] as string[]],

    // Detailing
    tipoDetailing: [''],
    partesDetailing: [[] as string[]],

    // Protector
    acabadoProtector: [''],
    partesProtector: [[] as string[]],
  });

  onTipoChange(): void {
    const tipo = this.form.get('tipoServicio')?.value as TipoServicio;
    this.tipoSeleccionado.set(tipo || null);
    this.resetCamposEspecificos();
  }

  toggleParte(control: string, parte: string): void {
    const current = this.form.get(control)?.value as string[] || [];
    const updated = current.includes(parte)
      ? current.filter(p => p !== parte)
      : [...current, parte];
    this.form.get(control)?.setValue(updated);
  }

  isParteSelected(control: string, parte: string): boolean {
    const partes = this.form.get(control)?.value as string[] || [];
    return partes.includes(parte);
  }

  agregarServicio(): void {
    const tipo = this.tipoSeleccionado();
    if (!tipo) return;

    const servicio: Partial<ServicioCreateDto> = {
      tipoServicio: tipo,
      fecha: new Date().toISOString(),
    };

    // Construir objeto según tipo
    switch (tipo) {
      case TipoServicio.DISENO:
        servicio.diseno = {
          tipoDiseno: this.form.value.tipoDiseno || '',
          nombreReferencia: this.form.value.nombreReferencia || '',
          personalizado: this.form.value.personalizado || false,
          partes: this.form.value.partesDiseno || [],
        };
        break;

      case TipoServicio.PPF:
        servicio.ppf = {
          acabado: this.form.value.acabadoPPF as 'mate' | 'brillante',
          kit: this.form.value.kit as 1 | 2 | 3,
          partes: this.form.value.partesPPF || [],
        };
        break;

      case TipoServicio.DETAILING:
        servicio.detailing = {
          tipo: this.form.value.tipoDetailing as 'general' | 'parcial',
          partes: this.form.value.partesDetailing || [],
        };
        break;

      case TipoServicio.PROTECTOR:
        servicio.protector = {
          acabado: this.form.value.acabadoProtector as 'mate' | 'brillante',
          partes: this.form.value.partesProtector || [],
        };
        break;
    }

    this.servicioAdded.emit(servicio);
    this.resetForm();
  }

  private resetCamposEspecificos(): void {
    this.form.patchValue({
      tipoDiseno: '',
      nombreReferencia: '',
      personalizado: false,
      partesDiseno: [],
      acabadoPPF: '',
      kit: null,
      partesPPF: [],
      tipoDetailing: '',
      partesDetailing: [],
      acabadoProtector: '',
      partesProtector: [],
    });
  }

  private resetForm(): void {
    this.form.reset();
    this.tipoSeleccionado.set(null);
  }
}
