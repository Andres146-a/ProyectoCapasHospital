package com.hospital.repositorios;

import com.hospital.modelos.SignoVital;
import java.util.List;

public interface SignoVitalRepository {
    void guardarSignoVital(SignoVital signoVital);
    void actualizarSignoVital(SignoVital signoVital);
    void eliminarSignoVital(int idSignoVital);
    SignoVital buscarPorId(int idSignoVital);
    List<SignoVital> listarTodos();
}
