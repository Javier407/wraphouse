// src/app/core/models/toast.model.ts

export type ToastType = 'success' | 'error' | 'info';

export interface Toast {
  id: number;
  title: string;
  message: string;
  type: ToastType;
}
