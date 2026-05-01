// src/main/java/co/vinni/motos/aplicacion/ServicioServicio.java

package co.vinni.moto.aplicacion;

import co.vinni.moto.dominio.modelo.*;
import co.vinni.moto.dominio.repositorio.ServicioRepositorio;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import java.time.LocalDateTime;
import java.util.List;

import static co.vinni.moto.dominio.modelo.TipoServicio.DISENO;
import static co.vinni.moto.dominio.modelo.TipoServicio.PPF;

/**
 * Servicio de aplicación para gestión de servicios.
 * Contiene la lógica de negocio y validaciones.
 */
@ApplicationScoped
public class ServicioServicio {

    @Inject
    ServicioRepositorio repositorio;

    @Transactional
    public Servicio registrarServicio(Servicio servicio) {
        // Validaciones de negocio
        validarServicio(servicio);
        
        // Asignar fecha si no viene
        if (servicio.fecha() == null) {
            servicio = new Servicio(
                servicio.id(),
                servicio.motoId(),
                servicio.tipoServicio(),
                LocalDateTime.now(),
                servicio.diseno(),
                servicio.ppf(),
                servicio.detailing(),
                servicio.protector()
            );
        }
        
        return repositorio.guardar(servicio);
    }

    public List<Servicio> listarServiciosDeMoto(Long motoId) {
        if (motoId == null || motoId <= 0) {
            throw new BadRequestException("ID de moto inválido");
        }
        return repositorio.listarPorMoto(motoId);
    }

    private void validarServicio(Servicio servicio) {
        if (servicio.motoId() == null) {
            throw new BadRequestException("El ID de la moto es obligatorio");
        }
        
        if (servicio.tipoServicio() == null) {
            throw new BadRequestException("El tipo de servicio es obligatorio");
        }

        // Validaciones específicas por tipo
        switch (servicio.tipoServicio()) {
            case DISENO -> validarDiseno(servicio.diseno());
            case PPF -> validarPPF(servicio.ppf());
            case DETAILING -> validarDetailing(servicio.detailing());
            case PROTECTOR -> validarProtector(servicio.protector());
        }
    }

    private void validarDiseno(Diseno diseno) {
        if (diseno == null) {
            throw new BadRequestException("Los datos de diseño son obligatorios para este tipo de servicio");
        }
        
        // Debe tener nombre de referencia O ser personalizado
        if ((diseno.nombreReferencia() == null || diseno.nombreReferencia().isBlank()) 
            && !Boolean.TRUE.equals(diseno.personalizado())) {
            throw new BadRequestException("Debe especificar un nombre de referencia o marcarlo como personalizado");
        }
    }

    private void validarPPF(PPF ppf) {
        if (ppf == null) {
            throw new BadRequestException("Los datos de PPF son obligatorios para este tipo de servicio");
        }
        
        if (ppf.acabado() == null || ppf.acabado().isBlank()) {
            throw new BadRequestException("El acabado de PPF es obligatorio");
        }
        
        if (!ppf.acabado().equals("mate") && !ppf.acabado().equals("brillante")) {
            throw new BadRequestException("El acabado debe ser 'mate' o 'brillante'");
        }
        
        if (ppf.kit() == null || ppf.kit() < 1 || ppf.kit() > 3) {
            throw new BadRequestException("El kit debe ser 1, 2 o 3");
        }
    }

    private void validarDetailing(Detailing detailing) {
        if (detailing == null) {
            throw new BadRequestException("Los datos de detailing son obligatorios para este tipo de servicio");
        }
        
        if (detailing.tipo() == null || detailing.tipo().isBlank()) {
            throw new BadRequestException("El tipo de detailing es obligatorio");
        }
    }

    private void validarProtector(Protector protector) {
        if (protector == null) {
            throw new BadRequestException("Los datos de protector son obligatorios para este tipo de servicio");
        }
        
        if (protector.acabado() == null || protector.acabado().isBlank()) {
            throw new BadRequestException("El acabado del protector es obligatorio");
        }
    }
}
