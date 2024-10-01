package pe.edu.cibertec.evertrailbackend.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.evertrailbackend.entidad.MetodoPago;
import pe.edu.cibertec.evertrailbackend.repositorio.MetodoPagoRepository;
import pe.edu.cibertec.evertrailbackend.serviceImp.IMP.ICRUDImp;

@Service
public class MetodoPagoService extends ICRUDImp<MetodoPago, Long> {

    @Autowired
    private MetodoPagoRepository repo;

    @Override
    public JpaRepository<MetodoPago, Long> getJpaRepository() {
        return repo;
    }
}