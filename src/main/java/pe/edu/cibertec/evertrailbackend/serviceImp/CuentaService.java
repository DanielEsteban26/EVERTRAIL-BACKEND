package pe.edu.cibertec.evertrailbackend.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.evertrailbackend.entidad.Usuario;
import pe.edu.cibertec.evertrailbackend.repositorio.UsuarioRepository;

@Service
public class CuentaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario getCuenta(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    public Usuario updateCuenta(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}