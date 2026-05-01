// src/main/java/co/vinni/moto/infraestructura/dto/ProtectorDto.java

package co.vinni.moto.infraestructura.dto;

import java.util.List;

public record ProtectorDto(
    String acabado,
    List<String> partes
) {}
