package co.vinni.common.exception;

public class NegocioException extends RuntimeException {

    private final int codigoHttp;

    public NegocioException(String mensaje, int codigoHttp) {
        super(mensaje);
        this.codigoHttp = codigoHttp;
    }

    public int getCodigoHttp() {
        return codigoHttp;
    }
}
