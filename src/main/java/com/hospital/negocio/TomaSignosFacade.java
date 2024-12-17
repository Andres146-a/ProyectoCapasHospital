package com.hospital.negocio;

import com.hospital.modelos.TomaSignos;
import com.hospital.repositorios.TomaSignosRepository;
import com.hospital.repositorios.TomaSignosRepositoryMySQL;

import java.util.List;

public class TomaSignosFacade {
    private final TomaSignosRepository repository = new TomaSignosRepositoryMySQL();

    public void registrarTomaSignos(TomaSignos tomaSignos) {
        if (tomaSignos == null) {
            throw new IllegalArgumentException("La toma de signos no puede ser nula.");
        }
        if (tomaSignos.getPulso() <= 0 || tomaSignos.getTemperatura() <= 0) {
            throw new IllegalArgumentException("El pulso y la temperatura deben ser mayores que 0.");
        }
        repository.guardarTomaSignos(tomaSignos);
    }

    public List<TomaSignos> obtenerTodasLasTomas() {
        return repository.listarTodos();
    }
}
