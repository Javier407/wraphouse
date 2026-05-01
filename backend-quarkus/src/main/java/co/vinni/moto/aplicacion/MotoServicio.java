package co.vinni.moto.aplicacion;

import co.vinni.moto.dominio.modelo.Moto;
import co.vinni.moto.dominio.puerto.MotoRepositorioPuerto;
import co.vinni.moto.infraestructura.dto.MotoDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MotoServicio {

    @Inject
    MotoRepositorioPuerto repositorio;

    public List<Moto> listar() {
        return repositorio.listarTodas();
    }

    public Optional<Moto> buscarPorId(Long id) {
        return repositorio.buscarPorId(id);
    }

    @Transactional
    public Moto crear(MotoDto dto) {
        return repositorio.guardar(dto);
    }

    @Transactional
    public Optional<Moto> actualizar(Long id, MotoDto dto) {
        Moto actualizada = repositorio.actualizar(id, dto);
        return Optional.ofNullable(actualizada);
    }

    @Transactional
    public boolean eliminar(Long id) {
        return repositorio.eliminar(id);
    }
}
