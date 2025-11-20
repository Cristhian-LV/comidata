package pe.edu.upeu.comidata.Modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "productoDB")
public class ProductoDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    @NotNull
    @Column(nullable = false, length = 100)
    private String nombre;

    // NUEVO: Descripción del producto
    @Column(length = 255)
    private String descripcion;

    @NotNull
    @Column(nullable = false)
    private Double precioBase;

    @PositiveOrZero
    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false, length = 20)
    private String estado;

    // Relación N:1 con CATEGORIA
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "idCategoria", nullable = false)
    private CategoriaDB categoria;

    // Relación N:1 con PROMOCION
    @ManyToOne
    @JoinColumn(name = "idPromocion")
    private PromocionDB promocion;
}