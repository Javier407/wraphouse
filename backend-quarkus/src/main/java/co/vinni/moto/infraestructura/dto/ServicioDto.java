// src/main/java/co/vinni/motos/infraestructura/dto/ServicioDto.java

package co.vinni.moto.infraestructura.dto;

import co.vinni.moto.dominio.modelo.TipoServicio;
import java.time.LocalDateTime;

/**
 * DTO de request para crear un servicio.
 * Contiene el tipo y los datos específicos según el tipo.
 */
public record ServicioDto(
    Long motoId,
    TipoServicio tipoServicio,
    LocalDateTime fecha,
    DisenoDto diseno,
    PPFDto ppf,
    DetailingDto detailing,
    ProtectorDto protector
) {}
