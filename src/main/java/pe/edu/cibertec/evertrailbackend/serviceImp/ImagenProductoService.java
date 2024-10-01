package pe.edu.cibertec.evertrailbackend.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.evertrailbackend.entidad.ImagenProducto;
import pe.edu.cibertec.evertrailbackend.repositorio.ImagenProductoRepository;
import pe.edu.cibertec.evertrailbackend.serviceImp.IMP.ICRUDImp;

@Service
public class ImagenProductoService extends ICRUDImp<ImagenProducto, Long> {

    @Autowired
    private ImagenProductoRepository repo;

    @Override
    public JpaRepository<ImagenProducto, Long> getJpaRepository() {
        return repo;
    }
}

