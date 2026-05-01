// src/main/java/co/vinni/motos/dominio/Detailing.java

package co.vinni.moto.dominio.modelo;

import java.util.List;

/**
 * Value Object para Detailing / Polichado.
 */
public record Detailing(
    Long id,
    String tipo,            // "general" o "parcial"
    List<String> partes     // Si es parcial, qué partes
) {
    public static Detailing crear(String tipo, List<String> partes) {
        return new Detailing(null, tipo, partes);
    }
}
