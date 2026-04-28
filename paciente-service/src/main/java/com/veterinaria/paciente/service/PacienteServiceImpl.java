package com.veterinaria.paciente.service;

import com.veterinaria.paciente.dto.PacienteRequest;
import com.veterinaria.paciente.dto.PacienteResponse;
import com.veterinaria.paciente.exception.PacienteNotFoundException;
import com.veterinaria.paciente.model.Paciente;
import com.veterinaria.paciente.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    @Override
    public PacienteResponse crearPaciente(PacienteRequest request) {
        Paciente paciente = Paciente.builder()
                .nombre(request.getNombre())
                .especie(request.getEspecie())
                .raza(request.getRaza())
                .fechaNacimiento(request.getFechaNacimiento())
                .nombrePropietario(request.getNombrePropietario())
                .telefonoPropietario(request.getTelefonoPropietario())
                .build();
        
        Paciente savedPaciente = pacienteRepository.save(paciente);
        return mapToResponse(savedPaciente);
    }

    @Override
    public PacienteResponse obtenerPacientePorId(Long id) {
        Paciente paciente = getPacienteById(id);
        return mapToResponse(paciente);
    }

    @Override
    public List<PacienteResponse> obtenerTodosLosPacientes() {
        return pacienteRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PacienteResponse actualizarPaciente(Long id, PacienteRequest request) {
        Paciente paciente = getPacienteById(id);
        
        paciente.setNombre(request.getNombre());
        paciente.setEspecie(request.getEspecie());
        paciente.setRaza(request.getRaza());
        paciente.setFechaNacimiento(request.getFechaNacimiento());
        paciente.setNombrePropietario(request.getNombrePropietario());
        paciente.setTelefonoPropietario(request.getTelefonoPropietario());
        
        Paciente updatedPaciente = pacienteRepository.save(paciente);
        return mapToResponse(updatedPaciente);
    }

    @Override
    public void eliminarPaciente(Long id) {
        Paciente paciente = getPacienteById(id);
        pacienteRepository.delete(paciente);
    }

    private Paciente getPacienteById(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new PacienteNotFoundException("Paciente no encontrado con ID: " + id));
    }

    private PacienteResponse mapToResponse(Paciente paciente) {
        return PacienteResponse.builder()
                .id(paciente.getId())
                .nombre(paciente.getNombre())
                .especie(paciente.getEspecie())
                .raza(paciente.getRaza())
                .fechaNacimiento(paciente.getFechaNacimiento())
                .nombrePropietario(paciente.getNombrePropietario())
                .telefonoPropietario(paciente.getTelefonoPropietario())
                .build();
    }
}
