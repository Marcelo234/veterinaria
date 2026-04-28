package com.veterinaria.paciente.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PacienteRequest {

    @NotBlank(message = "El nombre del paciente es obligatorio")
    private String nombre;

    @NotBlank(message = "La especie es obligatoria")
    private String especie;

    private String raza;

    private LocalDate fechaNacimiento;

    @NotBlank(message = "El nombre del propietario es obligatorio")
    private String nombrePropietario;

    @NotBlank(message = "El teléfono del propietario es obligatorio")
    private String telefonoPropietario;
}
