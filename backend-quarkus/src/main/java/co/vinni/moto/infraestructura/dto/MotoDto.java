// src/main/java/co/vinni/moto/infraestructura/dto/MotoDto.java
// ⚠️ ACTUALIZACIÓN: Se agregó campo fotoUrl

package co.vinni.moto.infraestructura.dto;

/**
 * DTO de entrada para crear una moto.
 */
public record MotoDto(
    String placa,
    String marca,
    String modelo,
    String color,
    String cliente,
    String fotoUrl        // ⭐ NUEVO CAMPO
) {}
