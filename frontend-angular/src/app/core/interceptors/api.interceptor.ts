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

      switch (true) {
        case error.status === 0:
          toast.error('Sin conexión', 'No se puede contactar al servidor. Verifica tu conexión a internet.');
          break;

        case error.status === 400: {
          const detalles: string[] = body?.error?.detalles ?? [];
          const detalle = detalles.length > 0 ? detalles[0] : mensaje;
          toast.error('Datos inválidos', detalle);
          break;
        }

        case error.status === 401:
          toast.error('No autorizado', 'Tu sesión expiró o no tienes credenciales válidas.');
          break;

        case error.status === 403:
          toast.error('Acceso denegado', 'No tienes permisos para realizar esta acción.');
          break;

        case error.status === 404:
          toast.error('No encontrado', mensaje);
          break;

        case error.status === 409:
          toast.error('Conflicto', mensaje);
          break;

        case error.status === 422:
          toast.error('Error de validación', mensaje);
          break;

        case error.status >= 500:
          toast.error('Error del servidor', 'Ocurrió un error interno. Intenta nuevamente.');
          break;

        default:
          toast.error('Error', mensaje);
      }

      return throwError(() => error);
    })
  );
};
