// src/main/java/co/vinni/motos/dominio/Diseno.java

package co.vinni.moto.dominio.modelo;
import java.util.List;

/**
 * Value Object para diseño.
 * Contiene los datos específicos de un servicio de diseño.
 */
public record Diseno(
    Long id,
    String tipoDiseno,           // Ej: "Tribal", "Llamas", "Geométrico"
    String nombreReferencia,     // Ej: "Monster Energy", "Red Bull Racing"
    Boolean personalizado,       // true si fue diseño exclusivo
    List<String> partes          // Partes trabajadas: ["tanque", "guardafangos", "carenado"]
) {
    public static Diseno crear(String tipoDiseno, String nombreReferencia, Boolean personalizado, List<String> partes) {
        return new Diseno(null, tipoDiseno, nombreReferencia, personalizado, partes);
    }
}
