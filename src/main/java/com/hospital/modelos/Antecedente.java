package com.hospital.modelos;

import com.hospital.repositorios.AntecedentesRepository;

public class Antecedente {
    private final int id;
    private final int idPaciente;
    private final String tipo;
    private final String descripcion;

    public Antecedente(int id, int idPaciente, String tipo, String descripcion) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public Antecedente(int idPaciente, String tipo, String descripcion) {
        this(0, idPaciente, tipo, descripcion); // Constructor para nuevo antecedente
    }

    public int getId() {
        return id;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "Antecedente{" +
                "id=" + id +
                ", idPaciente=" + idPaciente +
                ", tipo='" + tipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
    
}
