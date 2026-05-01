// src/main/java/co/vinni/motos/dominio/PPF.java

package co.vinni.moto.dominio.modelo;

import java.util.List;

/**
 * Value Object para PPF (Paint Protection Film).
 */
public record PPF(
    Long id,
    String acabado,         // "mate" o "brillante"
    Integer kit,            // 1, 2 o 3
    List<String> partes     // ["tanque", "faro", "guardafangos"]
) {
    public static PPF crear(String acabado, Integer kit, List<String> partes) {
        return new PPF(null, acabado, kit, partes);
    }

    // Validación en dominio
    public boolean esValido() {
        return acabado != null && 
               (acabado.equals("mate") || acabado.equals("brillante")) &&
               kit != null && kit >= 1 && kit <= 3;
    }
}
