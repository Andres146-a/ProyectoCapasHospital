package com.hospital.modelos;


import java.time.LocalDateTime;

public class TomaSignos {
    private int id;
    private SignoVital signoVital;
    private double valor;
    private LocalDateTime fecha;

    public TomaSignos(int id, SignoVital signoVital, double valor, LocalDateTime fecha) {
        if (id <= 0) throw new IllegalArgumentException("El ID debe ser mayor que 0.");
        if (signoVital == null) throw new IllegalArgumentException("El signo vital no puede ser nulo.");
        if (valor < signoVital.getRangoMinimo() || valor > signoVital.getRangoMaximo()) {
            throw new IllegalArgumentException("El valor está fuera del rango permitido para " + signoVital.getNombre());
        }
        if (fecha == null) throw new IllegalArgumentException("La fecha no puede ser nula.");

        this.id = id;
        this.signoVital = signoVital;
        this.valor = valor;
        this.fecha = fecha;
    }

    // Getters
    public int getId() {
        return id;
    }

    public SignoVital getSignoVital() {
        return signoVital;
    }

    public double getValor() {
        return valor;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return "TomaSignos{id=" + id + ", signoVital=" + signoVital.getNombre() + ", valor=" + valor + ", fecha=" + fecha + "}";
    }
}
