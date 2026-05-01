// src/main/java/co/vinni/motos/repositorio/ServicioRepositorio.java

package co.vinni.moto.dominio.repositorio;

import co.vinni.moto.dominio.modelo.Servicio;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del repositorio para Servicio.
 * Abstrae la persistencia siguiendo DDD.
 */
public interface ServicioRepositorio {
    List<Servicio> listarPorMoto(Long motoId);
    Servicio guardar(Servicio servicio);
    Optional<Servicio> buscarPorId(Long id);
}
