package pe.edu.upeu.comidata.Servicio;

import java.util.List;
import java.util.Optional;

/**
 * Define las operaciones CRUD básicas que deben implementar todos los servicios.
 * @param <T> La entidad del modelo (ej: RolDB, ProductoDB).
 * @param <ID> El tipo de la clave primaria (ej: Long).
 */
public interface CrudGenericoService<T, ID> {

    T save(T entity);

    List<T> saveAll(List<T> entities);

    Optional<T> findById(ID id);

    List<T> findAll();

    boolean existsById(ID id);

    long count();

    void deleteById(ID id);

    void delete(T entity);

    void deleteAll();
}