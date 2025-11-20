package pe.edu.upeu.comidata.Modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "clienteDB")
public class ClienteDB {
    // PK independiente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    // FK a Usuario (RELACIÓN OPCIONAL: unique = true, nullable = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", unique = true)
    private UsuarioDB usuario;

    @Column(length = 15)
    private String dni;

    @Column(length = 100)
    private String nombreCompleto;

    @Column(length = 15)
    private String telefono;

    @Column(length = 100)
    private String email;

    @Column(length = 255)
    private String ultimaDireccion;

    private Integer puntosFidelidad = 0;
}