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
@Table(name = "parametroGlobalDB")
public class ParametroGlobalDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParametro;

    @NotNull
    @Column(nullable = false, length = 50, unique = true)
    private String nombre; // Ejemplo: TASA_IGV, COSTO_DELIVERY

    @NotNull
    @Column(nullable = false, length = 255)
    private String valor; // Ejemplo: 0.18, 5.00 (se parsea a Double/Decimal en el Service Layer)

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private LocalDateTime ultimaModificacion;
}