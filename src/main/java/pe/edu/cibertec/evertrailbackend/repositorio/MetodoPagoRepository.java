package pe.edu.cibertec.evertrailbackend.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.cibertec.evertrailbackend.entidad.MetodoPago;

public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Long> {
}
