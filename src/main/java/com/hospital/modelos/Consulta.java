package com.hospital.modelos;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Consulta {
    private final IntegerProperty id;
    private final StringProperty doctor;
    private LocalDate fecha;
    private final StringProperty descripcion;
    private Paciente paciente;

    

    // Constructor principal
    public Consulta(int id, Paciente paciente, String doctor, LocalDate fecha, String descripcion) {
        this.id = new SimpleIntegerProperty(id);
        this.paciente = paciente;
        this.doctor = new SimpleStringProperty(doctor);
        this.fecha = fecha;
        this.descripcion = new SimpleStringProperty(descripcion);
    }

    // Constructor secundario ajustado
    // public Consulta(int i, Paciente paciente1, LocalDate now, String doctor, String descripcion, String string3) {
    //     this.id = new SimpleIntegerProperty(i);
    //     this.paciente = paciente1;
    //     this.fecha = now;
    //     this.doctor = new SimpleStringProperty(doctor);
    //     this.descripcion = new SimpleStringProperty(descripcion);
    // }

    // Métodos Property para TableView
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty doctorProperty() {
        return doctor;
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    // Getters y Setters
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getDoctor() {
        return doctor.get();
    }

    public void setDoctor(String doctor) {
        this.doctor.set(doctor);
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "id=" + id.get() +
                ", paciente=" + paciente +
                ", doctor='" + doctor.get() + '\'' +
                ", fecha=" + fecha +
                ", descripcion='" + descripcion.get() + '\'' +
                '}';
    }
}
