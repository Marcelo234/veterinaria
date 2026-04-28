package com.veterinaria.paciente.service;

import com.veterinaria.paciente.dto.PacienteRequest;
import com.veterinaria.paciente.dto.PacienteResponse;

import java.util.List;

public interface PacienteService {
    PacienteResponse crearPaciente(PacienteRequest request);
    PacienteResponse obtenerPacientePorId(Long id);
    List<PacienteResponse> obtenerTodosLosPacientes();
    PacienteResponse actualizarPaciente(Long id, PacienteRequest request);
    void eliminarPaciente(Long id);
}
