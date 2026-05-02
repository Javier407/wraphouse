import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Servicio, ServicioCreateDto } from '../models/servicio.model';
import { ApiResponse } from '../models/api-response.model';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ServicioService {
  private readonly http    = inject(HttpClient);
  private readonly baseUrl = `${environment.apiBase}/servicios`;

  createServicio(dto: ServicioCreateDto): Observable<Servicio> {
    return this.http.post<ApiResponse<Servicio>>(this.baseUrl, dto).pipe(
      map(res => res.data!)
    );
  }

  getServiciosByMoto(motoId: number): Observable<Servicio[]> {
    return this.http.get<ApiResponse<Servicio[]>>(`${this.baseUrl}/moto/${motoId}`).pipe(
      map(res => res.data ?? [])
    );
  }
}
