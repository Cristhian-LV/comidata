package pe.edu.upeu.comidata.Modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "carritoDB")
public class CarritoDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrito;

    // FK al Usuario que tiene el carrito
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "idUsuario", nullable = false)
    private UsuarioDB usuario;

    // FK al Producto añadido
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "idProducto", nullable = false)
    private ProductoDB producto;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer cantidad;
}