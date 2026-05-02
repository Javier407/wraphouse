package co.vinni.moto.infraestructura.rest;

import co.vinni.common.infraestructure.ResponseApi;
import co.vinni.moto.aplicacion.ServicioServicio;
import co.vinni.moto.dominio.modelo.Servicio;
import co.vinni.moto.infraestructura.dto.ServicioDto;
import co.vinni.moto.infraestructura.dto.ServicioMapper;
import co.vinni.moto.infraestructura.dto.ServicioRespuestaDto;
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

@Path("/servicios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServicioRecursos {

    private static final Logger LOG = Logger.getLogger(ServicioRecursos.class);

    @Inject
    ServicioServicio servicioServicio;

    @Context
    UriInfo uriInfo;

    @POST
    public Response registrar(@Valid ServicioDto dto) {
        Servicio modelo = ServicioMapper.dtoAModelo(dto);
        Servicio guardado = servicioServicio.registrarServicio(modelo);
        ServicioRespuestaDto respuesta = ServicioMapper.modeloADto(guardado);

        LOG.infof("POST /servicios — registrado id=%d tipo=%s motoId=%d",
                guardado.id(), guardado.tipoServicio(), guardado.motoId());

        URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(guardado.id())).build();
        return Response.created(location)
                .entity(ResponseApi.exito(respuesta, "Servicio registrado exitosamente", 201))
                .build();
    }

    @GET
    @Path("/moto/{motoId}")
    public Response listarPorMoto(@PathParam("motoId") Long motoId) {
        List<ServicioRespuestaDto> respuesta = servicioServicio.listarServiciosDeMoto(motoId)
                .stream()
                .map(ServicioMapper::modeloADto)
                .toList();
        return Response.ok(ResponseApi.exito(respuesta, "OK")).build();
    }
}
