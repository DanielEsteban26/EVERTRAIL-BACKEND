package pe.edu.cibertec.evertrailbackend.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.evertrailbackend.entidad.CarritoDetalle;
import pe.edu.cibertec.evertrailbackend.repositorio.CarritoDetalleRepository;
import pe.edu.cibertec.evertrailbackend.serviceImp.IMP.ICRUDImp;

@Service
public class CarritoDetalleService extends ICRUDImp<CarritoDetalle, Long> {

    @Autowired
    private CarritoDetalleRepository repo;

    @Override
    public JpaRepository<CarritoDetalle, Long> getJpaRepository() {
        return repo;
    }
}
