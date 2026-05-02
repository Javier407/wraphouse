import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { ToastService } from '../services/toast.service';

export const apiInterceptor: HttpInterceptorFn = (req, next) => {
  const toast = inject(ToastService);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      const body = error.error;
      const mensaje: string = body?.mensaje ?? body?.message ?? 'Error inesperado';

      if (error.status === 0) {
        toast.error('Sin conexión', 'No se puede contactar al servidor');
      } else if (error.status >= 500) {
        toast.error('Error del servidor', mensaje);
      } else if (error.status === 404) {
        toast.error('No encontrado', mensaje);
      } else if (error.status === 400) {
        const detalles: string[] = body?.error?.detalles ?? [];
        const detalle = detalles.length > 0 ? detalles[0] : mensaje;
        toast.error('Datos inválidos', detalle);
      }

      return throwError(() => error);
    })
  );
};
