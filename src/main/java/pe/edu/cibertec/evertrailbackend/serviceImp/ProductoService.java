package pe.edu.cibertec.evertrailbackend.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.evertrailbackend.entidad.Producto;
import pe.edu.cibertec.evertrailbackend.repositorio.ProductoRepository;
import pe.edu.cibertec.evertrailbackend.serviceImp.IMP.ICRUDImp;

@Service
public class ProductoService extends ICRUDImp<Producto, Long> {

    @Autowired
    private ProductoRepository repo;

    @Override
    public JpaRepository<Producto, Long> getJpaRepository() {
        return repo;
    }
}