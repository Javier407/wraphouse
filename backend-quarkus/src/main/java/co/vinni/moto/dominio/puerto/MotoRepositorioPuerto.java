package co.vinni.moto.dominio.puerto;

import co.vinni.moto.dominio.modelo.Moto;
import co.vinni.moto.infraestructura.dto.MotoDto;

import java.util.List;
import java.util.Optional;

public interface MotoRepositorioPuerto {
    List<Moto> listarTodas();
    Moto guardar(MotoDto dto);
    Optional<Moto> buscarPorId(Long id);
    Moto actualizar(Long id, MotoDto dto);
    boolean eliminar(Long id);
}
