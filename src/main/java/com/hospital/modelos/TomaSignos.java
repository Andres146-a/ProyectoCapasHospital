package com.hospital.modelos;

import java.time.LocalDate;

public class TomaSignos {
    private int id;
    private int idPaciente;
    private LocalDate fecha;
    private int pulso;
    private String presion;
    private double temperatura;

    public TomaSignos(int id, int idPaciente, LocalDate fecha, int pulso, String presion, double temperatura) {
        if (pulso <= 0 || temperatura <= 0) {
            throw new IllegalArgumentException("El pulso y temperatura deben ser mayores que 0.");
        }
        this.id = id;
        this.idPaciente = idPaciente;
        this.fecha = fecha;
        this.pulso = pulso;
        this.presion = presion;
        this.temperatura = temperatura;
    }

    public int getId() {
        return id;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getPulso() {
        return pulso;
    }

    public String getPresion() {
        return presion;
    }

    public double getTemperatura() {
        return temperatura;
    }

    @Override
    public String toString() {
        return "TomaSignos{" +
                "id=" + id +
                ", idPaciente=" + idPaciente +
                ", fecha=" + fecha +
                ", pulso=" + pulso +
                ", presion='" + presion + '\'' +
                ", temperatura=" + temperatura +
                '}';
    }
}
