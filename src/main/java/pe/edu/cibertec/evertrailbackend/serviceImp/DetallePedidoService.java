package pe.edu.cibertec.evertrailbackend.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.evertrailbackend.entidad.DetallePedido;
import pe.edu.cibertec.evertrailbackend.repositorio.DetallePedidoRepository;
import pe.edu.cibertec.evertrailbackend.serviceImp.IMP.ICRUDImp;

@Service
public class DetallePedidoService extends ICRUDImp<DetallePedido, Long> {

    @Autowired
    private DetallePedidoRepository repo;

    @Override
    public JpaRepository<DetallePedido, Long> getJpaRepository() {
        return repo;
    }
}
