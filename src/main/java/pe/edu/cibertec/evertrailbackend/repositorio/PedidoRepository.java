package pe.edu.cibertec.evertrailbackend.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.cibertec.evertrailbackend.entidad.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
