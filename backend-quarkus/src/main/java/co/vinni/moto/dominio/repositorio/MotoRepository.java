package co.vinni.moto.dominio.repositorio;
import co.vinni.moto.dominio.modelo.Moto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MotoRepository implements PanacheRepository<Moto> {
}