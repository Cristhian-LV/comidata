package pe.edu.upeu.comidata.Modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "promocionDB")
public class PromocionDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPromocion;

    @NotNull
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotNull
    @Column(nullable = false, length = 20)
    private String tipoDescuento;

    @NotNull
    @Column(nullable = false)
    private Double valorDescuento;

    @NotNull
    @Column(nullable = false)
    private LocalDate fechaInicio;

    @NotNull
    @Column(nullable = false)
    private LocalDate fechaFin;
}