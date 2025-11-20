package pe.edu.upeu.comidata.Servicio.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.comidata.Servicio.CrudGenericoService;
import java.util.List;
import java.util.Optional;

/**
 * Implementación base para las operaciones CRUD genéricas.
 * @param <T> La entidad del modelo.
 * @param <ID> El tipo de la clave primaria.
 */
public abstract class CrudGenericoServiceImp<T, ID> implements CrudGenericoService<T, ID> {

    protected abstract JpaRepository<T, ID> getRepository();

    @Override
    public T save(T entity) {
        return getRepository().save(entity);
    }

    @Override
    public List<T> saveAll(List<T> entities) {
        return getRepository().saveAll(entities);
    }

    @Override
    public Optional<T> findById(ID id) {
        return getRepository().findById(id);
    }

    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public boolean existsById(ID id) {
        return getRepository().existsById(id);
    }

    @Override
    public long count() {
        return getRepository().count();
    }

    @Override
    public void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    @Override
    public void delete(T entity) {
        getRepository().delete(entity);
    }

    @Override
    public void deleteAll() {
        getRepository().deleteAll();
    }
}