package com.hospital.repositorios;

import com.hospital.modelos.Antecedente;
import com.hospital.modelos.Paciente;

import java.util.List;

public interface AntecedentesRepository {
    void guardarAntecedente(Antecedente antecedente);
    void actualizarAntecedente(Antecedente antecedente);
    void eliminarAntecedente(int idAntecedente);
    List<Antecedente> listarAntecedentesPorPaciente(int idPaciente);
    List<Paciente> listarPacientesConAntecedentes();

}
