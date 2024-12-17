package com.hospital.negocio;

import com.hospital.modelos.SignoVital;
import com.hospital.repositorios.SignoVitalRepository;
import com.hospital.repositorios.SignoVitalRepositoryMySQL;

import java.util.List;

public class SignoVitalFacade {
    private final SignoVitalRepository repository = new SignoVitalRepositoryMySQL();

    public void guardarSignoVital(SignoVital signoVital) {
        if (signoVital.getNombre() == null || signoVital.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del signo vital no puede estar vacío.");
        }
        repository.guardarSignoVital(signoVital);
    }

    public List<SignoVital> listarSignosVitales() {
        return repository.listarTodos();
    }
}
