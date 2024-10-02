package pe.edu.cibertec.evertrailbackend.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.evertrailbackend.entidad.ResenaProducto;
import pe.edu.cibertec.evertrailbackend.repositorio.ReseñaProductoRepository;
import pe.edu.cibertec.evertrailbackend.serviceImp.IMP.ICRUDImp;

@Service
public class ResenaProductoService extends ICRUDImp<ResenaProducto, Long> {

    @Autowired
    private ReseñaProductoRepository repo;

    @Override
    public JpaRepository<ResenaProducto, Long> getJpaRepository() {
        return repo;
    }
}
