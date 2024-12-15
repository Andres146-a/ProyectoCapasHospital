package com.hospital.modelos;

public class Enfermero {
    private int id;
    private String nombre;
    private String especialidad;

    public Enfermero(int id, String nombre, String especialidad) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que 0.");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (especialidad == null || especialidad.isEmpty()) {
            throw new IllegalArgumentException("La especialidad no puede estar vacía.");
        }
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("El ID debe ser mayor que 0.");
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        if (especialidad == null || especialidad.isEmpty()) {
            throw new IllegalArgumentException("La especialidad no puede estar vacía.");
        }
        this.especialidad = especialidad;
    }

    @Override
    public String toString() {
        return "Enfermero{id=" + id + ", nombre='" + nombre + "', especialidad='" + especialidad + "'}";
    }
}
