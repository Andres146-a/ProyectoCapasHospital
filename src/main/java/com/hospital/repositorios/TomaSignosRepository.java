package com.hospital.repositorios;

import com.hospital.modelos.TomaSignos;
import java.util.List;

public interface TomaSignosRepository {
    void guardarTomaSignos(TomaSignos tomaSignos);
    List<TomaSignos> listarTodos();
    void actualizarTomaSignos(TomaSignos tomaSignos);
    void eliminarPorId(int id);
    TomaSignos buscarPorId(int id);
    List<TomaSignos> obtenerPorPaciente(String nombrePaciente); 
    List<String> obtenerTodosLosPacientes();
    void guardarTomaSignos(TomaSignos tomaSignos, String nombrePaciente);

}
