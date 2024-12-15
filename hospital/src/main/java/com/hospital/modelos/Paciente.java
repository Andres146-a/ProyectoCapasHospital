package com.hospital.modelos;

import java.util.ArrayList;
import java.util.List;

public class Paciente {
    private int id;
    private String nombre;
    private int edad;
    private List<TomaSignos> registroS;

    public Paciente(int id, String nombre, int edad) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que 0.");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (edad <= 0) {
            throw new IllegalArgumentException("La edad debe ser mayor que 0.");
        }
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.registroS = new ArrayList<>();
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        if (edad <= 0) throw new IllegalArgumentException("La edad debe ser mayor que 0.");
        this.edad = edad;
    }

    public List<TomaSignos> getRegistroS() {
        return registroS;
    }

    public void agregarRegistro(TomaSignos tomaSignos) {
        if (tomaSignos == null) {
            throw new IllegalArgumentException("El registro de signos no puede ser nulo.");
        }
        registroS.add(tomaSignos);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "Paciente{id=" + id + ", nombre='" + nombre + "', edad=" + edad + ", registrosSignos=" + registroS.size() + "}";
    }

    
}
