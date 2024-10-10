package pe.edu.cibertec.evertrailbackend.entidad;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity // Indica que esta clase es una entidad de JPA
@Table(name = "categorias") // Define el nombre de la tabla en la base de datos
@Data // Genera automáticamente getters, setters, toString, hashCode y equals
@NoArgsConstructor // Genera un constructor sin parámetros
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // Evita problemas de referencias circulares en la serialización
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Ignora propiedades de Hibernate que no son necesarias en la serialización
public class Categoria {

    @Id // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera automáticamente el valor de la clave primaria
    private Long id;

    @Column(name = "nombre", unique = true, nullable = false) // Nombre de la categoría, único y no nulo
    private String nombre;

    @Column(name = "descripcion") // Descripción de la categoría (opcional)
    private String descripcion;

    // Relación uno a muchos con Producto
    @OneToMany(mappedBy = "categoria") // Mapeo de la relación inversa
    private Set<Producto> productos; // Conjunto de productos asociados a esta categoría
}
