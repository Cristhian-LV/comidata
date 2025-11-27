package pe.edu.upeu.conceptos_poo.saborsistemas.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upeu.conceptos_poo.saborsistemas.modelos.Cliente;
import pe.edu.upeu.conceptos_poo.saborsistemas.repository.IClienteRepository;
import pe.edu.upeu.conceptos_poo.saborsistemas.repository.ICrudGenericoRepository;
import pe.edu.upeu.conceptos_poo.saborsistemas.service.ClienteInterface;

@Service
@RequiredArgsConstructor
public class ClienteServicioImp extends CRUD_Generico_ServiceImp<Cliente, String> implements ClienteInterface {

    private final IClienteRepository clienteRepository;

    @Override
    protected ICrudGenericoRepository<Cliente, String> getRepository(){
        return clienteRepository;
    }


    @Override
    public Cliente update(String id, Cliente entity) {
        Cliente clienteExistente = clienteRepository.findById(id).orElse(null);

        if (clienteExistente != null) {
            clienteExistente.setNombres(entity.getNombres());
            clienteExistente.setRepLegal(entity.getRepLegal());
            clienteExistente.setTipoDocumento(entity.getTipoDocumento());

            return clienteRepository.save(clienteExistente); // Guarda y actualiza
        }

        throw new RuntimeException("Cliente con DNI/RUC " + id + " no encontrado para actualizar.");
    }
}