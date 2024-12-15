package com.hospital.modelos;


public class SignoVital {
    private int id;
    private String nombre; // Ejemplo: Presión arterial, Temperatura, Frecuencia cardíaca
    private double rangoMinimo;
    private double rangoMaximo;

    public SignoVital(int id, String nombre, double rangoMinimo, double rangoMaximo) {
        if (id <= 0) throw new IllegalArgumentException("El ID debe ser mayor que 0.");
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (rangoMinimo >= rangoMaximo) {
            throw new IllegalArgumentException("El rango mínimo debe ser menor que el máximo.");
        }

        this.id = id;
        this.nombre = nombre;
        this.rangoMinimo = rangoMinimo;
        this.rangoMaximo = rangoMaximo;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getRangoMinimo() {
        return rangoMinimo;
    }

    public double getRangoMaximo() {
        return rangoMaximo;
    }

    @Override
    public String toString() {
        return "SignoVital{id=" + id + ", nombre='" + nombre + "', rangoMinimo=" + rangoMinimo + ", rangoMaximo=" + rangoMaximo + "}";
    }
}
