package pe.edu.upeu.comidata.Modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.validation.constraints.NotNull;
import java.sql.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "personalDB")
public class PersonalDB {

    // PK independiente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersonal;

    // FK a Usuario (RELACIÓN OPCIONAL: unique = true, nullable = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", unique = true)
    private UsuarioDB usuario;

    @NotNull
    @Column(nullable = false, length = 15, unique = true)
    private String dni;

    @NotNull
    @Column(nullable = false, length = 100)
    private String nombreCompleto;

    @Column(length = 15)
    private String telefono;

    @Column(length = 100)
    private String email;

    @Column(length = 255)
    private String direccionActual;

    private Date fechaContratacion;

    @NotNull
    @Column(nullable = false, length = 20)
    private String estado;

    private Date fechaFinTrabajo;
}