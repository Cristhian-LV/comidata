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
@Table(name = "entregaDB")
public class EntregaDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEntrega;

    // Relación 1:1 con Pedido (unique = true)
    @NotNull
    @OneToOne(optional = false)
    @JoinColumn(name = "idPedido", unique = true, nullable = false)
    private PedidoDB pedido;

    // FK al Repartidor (Personal)
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "idRepartidor", nullable = false)
    private PersonalDB repartidor;

    @NotNull
    @Column(nullable = false)
    private Double costoDelivery;

    private LocalDateTime fechaHoraSalida;

    private LocalDateTime fechaHoraEntrega;

    @NotNull
    @Column(length = 30, nullable = false)
    private String estadoEntrega;
}