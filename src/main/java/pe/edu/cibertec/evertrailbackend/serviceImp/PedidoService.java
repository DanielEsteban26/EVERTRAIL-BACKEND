package pe.edu.cibertec.evertrailbackend.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.evertrailbackend.entidad.Pedido;
import pe.edu.cibertec.evertrailbackend.repositorio.PedidoRepository;
import pe.edu.cibertec.evertrailbackend.serviceImp.IMP.ICRUDImp;

@Service
public class PedidoService extends ICRUDImp<Pedido, Long> {

    @Autowired
    private PedidoRepository repo;

    @Override
    public JpaRepository<Pedido, Long> getJpaRepository() {
        return repo;
    }
}
