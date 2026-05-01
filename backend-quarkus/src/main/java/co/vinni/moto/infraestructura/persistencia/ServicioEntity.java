package co.vinni.moto.infraestructura.persistencia;

import co.vinni.moto.dominio.modelo.TipoServicio;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "servicio")
public class ServicioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "moto_id", nullable = false)
    private Long motoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_servicio", nullable = false)
    private TipoServicio tipoServicio;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @OneToOne(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private DisenoEntity diseno;

    @OneToOne(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private PPFEntity ppf;

    @OneToOne(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private DetailingEntity detailing;

    @OneToOne(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProtectorEntity protector;

    @PrePersist
    public void prePersist() {
        if (fecha == null) fecha = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMotoId() { return motoId; }
    public void setMotoId(Long motoId) { this.motoId = motoId; }

    public TipoServicio getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(TipoServicio tipoServicio) { this.tipoServicio = tipoServicio; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public DisenoEntity getDiseno() { return diseno; }
    public void setDiseno(DisenoEntity diseno) {
        this.diseno = diseno;
        if (diseno != null) diseno.setServicio(this);
    }

    public PPFEntity getPpf() { return ppf; }
    public void setPpf(PPFEntity ppf) {
        this.ppf = ppf;
        if (ppf != null) ppf.setServicio(this);
    }

    public DetailingEntity getDetailing() { return detailing; }
    public void setDetailing(DetailingEntity detailing) {
        this.detailing = detailing;
        if (detailing != null) detailing.setServicio(this);
    }

    public ProtectorEntity getProtector() { return protector; }
    public void setProtector(ProtectorEntity protector) {
        this.protector = protector;
        if (protector != null) protector.setServicio(this);
    }
}
