package pe.edu.cibertec.evertrailbackend.services;


import java.util.Set;

public interface ICRUD<T,ID> {
    T registrar(T bean) throws Exception;
    T actualizar(T bean) throws Exception;
    T eliminar(ID id) throws Exception;
    T buscar(ID id) throws Exception;
    Set<T> listar() throws Exception;
}
