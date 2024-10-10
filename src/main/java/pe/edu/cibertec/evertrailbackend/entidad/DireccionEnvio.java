package pe.edu.cibertec.evertrailbackend.entidad;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Indica que esta clase es una entidad de JPA
@Table(name = "direcciones_envio") // Define el nombre de la tabla en la base de datos
@Data // Genera automáticamente getters, setters, toString, hashCode y equals
@NoArgsConstructor // Genera un constructor sin parámetros
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // Evita problemas de referencias circulares en la serialización
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Ignora propiedades de Hibernate que no son necesarias en la serialización
public class DireccionEnvio {

    @Id // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera automáticamente el valor de la clave primaria
    private Long id;

    // Relación muchos a uno con Usuario
    @ManyToOne(fetch = FetchType.LAZY) // Carga perezosa para evitar cargar usuarios innecesariamente
    @JoinColumn(name = "usuario_id", nullable = false) // Relación con el usuario asociado a la dirección
    private Usuario usuario;

    @Column(name = "linea_direccion1", nullable = false) // Línea principal de la dirección
    private String lineaDireccion1;

    @Column(name = "linea_direccion2") // Línea secundaria de la dirección (opcional)
    private String lineaDireccion2;

    @Column(name = "ciudad", nullable = false) // Ciudad de envío
    private String ciudad;

    @Column(name = "estado", nullable = false) // Estado o región de envío
    private String estado;

    @Column(name = "codigo_postal", nullable = false) // Código postal de envío
    private String codigoPostal;

    @Column(name = "pais", nullable = false) // País de envío
    private String pais;
}
