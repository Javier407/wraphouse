// src/main/java/co/vinni/motos/dominio/Protector.java

package co.vinni.moto.dominio.modelo;

import java.util.List;

/**
 * Value Object para Protectores.
 */
public record Protector(
    Long id,
    String acabado,         // "mate" o "brillante"
    List<String> partes     // Partes protegidas
) {
    public static Protector crear(String acabado, List<String> partes) {
        return new Protector(null, acabado, partes);
    }
}
