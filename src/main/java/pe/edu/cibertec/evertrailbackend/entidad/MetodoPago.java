package pe.edu.cibertec.evertrailbackend.entidad;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Indica que esta clase es una entidad de JPA
@Table(name = "metodos_pago") // Define el nombre de la tabla en la base de datos
@Data // Genera automáticamente getters, setters, toString, hashCode y equals
@NoArgsConstructor // Genera un constructor sin parámetros
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // Evita problemas de referencias circulares en la serialización
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Ignora propiedades de Hibernate que no son necesarias en la serialización
public class MetodoPago {

    @Id // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera automáticamente el valor de la clave primaria
    private Long id;

    // Relación muchos a uno con Usuario
    @ManyToOne(fetch = FetchType.LAZY) // Carga perezosa para evitar cargar usuarios innecesariamente
    @JoinColumn(name = "usuario_id", nullable = false) // Relación con el usuario asociado al método de pago
    private Usuario usuario;

    @Column(name = "numero_tarjeta", nullable = false) // Campo para el número de tarjeta
    private String numeroTarjeta;

    @Column(name = "nombre_titular", nullable = false) // Campo para el nombre del titular de la tarjeta
    private String nombreTitular;

    @Column(name = "fecha_expiracion", nullable = false) // Campo para la fecha de expiración de la tarjeta
    private String fechaExpiracion;

    @Column(name = "cvv", nullable = false) // Campo para el código CVV de la tarjeta
    private String cvv;
}
