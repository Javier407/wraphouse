package co.vinni.common.exception;

public class RecursoNoEncontradoException extends NegocioException {

    public RecursoNoEncontradoException(String recurso, Long id) {
        super(recurso + " con id " + id + " no encontrado", 404);
    }
}
