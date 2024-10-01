package pe.edu.cibertec.evertrailbackend.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.cibertec.evertrailbackend.entidad.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
