// src/main/java/co/vinni/motos/repositorio/entity/ProtectorEntity.java

package co.vinni.moto.dominio.repositorio;
import jakarta.persistence.*;

@Entity
@Table(name = "protector")
public class ProtectorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "servicio_id", nullable = false)
    private ServicioEntity servicio;

    @Column(name = "acabado")
    private String acabado;

    @ElementCollection
    @CollectionTable(name = "protector_partes", joinColumns = @JoinColumn(name = "protector_id"))
    @Column(name = "parte")
    private java.util.List<String> partes;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ServicioEntity getServicio() { return servicio; }
    public void setServicio(ServicioEntity servicio) { this.servicio = servicio; }

    public String getAcabado() { return acabado; }
    public void setAcabado(String acabado) { this.acabado = acabado; }

    public java.util.List<String> getPartes() { return partes; }
    public void setPartes(java.util.List<String> partes) { this.partes = partes; }
}
