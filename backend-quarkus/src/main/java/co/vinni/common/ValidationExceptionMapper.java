package co.vinni.common;

import co.vinni.common.infraestructure.ResponseApi;
import co.vinni.common.infraestructure.ResponseApiError;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.stream.Collectors;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final Logger LOG = Logger.getLogger(ValidationExceptionMapper.class);

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<String> detalles = exception.getConstraintViolations()
                .stream()
                .map(v -> {
                    String path = v.getPropertyPath().toString();
                    if (path.contains(".")) {
                        path = path.substring(path.lastIndexOf('.') + 1);
                    }
                    return path + ": " + v.getMessage();
                })
                .collect(Collectors.toList());

        LOG.debugf("Validación fallida en %s — %d violación(es): %s",
                uriInfo.getPath(), detalles.size(), detalles);

        ResponseApiError detalle = ResponseApiError.builder()
                .mensaje("Error de validación")
                .codigo("VAL-001")
                .detalles(detalles)
                .build();

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ResponseApi.error(
                        "Los datos enviados no son válidos",
                        Response.Status.BAD_REQUEST.getStatusCode(),
                        uriInfo.getPath(),
                        detalle))
                .build();
    }
}
