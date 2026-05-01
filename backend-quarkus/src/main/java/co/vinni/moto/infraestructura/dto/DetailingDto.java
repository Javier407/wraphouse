// src/main/java/co/vinni/moto/infraestructura/dto/DetailingDto.java

package co.vinni.moto.infraestructura.dto;

import java.util.List;

public record DetailingDto(
    String tipo,
    List<String> partes
) {}
