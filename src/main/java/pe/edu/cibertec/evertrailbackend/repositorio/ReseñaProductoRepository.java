package pe.edu.cibertec.evertrailbackend.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.cibertec.evertrailbackend.entidad.ResenaProducto;

public interface ReseñaProductoRepository extends JpaRepository<ResenaProducto, Long> {
}
