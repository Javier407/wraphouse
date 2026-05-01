// src/main/java/co/vinni/motos/infraestructura/dto/ServicioRespuestaDto.java

package co.vinni.moto.infraestructura.dto;

import co.vinni.moto.infraestructura.dto.DetailingDto;
import co.vinni.moto.infraestructura.dto.DisenoDto;
import co.vinni.moto.infraestructura.dto.PPFDto;
import co.vinni.moto.infraestructura.dto.ProtectorDto;
import co.vinni.moto.dominio.modelo.TipoServicio;
import java.time.LocalDateTime;

/**
 * DTO de respuesta para un servicio guardado.
 */
public record ServicioRespuestaDto(
    Long id,
    Long motoId,
    TipoServicio tipoServicio,
    LocalDateTime fecha,
    DisenoDto diseno,
    PPFDto ppf,
    DetailingDto detailing,
    ProtectorDto protector
) {}
