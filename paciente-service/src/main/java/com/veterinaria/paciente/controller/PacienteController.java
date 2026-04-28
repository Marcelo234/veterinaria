package com.veterinaria.paciente.controller;

import com.veterinaria.paciente.dto.PacienteRequest;
import com.veterinaria.paciente.dto.PacienteResponse;
import com.veterinaria.paciente.service.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<PacienteResponse> crearPaciente(@Valid @RequestBody PacienteRequest request) {
        PacienteResponse response = pacienteService.crearPaciente(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponse> obtenerPaciente(@PathVariable Long id) {
        PacienteResponse response = pacienteService.obtenerPacientePorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponse>> obtenerTodosLosPacientes() {
        List<PacienteResponse> responses = pacienteService.obtenerTodosLosPacientes();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponse> actualizarPaciente(@PathVariable Long id, @Valid @RequestBody PacienteRequest request) {
        PacienteResponse response = pacienteService.actualizarPaciente(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id) {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}
