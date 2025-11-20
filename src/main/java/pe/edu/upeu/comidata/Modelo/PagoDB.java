package pe.edu.upeu.comidata.Modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "pagoDB")
public class PagoDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    // Relación 1:1 con Pedido (unique = true)
    @NotNull
    @OneToOne(optional = false)
    @JoinColumn(name = "idPedido", unique = true, nullable = false)
    private PedidoDB pedido;

    @NotNull
    @Column(nullable = false)
    private Double montoPagado;

    @NotNull
    @Column(length = 50, nullable = false)
    private String metodoPago;

    @Column(length = 255)
    private String referenciaPago;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime fechaPago;

    @NotNull
    @Column(length = 20, nullable = false)
    private String estadoPago;
}