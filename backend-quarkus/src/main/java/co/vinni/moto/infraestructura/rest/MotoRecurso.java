package co.vinni.moto.infraestructura.rest;

import co.vinni.common.exception.RecursoNoEncontradoException;
import co.vinni.common.infraestructure.ResponseApi;
import co.vinni.moto.aplicacion.MotoServicio;
import co.vinni.moto.dominio.modelo.Moto;
import co.vinni.moto.infraestructura.dto.MotoDto;
import co.vinni.moto.infraestructura.dto.MotoRespuestaDto;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.logging.Logger;

import java.net.URI;
import java.util.List;

@Path("/motos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MotoRecurso {

    private static final Logger LOG = Logger.getLogger(MotoRecurso.class);

    @Inject
    MotoServicio servicio;

    @Context
    UriInfo uriInfo;

    @GET
    public Response listar() {
        List<MotoRespuestaDto> motos = servicio.listar().stream()
                .map(this::toDto)
                .toList();
        return Response.ok(ResponseApi.exito(motos, "OK")).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Moto moto = servicio.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Moto", id));
        return Response.ok(ResponseApi.exito(toDto(moto), "OK")).build();
    }

    @POST
    public Response crear(@Valid MotoDto dto) {
        Moto creada = servicio.crear(dto);
        LOG.infof("POST /motos — creada placa=%s", creada.placa());
        URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(creada.id())).build();
        return Response.created(location)
                .entity(ResponseApi.exito(toDto(creada), "Moto registrada exitosamente", 201))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Long id, @Valid MotoDto dto) {
        Moto actualizada = servicio.actualizar(id, dto)
                .orElseThrow(() -> new RecursoNoEncontradoException("Moto", id));
        return Response.ok(ResponseApi.exito(toDto(actualizada), "Moto actualizada exitosamente")).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) {
        boolean eliminada = servicio.eliminar(id);
        if (!eliminada) throw new RecursoNoEncontradoException("Moto", id);
        return Response.noContent().build();
    }

    // ── Mapper ──────────────────────────────────────────────────────────────

    private MotoRespuestaDto toDto(Moto m) {
        return new MotoRespuestaDto(
                m.id(), m.placa(), m.marca(), m.modelo(),
                m.color(), m.cliente(), m.fotoUrl(), m.fechaRegistro()
        );
    }
}
