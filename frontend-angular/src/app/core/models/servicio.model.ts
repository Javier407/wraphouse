// src/app/core/models/servicio.model.ts

export enum TipoServicio {
  DISENO = 'DISENO',
  PPF = 'PPF',
  PROTECTOR = 'PROTECTOR',
  DETAILING = 'DETAILING'
}

export interface Diseno {
  tipoDiseno: string;
  nombreReferencia: string;
  personalizado: boolean;
  partes: string[];
}

export interface PPF {
  acabado: 'mate' | 'brillante';
  kit: 1 | 2 | 3;
  partes: string[];
}

export interface Detailing {
  tipo: 'general' | 'parcial';
  partes: string[];
}

export interface Protector {
  acabado: 'mate' | 'brillante';
  partes: string[];
}

export interface Servicio {
  id?: number;
  motoId?: number;
  tipoServicio: TipoServicio;
  fecha: string;
  diseno?: Diseno;
  ppf?: PPF;
  detailing?: Detailing;
  protector?: Protector;
}

// DTO para crear servicios (sin id)
export interface ServicioCreateDto {
  motoId: number;
  tipoServicio: TipoServicio;
  fecha?: string;
  diseno?: Diseno;
  ppf?: PPF;
  detailing?: Detailing;
  protector?: Protector;
}

// Opciones de partes disponibles
export const PARTES_DISPONIBLES = [
  'Tanque',
  'Guardafangos delantero',
  'Guardafangos trasero',
  'Faro',
  'Carenado lateral',
  'Colin',
  'Tapa lateral',
  'Puños',
  'Retrovisor',
  'Escape',
  'Motor',
  'Chasis'
] as const;

export type ParteMoto = typeof PARTES_DISPONIBLES[number];
