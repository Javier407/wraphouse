// src/main/java/co/vinni/motos/infraestructura/dto/MotoRespuestaDto.java
// ⚠️ ACTUALIZACIÓN: Se agregó campo fotoUrl

package co.vinni.moto.infraestructura.dto;

import java.time.LocalDateTime;

/**
 * DTO de respuesta para una moto guardada.
 */
public record MotoRespuestaDto(
    Long id,
    String placa,
    String marca,
    String modelo,
    String color,
    String cliente,
    String fotoUrl,              // ⭐ NUEVO CAMPO
    LocalDateTime fechaRegistro
) {}
