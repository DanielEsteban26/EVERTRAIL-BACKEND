package pe.edu.cibertec.evertrailbackend.entidad;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Indica que esta clase es una entidad de JPA
@Table(name = "imagenes_productos") // Define el nombre de la tabla en la base de datos
@Data // Genera automáticamente getters, setters, toString, hashCode y equals
@NoArgsConstructor // Genera un constructor sin parámetros
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // Evita problemas de referencias circulares en la serialización
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Ignora propiedades de Hibernate que no son necesarias en la serialización
public class ImagenProducto {

    @Id // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera automáticamente el valor de la clave primaria
    private Long id;

    // Relación muchos a uno con Producto
    @ManyToOne(fetch = FetchType.LAZY) // Carga perezosa para evitar cargar productos innecesariamente
    @JoinColumn(name = "producto_id", nullable = false) // Relación con el producto asociado a la imagen
    private Producto producto;

    @Column(name = "url_imagen", nullable = false) // Campo para la URL de la imagen
    private String urlImagen;
}
