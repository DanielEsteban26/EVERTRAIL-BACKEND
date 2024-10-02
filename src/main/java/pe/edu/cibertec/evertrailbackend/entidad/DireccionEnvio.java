package pe.edu.cibertec.evertrailbackend.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

/**
 * Entidad que representa la tabla de direcciones de envío.
 */

@Entity
@Table(name = "direcciones_envio")
public class DireccionEnvio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único para cada dirección de envío

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference("usuario-direccionEnvio") // Rompe la recursión en la serialización
    private Usuario usuario; // Usuario al que pertenece la dirección de envío

    @Column(name = "linea_direccion1", nullable = false)
    private String lineaDireccion1; // Primera línea de la dirección

    @Column(name = "linea_direccion2")
    private String lineaDireccion2; // Segunda línea de la dirección (opcional)

    @Column(name = "ciudad", nullable = false)
    private String ciudad; // Ciudad de la dirección

    @Column(name = "estado", nullable = false)
    private String estado; // Estado de la dirección

    @Column(name = "codigo_postal", nullable = false)
    private String codigoPostal; // Código postal de la dirección

    @Column(name = "pais", nullable = false)
    private String pais; // País de la dirección

    // Constructor vacío
    public DireccionEnvio() {
    }

    // Constructor con parámetros
    public DireccionEnvio(Long id, String pais, String codigoPostal, String estado, String ciudad, String lineaDireccion2, String lineaDireccion1, Usuario usuario) {
        this.id = id;
        this.pais = pais;
        this.codigoPostal = codigoPostal;
        this.estado = estado;
        this.ciudad = ciudad;
        this.lineaDireccion2 = lineaDireccion2;
        this.lineaDireccion1 = lineaDireccion1;
        this.usuario = usuario;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getLineaDireccion2() {
        return lineaDireccion2;
    }

    public void setLineaDireccion2(String lineaDireccion2) {
        this.lineaDireccion2 = lineaDireccion2;
    }

    public String getLineaDireccion1() {
        return lineaDireccion1;
    }

    public void setLineaDireccion1(String lineaDireccion1) {
        this.lineaDireccion1 = lineaDireccion1;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
