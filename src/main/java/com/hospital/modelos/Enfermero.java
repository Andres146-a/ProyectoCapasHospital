package com.hospital.modelos;

import javafx.beans.property.*;

public class Enfermero {
    private final IntegerProperty id;
    private final StringProperty nombre;
    private final StringProperty especialidad;
    private final StringProperty estado; // Nuevo campo: Estado (Disponible u Ocupado)

    public Enfermero(int id, String nombre, String especialidad, String estado) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que 0.");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (especialidad == null || especialidad.isEmpty()) {
            throw new IllegalArgumentException("La especialidad no puede estar vacía.");
        }
        if (estado == null || estado.isEmpty()) {
            throw new IllegalArgumentException("El estado no puede estar vacío.");
        }

        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.especialidad = new SimpleStringProperty(especialidad);
        this.estado = new SimpleStringProperty(estado);
    }

    // Getters para ID
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("El ID debe ser mayor que 0.");
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    // Getters para Nombre
    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre.set(nombre);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    // Getters para Especialidad
    public String getEspecialidad() {
        return especialidad.get();
    }

    public void setEspecialidad(String especialidad) {
        if (especialidad == null || especialidad.isEmpty()) {
            throw new IllegalArgumentException("La especialidad no puede estar vacía.");
        }
        this.especialidad.set(especialidad);
    }

    public StringProperty especialidadProperty() {
        return especialidad;
    }

    // Getters para Estado
    public String getEstado() {
        return estado.get();
    }

    public void setEstado(String estado) {
        if (estado == null || estado.isEmpty()) {
            throw new IllegalArgumentException("El estado no puede estar vacío.");
        }
        this.estado.set(estado);
    }

    public StringProperty estadoProperty() {
        return estado;
    }

    @Override
    public String toString() {
        return "Enfermero{id=" + id.get() + ", nombre='" + nombre.get() +
                "', especialidad='" + especialidad.get() + "', estado='" + estado.get() + "'}";
    }
}
