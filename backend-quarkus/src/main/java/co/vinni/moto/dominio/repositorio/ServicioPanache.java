// src/main/java/co/vinni/motos/repositorio/ServicioPanache.java

package co.vinni.moto.dominio.repositorio;
import co.vinni.moto.dominio.modelo.*;
import co.vinni.moto.dominio.repositorio.MotoEntity.*;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ServicioPanache implements ServicioRepositorio, PanacheRepository<ServicioEntity> {

    @Override
    public List<co.vinni.moto.dominio.modelo.Servicio> listarPorMoto(Long motoId) {
        List<ServicioEntity> entities = find("motoId", motoId).list();
        return entities.stream()
            .map(this::entityAModelo)
            .collect(Collectors.toList());
    }

    @Override
    public Servicio guardar(Servicio servicio) {
        ServicioEntity entity = modeloAEntity(servicio);
        persist(entity);
        return entityAModelo(entity);
    }

    @Override
    public Optional<Servicio> buscarPorId(Long id) {
        return findByIdOptional(id)
            .map(this::entityAModelo);
    }

    // Transformación Entity → Modelo
    private Servicio entityAModelo(ServicioEntity entity) {
        Diseno diseno = entity.getDiseno() != null 
            ? new Diseno(
                entity.getDiseno().getId(),
                entity.getDiseno().getTipoDiseno(),
                entity.getDiseno().getNombreReferencia(),
                entity.getDiseno().getPersonalizado(),
                entity.getDiseno().getPartes()
            )
            : null;

        PPF ppf = entity.getPpf() != null
            ? new PPF(
                entity.getPpf().getId(),
                entity.getPpf().getAcabado(),
                entity.getPpf().getKit(),
                entity.getPpf().getPartes()
            )
            : null;

        Detailing detailing = entity.getDetailing() != null
            ? new Detailing(
                entity.getDetailing().getId(),
                entity.getDetailing().getTipo(),
                entity.getDetailing().getPartes()
            )
            : null;

        Protector protector = entity.getProtector() != null
            ? new Protector(
                entity.getProtector().getId(),
                entity.getProtector().getAcabado(),
                entity.getProtector().getPartes()
            )
            : null;

        return new Servicio(
            entity.getId(),
            entity.getMotoId(),
            entity.getTipoServicio(),
            entity.getFecha(),
            diseno,
            ppf,
            detailing,
            protector
        );
    }

    // Transformación Modelo → Entity
    private ServicioEntity modeloAEntity(Servicio modelo) {
        ServicioEntity entity = new ServicioEntity();
        entity.setId(modelo.id());
        entity.setMotoId(modelo.motoId());
        entity.setTipoServicio(modelo.tipoServicio());
        entity.setFecha(modelo.fecha());

        // Asignar subtipo correspondiente
        if (modelo.diseno() != null) {
            DisenoEntity disenoEntity = new DisenoEntity();
            disenoEntity.setTipoDiseno(modelo.diseno().tipoDiseno());
            disenoEntity.setNombreReferencia(modelo.diseno().nombreReferencia());
            disenoEntity.setPersonalizado(modelo.diseno().personalizado());
            disenoEntity.setPartes(modelo.diseno().partes());
            entity.setDiseno(disenoEntity);
        }

        if (modelo.ppf() != null) {
            PPFEntity ppfEntity = new PPFEntity();
            ppfEntity.setAcabado(modelo.ppf().acabado());
            ppfEntity.setKit(modelo.ppf().kit());
            ppfEntity.setPartes(modelo.ppf().partes());
            entity.setPpf(ppfEntity);
        }

        if (modelo.detailing() != null) {
            DetailingEntity detailingEntity = new DetailingEntity();
            detailingEntity.setTipo(modelo.detailing().tipo());
            detailingEntity.setPartes(modelo.detailing().partes());
            entity.setDetailing(detailingEntity);
        }

        if (modelo.protector() != null) {
            ProtectorEntity protectorEntity = new ProtectorEntity();
            protectorEntity.setAcabado(modelo.protector().acabado());
            protectorEntity.setPartes(modelo.protector().partes());
            entity.setProtector(protectorEntity);
        }

        return entity;
    }
}
