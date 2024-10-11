package pe.edu.cibertec.evertrailbackend.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.evertrailbackend.entidad.Carrito;

import pe.edu.cibertec.evertrailbackend.repositorio.CarritoRepository;
import pe.edu.cibertec.evertrailbackend.serviceImp.IMP.ICRUDImp;

@Service
public class CarritoService extends ICRUDImp<Carrito, Long> {

    @Autowired
    private CarritoRepository repo;

    @Override
    public JpaRepository<Carrito, Long> getJpaRepository() {
        return repo;
    }
}
