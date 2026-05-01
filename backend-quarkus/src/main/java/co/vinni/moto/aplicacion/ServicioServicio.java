package co.vinni.moto.aplicacion;

import co.vinni.common.exception.NegocioException;
import co.vinni.moto.dominio.modelo.*;
import co.vinni.moto.dominio.puerto.ServicioRepositorioPuerto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class ServicioServicio {

    private static final Set<String> ACABADOS_VALIDOS = Set.of("mate", "brillante");

    @Inject
    ServicioRepositorioPuerto repositorio;

    @Transactional
    public Servicio registrarServicio(Servicio servicio) {
        validarServicio(servicio);

        if (servicio.fecha() == null) {
            servicio = new Servicio(
                    servicio.id(), servicio.motoId(), servicio.tipoServicio(),
                    LocalDateTime.now(), servicio.diseno(), servicio.ppf(),
                    servicio.detailing(), servicio.protector()
            );
        }

        return repositorio.guardar(servicio);
    }

    public List<Servicio> listarServiciosDeMoto(Long motoId) {
        if (motoId == null || motoId <= 0)
            throw new NegocioException("ID de moto inválido", 400);
        return repositorio.listarPorMoto(motoId);
    }

    // ── Validaciones de negocio ─────────────────────────────────────────────

    private void validarServicio(Servicio servicio) {
        if (servicio.motoId() == null)
            throw new NegocioException("El ID de la moto es obligatorio", 400);
        if (servicio.tipoServicio() == null)
            throw new NegocioException("El tipo de servicio es obligatorio", 400);

        switch (servicio.tipoServicio()) {
            case DISENO    -> validarDiseno(servicio.diseno());
            case PPF       -> validarPPF(servicio.ppf());
            case DETAILING -> validarDetailing(servicio.detailing());
            case PROTECTOR -> validarProtector(servicio.protector());
        }
    }

    private void validarDiseno(Diseno diseno) {
        if (diseno == null)
            throw new NegocioException("Los datos de diseño son obligatorios", 400);
        if ((diseno.nombreReferencia() == null || diseno.nombreReferencia().isBlank())
                && !Boolean.TRUE.equals(diseno.personalizado()))
            throw new NegocioException("Debe especificar un nombre de referencia o marcarlo como personalizado", 400);
    }

    private void validarPPF(PPF ppf) {
        if (ppf == null)
            throw new NegocioException("Los datos de PPF son obligatorios", 400);
        if (ppf.acabado() == null || ppf.acabado().isBlank())
            throw new NegocioException("El acabado de PPF es obligatorio", 400);
        if (!ACABADOS_VALIDOS.contains(ppf.acabado()))
            throw new NegocioException("El acabado debe ser 'mate' o 'brillante'", 400);
        if (ppf.kit() == null || ppf.kit() < 1 || ppf.kit() > 3)
            throw new NegocioException("El kit debe ser 1, 2 o 3", 400);
    }

    private void validarDetailing(Detailing detailing) {
        if (detailing == null)
            throw new NegocioException("Los datos de detailing son obligatorios", 400);
        if (detailing.tipo() == null || detailing.tipo().isBlank())
            throw new NegocioException("El tipo de detailing es obligatorio", 400);
    }

    private void validarProtector(Protector protector) {
        if (protector == null)
            throw new NegocioException("Los datos de protector son obligatorios", 400);
        if (protector.acabado() == null || protector.acabado().isBlank())
            throw new NegocioException("El acabado del protector es obligatorio", 400);
        if (!ACABADOS_VALIDOS.contains(protector.acabado()))
            throw new NegocioException("El acabado debe ser 'mate' o 'brillante'", 400);
    }
}
