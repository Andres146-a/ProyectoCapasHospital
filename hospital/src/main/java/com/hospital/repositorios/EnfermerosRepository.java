package com.hospital.repositorios;

import com.hospital.modelos.Enfermero;
import java.util.List;

public interface EnfermerosRepository {
    void guardarEnfermero(Enfermero enfermero);
    void actualizarEnfermero(Enfermero enfermero);
    void eliminarEnfermero(int idEnfermero);
    Enfermero buscarPorId(int idEnfermero);
    List<Enfermero> listarTodos();
}
