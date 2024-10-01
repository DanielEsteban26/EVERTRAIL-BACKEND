package pe.edu.cibertec.evertrailbackend.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.cibertec.evertrailbackend.entidad.ImagenProducto;

public interface ImagenProductoRepository extends JpaRepository<ImagenProducto, Long> {
}