package co.vinni.common.exception;

import co.vinni.common.infraestructure.ResponseApi;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
public class NegocioExceptionMapper implements ExceptionMapper<NegocioException> {

    private static final Logger LOG = Logger.getLogger(NegocioExceptionMapper.class);

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(NegocioException e) {
        LOG.warnf("Error de negocio [%d] en %s: %s", e.getCodigoHttp(), uriInfo.getPath(), e.getMessage());

        return Response.status(e.getCodigoHttp())
                .entity(ResponseApi.error(e.getMessage(), e.getCodigoHttp(), uriInfo.getPath()))
                .build();
    }
}
