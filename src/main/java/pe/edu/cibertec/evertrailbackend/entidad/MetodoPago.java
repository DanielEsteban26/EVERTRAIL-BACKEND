package pe.edu.cibertec.evertrailbackend.entidad;

import jakarta.persistence.*;

/**
 * Entidad que representa la tabla de métodos de pago.
 */
@Entity
@Table(name = "metodos_pago")
public class MetodoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único para cada método de pago

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; // Usuario al que pertenece el método de pago

    @Column(name = "numero_tarjeta", nullable = false)
    private String numeroTarjeta; // Número de la tarjeta de crédito

    @Column(name = "nombre_titular", nullable = false)
    private String nombreTitular; // Nombre del titular de la tarjeta

    @Column(name = "fecha_expiracion", nullable = false)
    private String fechaExpiracion; // Fecha de expiración de la tarjeta

    @Column(name = "cvv", nullable = false)
    private String cvv; // Código de seguridad de la tarjeta

    // Constructor vacío
    public MetodoPago() {
    }

    // Constructor con parámetros
    public MetodoPago(Long id, String cvv, String fechaExpiracion, String nombreTitular, String numeroTarjeta, Usuario usuario) {
        this.id = id;
        this.cvv = cvv;
        this.fechaExpiracion = fechaExpiracion;
        this.nombreTitular = nombreTitular;
        this.numeroTarjeta = numeroTarjeta;
        this.usuario = usuario;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getNombreTitular() {
        return nombreTitular;
    }

    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

