// src/main/java/co/vinni/moto/infraestructura/dto/DisenoDto.java

package co.vinni.moto.infraestructura.dto;

import java.util.List;

public record DisenoDto(
    String tipoDiseno,
    String nombreReferencia,
    Boolean personalizado,
    List<String> partes
) {}
