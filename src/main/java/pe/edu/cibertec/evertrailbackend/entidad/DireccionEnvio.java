package pe.edu.cibertec.evertrailbackend.entidad;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

/**
 * Entidad que representa la tabla de direcciones de envío.
 */
@Entity
@Table(name = "direcciones_envio")
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class DireccionEnvio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único para cada dirección de envío

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
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
}