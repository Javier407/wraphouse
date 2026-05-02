package co.vinni.moto.infraestructura.persistencia;

import co.vinni.moto.dominio.modelo.*;
import co.vinni.moto.dominio.puerto.ServicioRepositorioPuerto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ServicioPanache implements ServicioRepositorioPuerto, PanacheRepository<ServicioEntity> {

    @Override
    public List<Servicio> listarPorMoto(Long motoId) {
        return find("motoId", motoId).stream()
                .map(this::toModelo)
                .toList();
    }

    @Override
    public Servicio guardar(Servicio servicio) {
        ServicioEntity entity = toEntity(servicio);
        persist(entity);
        return toModelo(entity);
    }

    @Override
    public Optional<Servicio> buscarPorId(Long id) {
        return findByIdOptional(id).map(this::toModelo);
    }

    // ── Mappers ─────────────────────────────────────────────────────────────

    private Servicio toModelo(ServicioEntity e) {
        Diseno diseno = e.getDiseno() != null ? new Diseno(
                e.getDiseno().getId(), e.getDiseno().getTipoDiseno(),
                e.getDiseno().getNombreReferencia(), e.getDiseno().getPersonalizado(),
                e.getDiseno().getPartes()) : null;

        PPF ppf = e.getPpf() != null ? new PPF(
                e.getPpf().getId(), e.getPpf().getAcabado(),
                e.getPpf().getKit(), e.getPpf().getPartes()) : null;

        Detailing detailing = e.getDetailing() != null ? new Detailing(
                e.getDetailing().getId(), e.getDetailing().getTipo(),
                e.getDetailing().getPartes()) : null;

        Protector protector = e.getProtector() != null ? new Protector(
                e.getProtector().getId(), e.getProtector().getAcabado(),
                e.getProtector().getPartes()) : null;

        return new Servicio(e.getId(), e.getMotoId(), e.getTipoServicio(),
                e.getFecha(), diseno, ppf, detailing, protector);
    }

    private ServicioEntity toEntity(Servicio s) {
        ServicioEntity entity = new ServicioEntity();
        entity.setId(s.id());
        entity.setMotoId(s.motoId());
        entity.setTipoServicio(s.tipoServicio());
        entity.setFecha(s.fecha());

        if (s.diseno() != null) {
            DisenoEntity d = new DisenoEntity();
            d.setTipoDiseno(s.diseno().tipoDiseno());
            d.setNombreReferencia(s.diseno().nombreReferencia());
            d.setPersonalizado(s.diseno().personalizado());
            d.setPartes(s.diseno().partes());
            entity.setDiseno(d);
        }
        if (s.ppf() != null) {
            PPFEntity p = new PPFEntity();
            p.setAcabado(s.ppf().acabado());
            p.setKit(s.ppf().kit());
            p.setPartes(s.ppf().partes());
            entity.setPpf(p);
        }
        if (s.detailing() != null) {
            DetailingEntity d = new DetailingEntity();
            d.setTipo(s.detailing().tipo());
            d.setPartes(s.detailing().partes());
            entity.setDetailing(d);
        }
        if (s.protector() != null) {
            ProtectorEntity p = new ProtectorEntity();
            p.setAcabado(s.protector().acabado());
            p.setPartes(s.protector().partes());
            entity.setProtector(p);
        }
        return entity;
    }
}
