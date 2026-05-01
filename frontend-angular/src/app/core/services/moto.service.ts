import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { BehaviorSubject, Observable, catchError, map, tap, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Moto, MotoCreateDto, MotoStats, SortState } from '../models/moto.model';
import { ApiResponse } from '../models/api-response.model';

@Injectable({ providedIn: 'root' })
export class MotoService {

  private readonly http   = inject(HttpClient);
  private readonly apiUrl = `${environment.apiBase}/motos`;

  // ── Estado privado ────────────────────────────────────────────────────────
  private readonly _motos$     = new BehaviorSubject<Moto[]>([]);
  private readonly _filtered$  = new BehaviorSubject<Moto[]>([]);
  private readonly _loading$   = new BehaviorSubject<boolean>(false);
  private readonly _error$     = new BehaviorSubject<string | null>(null);
  private readonly _apiStatus$ = new BehaviorSubject<'connecting' | 'online' | 'offline'>('connecting');

  // ── Observables públicos ──────────────────────────────────────────────────
  readonly motos$     = this._motos$.asObservable();
  readonly filtered$  = this._filtered$.asObservable();
  readonly loading$   = this._loading$.asObservable();
  readonly error$     = this._error$.asObservable();
  readonly apiStatus$ = this._apiStatus$.asObservable();

  // ── Estado de filtros ─────────────────────────────────────────────────────
  private _query = '';
  private _sort: SortState = { col: 'id', dir: 'asc' };

  // ── Getters calculados ────────────────────────────────────────────────────
  get stats(): MotoStats {
    const all = this._motos$.value;
    const ultima = all.reduce((latest: Moto | null, m) =>
      !latest || new Date(m.fechaRegistro ?? 0) > new Date(latest.fechaRegistro ?? 0) ? m : latest
    , null);
    return {
      total:          all.length,
      clientesUnicos: new Set(all.map(m => m.cliente?.trim().toLowerCase())).size,
      marcas:         new Set(all.map(m => m.marca?.trim().toLowerCase())).size,
      ultimaPlaca:    ultima?.placa ?? '—',
    };
  }

  get recentMotos(): Moto[] {
    return [...this._motos$.value]
      .sort((a, b) => new Date(b.fechaRegistro ?? 0).getTime() - new Date(a.fechaRegistro ?? 0).getTime())
      .slice(0, 5);
  }

  get sortState(): SortState { return this._sort; }

  // ── HTTP ──────────────────────────────────────────────────────────────────
  loadMotos(): void {
    this._loading$.next(true);
    this._error$.next(null);
    this._apiStatus$.next('connecting');

    this.http.get<ApiResponse<Moto[]>>(this.apiUrl).pipe(
      map(res => res.data ?? []),
      tap(motos => {
        this._motos$.next(motos);
        this._apiStatus$.next('online');
        this._loading$.next(false);
        this._apply();
      }),
      catchError((err: HttpErrorResponse) => {
        this._apiStatus$.next('offline');
        this._loading$.next(false);
        this._error$.next('No se pudo conectar con el servidor');
        return throwError(() => err);
      })
    ).subscribe();
  }

  createMoto(dto: MotoCreateDto): Observable<Moto> {
    return this.http.post<ApiResponse<Moto>>(this.apiUrl, dto).pipe(
      map(res => res.data!),
      tap(() => this.loadMotos()),
      catchError((err: HttpErrorResponse) => throwError(() => err))
    );
  }

  deleteMoto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      tap(() => this.loadMotos()),
      catchError((err: HttpErrorResponse) => throwError(() => err))
    );
  }

  updateMoto(id: number, dto: MotoCreateDto): Observable<Moto> {
    return this.http.put<ApiResponse<Moto>>(`${this.apiUrl}/${id}`, dto).pipe(
      map(res => res.data!),
      tap(() => this.loadMotos()),
      catchError((err: HttpErrorResponse) => throwError(() => err))
    );
  }

  // ── Filtro y Orden ────────────────────────────────────────────────────────
  search(query: string): void {
    this._query = query.toLowerCase().trim();
    this._apply();
  }

  sort(col: keyof Moto): void {
    this._sort = this._sort.col === col
      ? { col, dir: this._sort.dir === 'asc' ? 'desc' : 'asc' }
      : { col, dir: 'asc' };
    this._apply();
  }

  private _apply(): void {
    let result = [...this._motos$.value];

    if (this._query) {
      result = result.filter(m =>
        [m.placa, m.marca, m.modelo, m.color ?? '', m.cliente]
          .some(v => String(v).toLowerCase().includes(this._query))
      );
    }

    const { col, dir } = this._sort;
    if (!col) { this._filtered$.next(result); return; }

    result.sort((a, b) => {
      const va = col === 'id' ? Number(a[col]) : String(a[col] ?? '').toLowerCase();
      const vb = col === 'id' ? Number(b[col]) : String(b[col] ?? '').toLowerCase();
      if (va < vb) return dir === 'asc' ? -1 : 1;
      if (va > vb) return dir === 'asc' ? 1 : -1;
      return 0;
    });

    this._filtered$.next(result);
  }
}
