// src/main/java/co/vinni/motos/dominio/Servicio.java

package co.vinni.moto.dominio.modelo;

import java.time.LocalDateTime;

/**
 * Agregado root para los servicios realizados a una moto.
 * Sigue el patrón DDD: el Servicio es el centro y contiene referencias a sus subtipos.
 */
public record Servicio(
    Long id,
    Long motoId,
    co.vinni.moto.dominio.modelo.TipoServicio tipoServicio,
    LocalDateTime fecha,
    Diseno diseno,           // Opcional - solo si tipoServicio == DISENO
    PPF ppf,                 // Opcional - solo si tipoServicio == PPF
    Detailing detailing,     // Opcional - solo si tipoServicio == DETAILING
    Protector protector      // Opcional - solo si tipoServicio == PROTECTOR
) {
    // Constructor builder-style para facilitar creación
    public static Servicio crear(Long motoId, co.vinni.moto.dominio.modelo.TipoServicio tipo, LocalDateTime fecha) {
        return new Servicio(null, motoId, tipo, fecha, null, null, null, null);
    }

    public Servicio conDiseno(Diseno diseno) {
        return new Servicio(id, motoId, tipoServicio, fecha, diseno, ppf, detailing, protector);
    }

    public Servicio conPPF(PPF ppf) {
        return new Servicio(id, motoId, tipoServicio, fecha, diseno, ppf, detailing, protector);
    }

    public Servicio conDetailing(Detailing detailing) {
        return new Servicio(id, motoId, tipoServicio, fecha, diseno, ppf, detailing, protector);
    }

    public Servicio conProtector(Protector protector) {
        return new Servicio(id, motoId, tipoServicio, fecha, diseno, ppf, detailing, protector);
    }
}
