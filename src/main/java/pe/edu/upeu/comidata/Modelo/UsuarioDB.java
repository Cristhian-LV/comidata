package pe.edu.upeu.comidata.Modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "usuarioDB")
public class UsuarioDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @NotNull(message = "El nombre de usuario no puede ser nulo")
    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @NotNull(message = "La clave no puede ser nula")
    @Column(nullable = false, length = 100)
    private String clave;

    // Relación Muchos-a-Uno (N:1) con Rol
    @NotNull(message = "El rol no puede ser nulo")
    @ManyToOne(optional = false)
    @JoinColumn(name = "idRol", nullable = false)
    private RolDB rol;

    // Mapeo inverso de la relación 1:1 con PERSONAL (sin cascade)
    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
    private PersonalDB personal;

    // Mapeo inverso de la relación 1:0..1 con CLIENTE (sin cascade)
    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
    private ClienteDB cliente;
}