package com.hospital.negocio;

import java.util.List;

import com.hospital.modelos.Antecedente;
import com.hospital.modelos.Paciente;
import com.hospital.repositorios.AntecedentesRepository;
import com.hospital.repositorios.AntecedentesRepositoryMySQL;
public class AntecedentesFacade {

    private final AntecedentesRepository repository;

    public AntecedentesFacade() {
        this.repository = new AntecedentesRepositoryMySQL();
    }
    public AntecedentesFacade(AntecedentesRepository repository) {
        this.repository = repository;
    }
    public List<Paciente> listarPacientesConAntecedentes() {
        return repository.listarPacientesConAntecedentes(); // Llama al método del repositorio
    }
    // public AntecedentesFacade(AntecedentesRepository repository) {
    //     if (repository == null) {
    //         throw new IllegalArgumentException("El repositorio no puede ser nulo.");
    //     }
    //     this.repository = repository;
    // }
    public void guardarAntecedente(int idPaciente, String tipo, String descripcion) {
        if (idPaciente <= 0) {
            throw new IllegalArgumentException("El ID del paciente debe ser válido.");
        }
        if (tipo == null || tipo.isEmpty()) {
            throw new IllegalArgumentException("El tipo de antecedente no puede estar vacío.");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }

        Antecedente antecedente = new Antecedente(idPaciente, tipo, descripcion);
        repository.guardarAntecedente(antecedente);
    }
    public List<Antecedente> obtenerAntecedentes(int idPaciente) {
        return repository.listarAntecedentesPorPaciente(idPaciente);
    }
   
   /**
 * Método para actualizar un antecedente.
 * @param idAntecedente ID del antecedente a actualizar.
 * @param tipo Tipo del antecedente.
 * @param nuevaDescripcion Nueva descripción del antecedente.
 */
public void actualizarAntecedente(int idAntecedente, String tipo, String nuevaDescripcion) {
    if (idAntecedente <= 0) {
        throw new IllegalArgumentException("El ID del antecedente debe ser válido.");
    }
    if (tipo == null || tipo.isEmpty()) {
        throw new IllegalArgumentException("El tipo del antecedente no puede estar vacío.");
    }
    if (nuevaDescripcion == null || nuevaDescripcion.isEmpty()) {
        throw new IllegalArgumentException("La nueva descripción no puede estar vacía.");
    }

    // Crear un objeto Antecedente temporal con los datos necesarios para la actualización.
    Antecedente antecedente = new Antecedente(idAntecedente, 0, tipo, nuevaDescripcion);
    repository.actualizarAntecedente(antecedente);
}
public void eliminarAntecedente(int idAntecedente) {
    if (idAntecedente <= 0) {
        throw new IllegalArgumentException("El ID del antecedente debe ser válido.");
    }
    repository.eliminarAntecedente(idAntecedente); // Llamada al repositorio
}
// public List<Paciente> listarPacientesConAntecedentes() {
//     return repository.listarPacientesConAntecedentes(); // Llamada al repositorio
// }

}
