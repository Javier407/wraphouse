package co.vinni.moto.dominio.modelo;

import java.time.LocalDateTime;

/**
 * Objeto de dominio puro para Moto. Sin anotaciones de persistencia.
 * La persistencia se delega a MotoEntity en infraestructura.
 */
public record Moto(
    Long id,
    String placa,
    String marca,
    String modelo,
    String color,
    String cliente,
    String fotoUrl,
    LocalDateTime fechaRegistro
) {}
