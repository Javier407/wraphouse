package co.vinni.moto.infraestructura.persistencia;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "detailing")
public class DetailingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "servicio_id", nullable = false)
    private ServicioEntity servicio;

    @Column(name = "tipo")
    private String tipo;

    @ElementCollection
    @CollectionTable(name = "detailing_partes", joinColumns = @JoinColumn(name = "detailing_id"))
    @Column(name = "parte")
    private List<String> partes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ServicioEntity getServicio() { return servicio; }
    public void setServicio(ServicioEntity servicio) { this.servicio = servicio; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public List<String> getPartes() { return partes; }
    public void setPartes(List<String> partes) { this.partes = partes; }
}
