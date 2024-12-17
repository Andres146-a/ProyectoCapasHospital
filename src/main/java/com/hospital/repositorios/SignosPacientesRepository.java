package com.hospital.repositorios;

import com.hospital.modelos.SignosPaciente;
import java.util.List;

public interface SignosPacientesRepository {
    void guardarSignosPaciente(SignosPaciente signosPaciente);
    void actualizarSignosPaciente(SignosPaciente signosPaciente);
    void eliminarSignosPaciente(int idSignosPaciente);
    SignosPaciente buscarPorId(int idSignosPaciente);
    List<SignosPaciente> listarTodos();
}
