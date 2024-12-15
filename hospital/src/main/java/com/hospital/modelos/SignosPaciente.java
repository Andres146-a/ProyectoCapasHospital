package com.hospital.modelos;

public class SignosPaciente {
    private int id;
    private int idPaciente;
    private double temperatura;
    private String presion;

    public SignosPaciente(int id, int idPaciente, double temperatura, String presion) {
        if (id <= 0 || idPaciente <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que 0.");
        }
        if (temperatura <= 0) {
            throw new IllegalArgumentException("La temperatura debe ser mayor que 0.");
        }
        if (presion == null || presion.isEmpty()) {
            throw new IllegalArgumentException("La presión no puede estar vacía.");
        }
        this.id = id;
        this.idPaciente = idPaciente;
        this.temperatura = temperatura;
        this.presion = presion;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("El ID debe ser mayor que 0.");
        this.id = id;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        if (idPaciente <= 0) throw new IllegalArgumentException("El ID del paciente debe ser mayor que 0.");
        this.idPaciente = idPaciente;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        if (temperatura <= 0) throw new IllegalArgumentException("La temperatura debe ser mayor que 0.");
        this.temperatura = temperatura;
    }

    public String getPresion() {
        return presion;
    }

    public void setPresion(String presion) {
        if (presion == null || presion.isEmpty()) {
            throw new IllegalArgumentException("La presión no puede estar vacía.");
        }
        this.presion = presion;
    }

    @Override
    public String toString() {
        return "SignosPaciente{id=" + id + ", idPaciente=" + idPaciente + ", temperatura=" + temperatura + ", presion='" + presion + "'}";
    }
}
