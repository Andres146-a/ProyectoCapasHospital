package com.hospital.negocio;

import com.hospital.modelos.SignoVital;
import com.hospital.repositorios.SignoVitalRepository;
import com.hospital.repositorios.SignoVitalRepositoryMySQL;

import java.util.List;

public class SignoVitalFacade {
    private final SignoVitalRepository repository;

    // Constructor para inyección de dependencias
    public SignoVitalFacade(SignoVitalRepository repository) {
        this.repository = repository;
    }

    // Validar los datos del Signo Vital
    private void validarSignoVital(SignoVital signoVital) {
        if (signoVital.getNombre() == null || signoVital.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del signo vital no puede estar vacío.");
        }
        if (signoVital.getUnidadMedida() == null || signoVital.getUnidadMedida().isEmpty()) {
            throw new IllegalArgumentException("La unidad de medida no puede estar vacía.");
        }
    }

    // Guardar o actualizar un Signo Vital
    public void guardarSignoVital(SignoVital signoVital) {
        validarSignoVital(signoVital);

        if (signoVital.getId() > 0) {
            repository.actualizarSignoVital(signoVital);
        } else {
            repository.guardarSignoVital(signoVital);
        }
    }

    // Obtener todos los signos vitales
    public List<SignoVital> listarSignosVitales() {
        return repository.listarTodos();
    }

    // Buscar signo vital por ID
    public SignoVital buscarSignoVitalPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que 0.");
        }
        return repository.buscarPorId(id);
    }

    // Eliminar un signo vital
    public void eliminarSignoVital(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que 0.");
        }
        repository.eliminarSignoVital(id);
    }
}
