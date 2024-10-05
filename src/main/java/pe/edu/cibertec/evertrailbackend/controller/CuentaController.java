package pe.edu.cibertec.evertrailbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.evertrailbackend.entidad.Usuario;
import pe.edu.cibertec.evertrailbackend.serviceImp.CuentaService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/cuenta")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public ResponseEntity<Usuario> getCuenta(@RequestParam String nombreUsuario) {
        try {
            return ResponseEntity.ok(cuentaService.getCuenta(nombreUsuario));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping
    public ResponseEntity<Usuario> updateCuenta(@RequestBody Usuario usuario) {
        try {
            return ResponseEntity.ok(cuentaService.updateCuenta(usuario));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}