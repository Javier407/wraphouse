export interface ApiResponse<T> {
  mensaje: string;
  status: number;
  timestamp: string;
  path?: string;
  success: boolean;
  data?: T;
  error?: ApiResponseError;
}

export interface ApiResponseError {
  mensaje: string;
  codigo: string;
  detalles?: string[];
}
