package pe.edu.cibertec.evertrailbackend.serviceImp.IMP; // Define el paquete donde se encuentra esta clase

import org.springframework.data.jpa.repository.JpaRepository; // Importa la interfaz JpaRepository de Spring Data JPA
import pe.edu.cibertec.evertrailbackend.services.ICRUD; // Importa la interfaz ICRUD

import java.util.HashSet; // Importa la clase HashSet de Java
import java.util.Set; // Importa la interfaz Set de Java

public abstract class ICRUDImp<T,ID> implements ICRUD<T,ID> { // Define una clase abstracta que implementa la interfaz ICRUD

    public abstract JpaRepository<T, ID> getJpaRepository(); // Método abstracto que debe ser implementado por las subclases para proporcionar el repositorio JPA

    @Override
    public T registrar(T bean) throws Exception { // Implementa el método registrar de ICRUD
        return getJpaRepository().save(bean); // Guarda y retorna la entidad usando el repositorio JPA
    }

    @Override
    public T actualizar(T bean) throws Exception { // Implementa el método actualizar de ICRUD
        return getJpaRepository().save(bean); // Guarda y retorna la entidad usando el repositorio JPA
    }

    @Override
    public T eliminar(ID id) throws Exception { // Implementa el método eliminar de ICRUD
        T entity = getJpaRepository().findById(id).orElse(null); // Busca la entidad por ID, retorna null si no se encuentra
        getJpaRepository().delete(entity); // Elimina la entidad usando el repositorio JPA
        return entity; // Retorna la entidad eliminada
    }

    @Override
    public T buscar(ID id) throws Exception { // Implementa el método buscar de ICRUD
        return getJpaRepository().findById(id).orElseThrow(()-> new Exception("No se encontro el registro")); // Busca la entidad por ID, lanza una excepción si no se encuentra
    }

    @Override
    public Set<T> listar() throws Exception { // Implementa el método listar de ICRUD
        return new HashSet<>(getJpaRepository().findAll()); // Retorna todas las entidades en un HashSet
    }
}
