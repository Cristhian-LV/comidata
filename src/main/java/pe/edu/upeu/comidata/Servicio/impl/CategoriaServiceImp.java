package pe.edu.upeu.comidata.Servicio.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.comidata.Modelo.CategoriaDB;
import pe.edu.upeu.comidata.repository.CategoriaRepository;
import pe.edu.upeu.comidata.Servicio.CategoriaService;

@Service
@Transactional
public class CategoriaServiceImp extends CrudGenericoServiceImp<CategoriaDB, Long> implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImp(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    protected JpaRepository<CategoriaDB, Long> getRepository() {
        return categoriaRepository;
    }
}