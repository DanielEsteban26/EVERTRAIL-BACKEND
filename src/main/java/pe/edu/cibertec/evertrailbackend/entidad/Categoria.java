package pe.edu.cibertec.evertrailbackend.entidad;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único para cada categoría

    @Column(name = "nombre", unique = true, nullable = false)
    private String nombre; // Nombre de la categoría, único y no nulo

    @Column(name = "descripcion")
    private String descripcion; // Descripción de la categoría

    @OneToMany(mappedBy = "categoria")
    private Set<Producto> productos; // Conjunto de productos que pertenecen a esta categoría
}