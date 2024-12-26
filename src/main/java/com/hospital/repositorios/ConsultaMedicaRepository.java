package com.hospital.repositorios;
import java.time.LocalDate;
import java.util.List;


import com.hospital.modelos.ConsultaMedica;
public interface ConsultaMedicaRepository {
    void guardarConsulta(ConsultaMedica consultaMedica);
    List<ConsultaMedica> listarTodas();
    List<ConsultaMedica> obtenerPorPaciente(int idPaciente);
    List<ConsultaMedica> obtenerPorFecha(LocalDate fecha);
    ConsultaMedica buscarPorId(int id);
    void actualizarConsulta(ConsultaMedica consultaMedica);
    void eliminarPorId(int id);
    List<ConsultaMedica> buscarPorEnfermedad(String enfermedad);
}

