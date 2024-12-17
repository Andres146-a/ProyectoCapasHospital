package com.hospital.repositorios;

import com.hospital.modelos.CentroMedico;
import java.util.List;

public interface CentroMedicoRepository {
    void guardarCentroMedico(CentroMedico centroMedico);
    void actualizarCentroMedico(CentroMedico centroMedico);
    void eliminarCentroMedico(int idCentroMedico);
    CentroMedico buscarPorId(int idCentroMedico);
    List<CentroMedico> listarTodos();
}
