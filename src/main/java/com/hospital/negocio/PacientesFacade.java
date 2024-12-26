package com.hospital.negocio;

import com.hospital.modelos.Paciente;
import com.hospital.modelos.TomaSignos;
import com.hospital.repositorios.PacientesRepository;
import com.hospital.repositorios.PacientesRepositoryMySQL;

import java.util.*;
import java.util.stream.Collectors;

public class PacientesFacade {
    private final AlertasFacade alertasFacade = new AlertasFacade();
    private final PacientesRepository repository;

    // Constructor principal que recibe un repositorio
    public PacientesFacade(PacientesRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("El repositorio de pacientes no puede ser nulo.");
        }
        this.repository = repository;
    }

    // Constructor por defecto para inicializar con la implementación MySQL
    public PacientesFacade() {
        this(new PacientesRepositoryMySQL());
    }
    public List<Paciente> buscarPacientesConAntecedentes() {
        return repository.listarPacientesConAntecedentes();
    }
    
    public String generarAlerta(Paciente paciente, Map<String, String> parametros) {
        return alertasFacade.generarAlerta(paciente, parametros);
    }

    public List<Paciente> listarPacientesActivos() {
        return repository.listarActivos();
    }

    private void validarPaciente(Paciente paciente) {
        if (paciente.getNombre() == null || paciente.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del paciente no puede estar vacío.");
        }
        if (paciente.getApellido() == null || paciente.getApellido().isEmpty()) {
            throw new IllegalArgumentException("El apellido del paciente no puede estar vacío.");
        }
        if (paciente.getEdad() <= 0) {
            throw new IllegalArgumentException("La edad del paciente debe ser válida.");
        }
        if (paciente.getEnfermedades() == null || paciente.getEnfermedades().isEmpty()) {
            throw new IllegalArgumentException("El paciente debe tener al menos una enfermedad registrada.");
        }
    }

    public static Map<String, String> obtenerParametrosConfigurados() {
        Map<String, String> parametros = new HashMap<>();
        parametros.put("frecuenciaMin", "60");
        parametros.put("frecuenciaMax", "100");
        parametros.put("presionSistolica", "140");
        parametros.put("presionDiastolica", "90");
        parametros.put("temperaturaMax", "38.0");
        return parametros;
    }

    public int guardarPaciente(Paciente paciente) {
        validarPaciente(paciente);

        if (paciente.getId() > 0) {
            repository.actualizarPaciente(paciente);
            return paciente.getId();
        } else {
            return repository.guardarPaciente(paciente);
        }
    }

    public List<Paciente> obtenerTodosLosPacientes() {
        return repository.listarTodos();
    }

    public Paciente buscarPacientePorId(int idPaciente) {
        System.out.println("Llamando a buscarPacientePorId con ID: " + idPaciente);

        if (idPaciente <= 0) {
            throw new IllegalArgumentException("El ID del paciente debe ser válido.");
        }

        Paciente paciente = repository.buscarPorId(idPaciente);
        if (paciente == null) {
            System.out.println("Paciente no encontrado con ID: " + idPaciente);
        } else {
            System.out.println("Paciente encontrado en la fachada: " + paciente.getNombre() + " " + paciente.getApellido());
            System.out.println("Antecedentes del paciente: " + paciente.getAntecedentes());
        }

        return paciente;
    }

    public List<Paciente> obtenerPacientesConAlerta() {
        return repository.listarTodos().stream()
                .filter(p -> p.getAlerta() != null && !p.getAlerta().isEmpty())
                .collect(Collectors.toList());
    }

    public void eliminarPaciente(int idPaciente) {
        if (idPaciente <= 0) {
            throw new IllegalArgumentException("El ID del paciente debe ser válido.");
        }
        repository.eliminarPaciente(idPaciente);
    }

    public List<Paciente> buscarPacientesPorNombre(String nombre) {
        System.out.println("Llamando a buscarPacientesPorNombre con nombre: " + nombre);

        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre a buscar no puede estar vacío.");
        }

        List<Paciente> pacientes = repository.listarTodos().stream()
            .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
            .collect(Collectors.toList());

        System.out.println("Pacientes encontrados: " + pacientes.size());
        for (Paciente p : pacientes) {
            System.out.println("Paciente: " + p.getNombre() + ", Antecedentes: " + p.getAntecedentes());
        }

        return pacientes;
    }

    public List<Paciente> buscarPacientesPorEnfermedad(String enfermedad) {
        if (enfermedad == null || enfermedad.isEmpty()) {
            throw new IllegalArgumentException("La enfermedad a buscar no puede estar vacía.");
        }
        return repository.listarTodos().stream()
                .filter(p -> p.getEnfermedades().toLowerCase().contains(enfermedad.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void actualizarAlertaPaciente(int idPaciente, String alerta) {
        if (idPaciente <= 0) {
            throw new IllegalArgumentException("El ID del paciente debe ser válido.");
        }
        if (alerta == null || alerta.isEmpty()) {
            throw new IllegalArgumentException("La alerta no puede estar vacía.");
        }

        Paciente paciente = repository.buscarPorId(idPaciente);
        if (paciente != null) {
            paciente.setAlerta(alerta);
            repository.actualizarPaciente(paciente);
        } else {
            throw new IllegalArgumentException("Paciente no encontrado con el ID proporcionado.");
        }
    }

    public List<Paciente> listarActivos() {
        return repository.listarActivos();
    }

    public void cambiarEstado(int idPaciente, boolean activo) {
        if (idPaciente <= 0) {
            throw new IllegalArgumentException("El ID del paciente debe ser válido.");
        }
        repository.cambiarEstado(idPaciente, activo);
    }

    public List<Paciente> listarInactivos() {
        return repository.listarInactivos();
    }

    public List<Paciente> listarPacientesEnRiesgo() {
        List<Paciente> pacientesActivos = repository.listarActivos();

        return pacientesActivos.stream()
                .filter(this::esPacienteEnRiesgo)
                .collect(Collectors.toList());
    }

    private boolean esPacienteEnRiesgo(Paciente paciente) {
        try {
            int frecuenciaCardiaca = Integer.parseInt(paciente.getFrecuenciaCardiaca().replaceAll("\\D", ""));
            if (frecuenciaCardiaca > 120) {
                return true;
            }

            String[] presionArterial = paciente.getPresionArterial().split("/");
            int presionSistolica = Integer.parseInt(presionArterial[0].replaceAll("\\D", ""));
            if (presionSistolica > 140) {
                return true;
            }

            double temperatura = Double.parseDouble(paciente.getTemperatura().replaceAll(",", "."));
            if (temperatura > 38.0) {
                return true;
            }
        } catch (Exception e) {
            System.err.println("Error al evaluar riesgo para el paciente: " + paciente.getNombre() + ". " + e.getMessage());
        }

        return false;
    }

    public List<TomaSignos> obtenerSignosVitalesPorPaciente(int pacienteId) {
    if (pacienteId <= 0) {
        throw new IllegalArgumentException("El ID del paciente debe ser válido.");
    }
    return repository.obtenerSignosVitales(pacienteId);
}

    public void actualizarAlerta(int idPaciente, String alerta) {
        if (idPaciente <= 0) {
            throw new IllegalArgumentException("El ID del paciente debe ser válido.");
        }
        if (alerta == null || alerta.trim().isEmpty()) {
            throw new IllegalArgumentException("La alerta no puede estar vacía.");
        }
    
        try {
            repository.actualizarAlerta(idPaciente, alerta);
            System.out.println("Alerta actualizada correctamente para el paciente con ID: " + idPaciente);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la alerta del paciente: " + e.getMessage(), e);
        }
    }
    
}
