// src/app/core/models/moto.model.ts
// ⚠️ ACTUALIZACIÓN: Se agregaron campos fotoUrl y servicios

import { Servicio } from './servicio.model';

export interface Moto {
  id: number;
  placa: string;
  marca: string;
  modelo: string;
  color: string | null;
  cliente: string;
  fotoUrl: string | null;
  fechaRegistro: string;
  servicios?: Servicio[];
}

export interface MotoCreateDto {
  placa: string;
  marca: string;
  modelo: string;
  color: string | null;
  cliente: string;
  fotoUrl?: string | null;
}

export interface MotoStats {
  total: number;
  clientesUnicos: number;
  marcas: number;
  ultimaPlaca: string;
}

export interface SortState {
  col: keyof Moto | null;
  dir: 'asc' | 'desc';
}
