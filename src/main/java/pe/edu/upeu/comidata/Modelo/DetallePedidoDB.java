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
@Table(name = "detallePedidoDB")
public class DetallePedidoDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "idPedido", nullable = false)
    private PedidoDB pedido;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "idProducto", nullable = false)
    private ProductoDB producto;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer cantidad;

    @NotNull
    @Column(nullable = false)
    private Double precioBaseUnitario;

    @Column(nullable = false)
    private Double descuentoMonto = 0.0;

    @NotNull
    @Column(nullable = false)
    private Double subtotalNeto; // (Precio Base * Cantidad) - DescuentoMonto

    @NotNull
    @Column(nullable = false)
    private Double tasaIgv; // Valor del IGV al momento de la venta

    @NotNull
    @Column(nullable = false)
    private Double igvMonto; // subtotalNeto * tasaIgv

    @NotNull
    @Column(nullable = false)
    private Double subtotal; // subtotalNeto + igvMonto
}