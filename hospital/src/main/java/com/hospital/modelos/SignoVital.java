package com.hospital.modelos;

public class SignoVital {
    private int id;
    private String nombre;
    private String valorNormal;

    public SignoVital(int id, String nombre, String valorNormal) {
        if (id <= 0) throw new IllegalArgumentException("El ID debe ser mayor que 0.");
        if (nombre == null || nombre.isEmpty()) throw new IllegalArgumentException("El nombre no puede estar vacío.");
        if (valorNormal == null || valorNormal.isEmpty()) throw new IllegalArgumentException("El valor normal no puede estar vacío.");
        this.id = id;
        this.nombre = nombre;
        this.valorNormal = valorNormal;
    }

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
        if (nombre == null || nombre.isEmpty()) throw new IllegalArgumentException("El nombre no puede estar vacío.");
        this.nombre = nombre;
    }

    public String getValorNormal() {
        return valorNormal;
    }

    public void setValorNormal(String valorNormal) {
        if (valorNormal == null || valorNormal.isEmpty()) throw new IllegalArgumentException("El valor normal no puede estar vacío.");
        this.valorNormal = valorNormal;
    }

    @Override
    public String toString() {
        return "SignoVital{id=" + id + ", nombre='" + nombre + "', valorNormal='" + valorNormal + "'}";
    }
}
