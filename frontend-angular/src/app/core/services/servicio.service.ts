// src/app/core/services/servicio.service.ts

import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Servicio, ServicioCreateDto } from '../models/servicio.model';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ServicioService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiBase}/servicios`;

  /**
   * Registrar un nuevo servicio para una moto
   */
  createServicio(dto: ServicioCreateDto): Observable<Servicio> {
    return this.http.post<Servicio>(this.baseUrl, dto);
  }

  /**
   * Listar todos los servicios de una moto específica
   */
  getServiciosByMoto(motoId: number): Observable<Servicio[]> {
    return this.http.get<Servicio[]>(`${this.baseUrl}/moto/${motoId}`);
  }
}
