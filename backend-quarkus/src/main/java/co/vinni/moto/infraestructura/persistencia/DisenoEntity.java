package co.vinni.moto.infraestructura.persistencia;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "diseno")
public class DisenoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "servicio_id", nullable = false)
    private ServicioEntity servicio;

    @Column(name = "tipo_diseno")
    private String tipoDiseno;

    @Column(name = "nombre_referencia")
    private String nombreReferencia;

    @Column(name = "personalizado")
    private Boolean personalizado;

    @ElementCollection
    @CollectionTable(name = "diseno_partes", joinColumns = @JoinColumn(name = "diseno_id"))
    @Column(name = "parte")
    private List<String> partes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ServicioEntity getServicio() { return servicio; }
    public void setServicio(ServicioEntity servicio) { this.servicio = servicio; }

    public String getTipoDiseno() { return tipoDiseno; }
    public void setTipoDiseno(String tipoDiseno) { this.tipoDiseno = tipoDiseno; }

    public String getNombreReferencia() { return nombreReferencia; }
    public void setNombreReferencia(String nombreReferencia) { this.nombreReferencia = nombreReferencia; }

    public Boolean getPersonalizado() { return personalizado; }
    public void setPersonalizado(Boolean personalizado) { this.personalizado = personalizado; }

    public List<String> getPartes() { return partes; }
    public void setPartes(List<String> partes) { this.partes = partes; }
}
