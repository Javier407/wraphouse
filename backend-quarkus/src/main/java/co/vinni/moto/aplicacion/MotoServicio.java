package co.vinni.moto.aplicacion;

import co.vinni.moto.dominio.modelo.Moto;
import co.vinni.moto.dominio.repositorio.MotoRepository;
import co.vinni.moto.infraestructura.dto.MotoDto;
import jakarta.transaction.Transactional;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class MotoServicio {

    @Inject
    MotoRepository repo;

    public List<Moto> listar() {
        return repo.listAll();
    }

    @Transactional
    public Moto crear(MotoDto dto) {

        Moto m = new Moto();

        m.placa = dto.placa();
        m.marca = dto.marca();
        m.modelo = dto.modelo();
        m.color = dto.color();
        m.cliente = dto.cliente();
        m.fotoUrl = dto.fotoUrl();
        m.fechaRegistro = java.time.LocalDateTime.now();

        repo.persist(m);

        return m;
    }
}