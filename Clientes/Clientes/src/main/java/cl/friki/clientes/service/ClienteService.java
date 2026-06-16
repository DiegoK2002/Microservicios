package cl.friki.clientes.service;

import cl.friki.clientes.dto.ClienteDTO;
import cl.friki.clientes.model.Cliente;
import cl.friki.clientes.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // ── Listar todos ──────────────────────────────────────────────────────────
    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ── Buscar por ID ─────────────────────────────────────────────────────────
    public Optional<ClienteDTO> findById(Long id) {
        return clienteRepository.findById(id).map(this::toDTO);
    }

    // ── Buscar por RUT ────────────────────────────────────────────────────────
    public Optional<ClienteDTO> findByRut(String rut) {
        return clienteRepository.findByRut(rut).map(this::toDTO);
    }

    // ── Crear ─────────────────────────────────────────────────────────────────
    public ClienteDTO save(ClienteDTO dto) {
        return toDTO(clienteRepository.save(toEntity(dto)));
    }

    // ── Actualizar ────────────────────────────────────────────────────────────
    public Optional<ClienteDTO> update(Long id, ClienteDTO dto) {
        return clienteRepository.findById(id).map(existing -> {
            existing.setNombre(dto.getNombre());
            existing.setApellido(dto.getApellido());
            existing.setEmail(dto.getEmail());
            existing.setTelefono(dto.getTelefono());
            existing.setDireccion(dto.getDireccion());
            existing.setRut(dto.getRut());
            return toDTO(clienteRepository.save(existing));
        });
    }

    // ── Eliminar ──────────────────────────────────────────────────────────────
    public boolean delete(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ── Conversión Entity <-> DTO ─────────────────────────────────────────────
    private ClienteDTO toDTO(Cliente c) {
        return new ClienteDTO(
                c.getId(),
                c.getNombre(),
                c.getApellido(),
                c.getEmail(),
                c.getTelefono(),
                c.getDireccion(),
                c.getRut()
        );
    }

    private Cliente toEntity(ClienteDTO dto) {
        return new Cliente(
                dto.getId(),
                dto.getNombre(),
                dto.getApellido(),
                dto.getEmail(),
                dto.getTelefono(),
                dto.getDireccion(),
                dto.getRut()
        );
    }
}
