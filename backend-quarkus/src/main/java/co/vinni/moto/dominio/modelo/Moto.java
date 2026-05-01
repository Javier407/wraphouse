// src/main/java/co/vinni/motos/dominio/modelo/Moto.java
// ⚠️ ACTUALIZACIÓN: Se agregó campo fotoUrl

package co.vinni.moto.dominio.modelo;
import java.time.LocalDateTime;
import jakarta.persistence.*;


@Entity
@Table(name = "moto")
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String placa;
    public String marca;
    public String modelo;
    public String color;
    public String cliente;

    @Column(name = "foto_url")
    public String fotoUrl;

    @Column(name = "fecha_registro")
    public LocalDateTime fechaRegistro;

    public Moto() {}
}