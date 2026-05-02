package co.vinni.moto.dominio.puerto;

import co.vinni.moto.dominio.modelo.Servicio;

import java.util.List;
import java.util.Optional;

public interface ServicioRepositorioPuerto {
    List<Servicio> listarPorMoto(Long motoId);
    Servicio guardar(Servicio servicio);
    Optional<Servicio> buscarPorId(Long id);
}
