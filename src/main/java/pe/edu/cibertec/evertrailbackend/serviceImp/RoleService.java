package pe.edu.cibertec.evertrailbackend.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.evertrailbackend.entidad.Role;
import pe.edu.cibertec.evertrailbackend.repositorio.RoleRepository;
import pe.edu.cibertec.evertrailbackend.serviceImp.IMP.ICRUDImp;

@Service
public class RoleService extends ICRUDImp<Role, Long> {

    @Autowired
    private RoleRepository repo;

    @Override
    public JpaRepository<Role, Long> getJpaRepository() {
        return repo;
    }
}
