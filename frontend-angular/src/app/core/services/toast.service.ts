// src/app/core/services/toast.service.ts

import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Toast, ToastType } from '../models/toast.model';

@Injectable({ providedIn: 'root' })
export class ToastService {

  private _seq = 0;
  private readonly _toasts$ = new BehaviorSubject<Toast[]>([]);
  readonly toasts$ = this._toasts$.asObservable();

  show(title: string, message: string, type: ToastType = 'info', duration = 4500): void {
    const toast: Toast = { id: ++this._seq, title, message, type };
    this._toasts$.next([...this._toasts$.value, toast]);
    setTimeout(() => this.dismiss(toast.id), duration);
  }

  dismiss(id: number): void {
    this._toasts$.next(this._toasts$.value.filter(t => t.id !== id));
  }

  success(title: string, message: string): void { this.show(title, message, 'success'); }
  error(title: string, message: string): void   { this.show(title, message, 'error'); }
  info(title: string, message: string): void    { this.show(title, message, 'info'); }
}
