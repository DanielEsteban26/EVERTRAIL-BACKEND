package pe.edu.cibertec.evertrailbackend.entidad;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa la tabla de métodos de pago.
 */
@Entity
@Table(name = "metodos_pago")
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
}