package co.vinni.moto.infraestructura.persistencia;

import co.vinni.moto.dominio.modelo.Moto;
import co.vinni.moto.dominio.puerto.MotoRepositorioPuerto;
import co.vinni.moto.infraestructura.dto.MotoDto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MotoRepositorio implements MotoRepositorioPuerto, PanacheRepository<MotoEntity> {

    private static final Logger LOG = Logger.getLogger(MotoRepositorio.class);

    @Override
    public List<Moto> listarTodas() {
        return findAll().stream()
                .map(this::toModelo)
                .toList();
    }

    @Override
    public Moto guardar(MotoDto dto) {
        MotoEntity entity = new MotoEntity();
        entity.setPlaca(dto.placa().toUpperCase().trim());
        entity.setMarca(dto.marca().trim());
        entity.setModelo(dto.modelo().trim());
        entity.setColor(dto.color() != null ? dto.color().trim() : null);
        entity.setCliente(dto.cliente().trim());
        entity.setFotoUrl(dto.fotoUrl());
        entity.setFechaRegistro(LocalDateTime.now());
        persist(entity);
        LOG.infof("Moto guardada con id=%d placa=%s", entity.getId(), entity.getPlaca());
        return toModelo(entity);
    }

    @Override
    public Optional<Moto> buscarPorId(Long id) {
        return findByIdOptional(id).map(this::toModelo);
    }

    @Override
    public Moto actualizar(Long id, MotoDto dto) {
        MotoEntity entity = findById(id);
        if (entity == null) return null;
        entity.setPlaca(dto.placa().toUpperCase().trim());
        entity.setMarca(dto.marca().trim());
        entity.setModelo(dto.modelo().trim());
        entity.setColor(dto.color() != null ? dto.color().trim() : null);
        entity.setCliente(dto.cliente().trim());
        entity.setFotoUrl(dto.fotoUrl());
        LOG.infof("Moto actualizada id=%d", id);
        return toModelo(entity);
    }

    @Override
    public boolean eliminar(Long id) {
        boolean eliminado = deleteById(id);
        if (eliminado) LOG.infof("Moto eliminada id=%d", id);
        return eliminado;
    }

    // ── Mapper interno ──────────────────────────────────────────────────────

    private Moto toModelo(MotoEntity e) {
        return new Moto(
                e.getId(), e.getPlaca(), e.getMarca(), e.getModelo(),
                e.getColor(), e.getCliente(), e.getFotoUrl(), e.getFechaRegistro()
        );
    }
}
