package pe.edu.cibertec.evertrailbackend.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.evertrailbackend.entidad.Categoria;
import pe.edu.cibertec.evertrailbackend.repositorio.CategoriaRepository;
import pe.edu.cibertec.evertrailbackend.serviceImp.IMP.ICRUDImp;


@Service
public class CategoriaService extends ICRUDImp<Categoria, Long> {

    @Autowired
    private CategoriaRepository repo;

    @Override
    public JpaRepository<Categoria, Long> getJpaRepository() {
        return repo;
    }
}
