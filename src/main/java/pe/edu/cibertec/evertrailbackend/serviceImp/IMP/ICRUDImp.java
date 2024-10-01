package pe.edu.cibertec.evertrailbackend.serviceImp.IMP;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.cibertec.evertrailbackend.services.ICRUD;


import java.util.Set;

public abstract class ICRUDImp<T,ID> implements ICRUD<T,ID> {

    public abstract JpaRepository<T, ID> getJpaRepository();


    @Override
    public T registrar(T bean) throws Exception {
        return null;
    }

    @Override
    public T actualizar(T bean) throws Exception {
        return null;
    }

    @Override
    public T eliminar(ID id) throws Exception {
        return null;
    }

    @Override
    public T buscar(ID id) throws Exception {
        return null;
    }

    @Override
    public Set<T> listar() throws Exception {
        return null;
    }
}
