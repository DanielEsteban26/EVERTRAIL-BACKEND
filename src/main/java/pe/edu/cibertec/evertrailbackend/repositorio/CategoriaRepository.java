package pe.edu.cibertec.evertrailbackend.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.cibertec.evertrailbackend.entidad.Categoria;


public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
