package pe.edu.upeu.conceptos_poo.saborsistemas.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor; // Útil para usar 'final'
import pe.edu.upeu.conceptos_poo.saborsistemas.utils.Constantes;

@Data
@Builder
public class ViewConfig {

    // CAMPOS REQUERIDOS (final):
    // Obligan al usuario del builder a especificar estos valores.
    private final String fxmlPath; // Ruta del archivo FXML (e.g., "/fxml/Login.fxml")
    private final String title;    // Título de la ventana

    // CAMPOS OPCIONALES (con @Builder.Default):
    // Usan este valor si no se especifican al llamar al builder.

    @Builder.Default
    private String iconPath = Constantes.ic_comidata; // Ruta del ícono (e.g., "/img/app_icon.png")

    @Builder.Default
    private boolean resizable = true; // Si la ventana se puede redimensionar

    @Builder.Default
    private boolean centerOnScreen = true; // Si se centra automáticamente al cargar

    @Builder.Default
    private boolean isModal = false;
}