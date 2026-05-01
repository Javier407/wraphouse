// src/main/java/co/vinni/motos/infraestructura/rest/ServicioRecursos.java

package co.vinni.moto.infraestructura.rest;

import co.vinni.moto.aplicacion.ServicioServicio;
import co.vinni.moto.dominio.modelo.Servicio;
import co.vinni.moto.infraestructura.dto.ServicioDto;
import co.vinni.moto.infraestructura.dto.ServicioRespuestaDto;
import co.vinni.moto.infraestructura.ServicioMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Endpoints REST para gestión de servicios.
 */
@Path("/servicios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServicioRecursos {

    @Inject
    ServicioServicio servicioServicio;

    /**
     * POST /kick/servicios
     * Registrar un nuevo servicio para una moto.
     */
    @POST
    public Response registrar(ServicioDto dto) {
        Servicio modelo = ServicioMapper.dtoAModelo(dto);
        Servicio guardado = servicioServicio.registrarServicio(modelo);
        ServicioRespuestaDto respuesta = ServicioMapper.modeloADto(guardado);
        
        return Response
            .created(URI.create("/servicios/" + guardado.id()))
            .entity(respuesta)
            .build();
    }

    /**
     * GET /kick/motos/{motoId}/servicios
     * Listar todos los servicios de una moto específica.
     */
    @GET
    @Path("/moto/{motoId}")
    public Response listarPorMoto(@PathParam("motoId") Long motoId) {
        List<Servicio> servicios = servicioServicio.listarServiciosDeMoto(motoId);
        
        List<ServicioRespuestaDto> respuesta = servicios.stream()
            .map(ServicioMapper::modeloADto)
            .collect(Collectors.toList());
        
        return Response.ok(respuesta).build();
    }
}
