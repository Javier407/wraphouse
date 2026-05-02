package co.vinni.common.infraestructure;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseApi<T> {

    private final String mensaje;
    private final int status;
    private final String timestamp;
    private final String path;
    private final boolean success;
    private final T data;
    private final ResponseApiError error;

    private ResponseApi(String mensaje, int status, String path,
                        boolean success, T data, ResponseApiError error) {
        this.mensaje   = mensaje;
        this.status    = status;
        this.timestamp = Instant.now().toString();
        this.path      = path;
        this.success   = success;
        this.data      = data;
        this.error     = error;
    }

    // ── Factories de éxito ──────────────────────────────────────────────────

    public static <T> ResponseApi<T> exito(T data, String mensaje) {
        return new ResponseApi<>(mensaje, 200, null, true, data, null);
    }

    public static <T> ResponseApi<T> exito(T data, String mensaje, int status) {
        return new ResponseApi<>(mensaje, status, null, true, data, null);
    }

    // ── Factories de error ──────────────────────────────────────────────────

    public static <T> ResponseApi<T> error(String mensaje, int status, String path) {
        return new ResponseApi<>(mensaje, status, path, false, null, null);
    }

    public static <T> ResponseApi<T> error(String mensaje, int status, String path, ResponseApiError detalle) {
        return new ResponseApi<>(mensaje, status, path, false, null, detalle);
    }

    // ── Getters ─────────────────────────────────────────────────────────────

    public String getMensaje()         { return mensaje; }
    public int getStatus()             { return status; }
    public String getTimestamp()       { return timestamp; }
    public String getPath()            { return path; }
    public boolean isSuccess()         { return success; }
    public T getData()                 { return data; }
    public ResponseApiError getError() { return error; }
}
