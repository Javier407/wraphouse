package co.vinni.moto.infraestructura.persistencia;

import co.vinni.moto.aplicacion.MotoServicio;
import co.vinni.moto.dominio.modelo.Moto;
import co.vinni.moto.infraestructura.dto.MotoDto;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/motos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MotoResource {

    @Inject
    MotoServicio servicio;

    @GET
    public List<Moto> listar() {
        return servicio.listar();
    }

    @POST
    public Moto crear(MotoDto dto) {
        return servicio.crear(dto);
    }
}