package pe.edu.cibertec.evertrailbackend.entidad;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único para cada producto

    @Column(name = "nombre", nullable = false)
    private String nombre; // Nombre del producto, no nulo

    @Column(name = "descripcion")
    private String descripcion; // Descripción del producto

    @Column(name = "precio", nullable = false)
    private Double precio; // Precio del producto, no nulo

    @Column(name = "stock", nullable = false)
    private Integer stock; // Cantidad de stock del producto, no nulo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria; // Categoría a la que pertenece el producto

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ImagenProducto> imagenesProductos; // Conjunto de imágenes del producto

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DetallePedido> detallesPedido; // Conjunto de detalles de pedidos que incluyen este producto

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ResenaProducto> reseñasProductos; // Conjunto de reseñas del producto
}