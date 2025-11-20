package pe.edu.upeu.comidata.Servicio;

import pe.edu.upeu.comidata.Modelo.PromocionDB;
import java.time.LocalDate;
import java.util.List;

public interface PromocionService extends CrudGenericoService<PromocionDB, Long> {
    List<PromocionDB> findActivePromotions(LocalDate fechaActual);
}