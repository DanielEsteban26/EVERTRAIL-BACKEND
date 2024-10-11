package pe.edu.cibertec.evertrailbackend.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.cibertec.evertrailbackend.entidad.Carrito;


public interface CarritoRepository extends JpaRepository<Carrito, Long> {
}
