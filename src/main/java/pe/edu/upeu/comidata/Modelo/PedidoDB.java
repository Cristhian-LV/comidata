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
@Table(name = "pedidoDB")
public class PedidoDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    // FK al cliente (Puede ser Nulo si es una venta anónima/rápida)
    @ManyToOne
    @JoinColumn(name = "idCliente")
    private ClienteDB cliente;

    // FK al Usuario que registró el pedido (cliente online o personal/vendedor)
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "idUsuarioGenerador", nullable = false)
    private UsuarioDB usuarioGenerador;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @NotNull
    @Column(length = 20, nullable = false)
    private String tipoEntrega;

    @NotNull
    @Column(length = 20, nullable = false)
    private String estadoPedido;

    @NotNull
    @Column(nullable = false)
    private Double montoTotal;

    @NotNull
    @Column(length = 20, nullable = false)
    private String tipoDocumento; // Boleta o Factura

    @Column(length = 50)
    private String numeroDocumento;
}