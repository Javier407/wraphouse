// src/main/java/co/vinni/moto/infraestructura/dto/PPFDto.java

package co.vinni.moto.infraestructura.dto;

import java.util.List;

public record PPFDto(
    String acabado,
    Integer kit,
    List<String> partes
) {}
