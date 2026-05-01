// src/app/shared/pipes/format-date.pipe.ts

import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'formatDate', standalone: true, pure: true })
export class FormatDatePipe implements PipeTransform {
  transform(iso: string | null | undefined, full = false): string {
    if (!iso) return '—';
    try {
      const opts: Intl.DateTimeFormatOptions = full
        ? { day: '2-digit', month: 'long',  year: 'numeric', hour: '2-digit', minute: '2-digit' }
        : { day: '2-digit', month: 'short', year: 'numeric' };
      return new Date(iso).toLocaleDateString('es-CO', opts);
    } catch {
      return iso;
    }
  }
}
