package com.hospital.modelos;

public class TomaSignos {
    private int id;
    private String frecuenciaCardiaca;
    private String presionArterial;
    private String temperatura;
    private String frecuenciaRespiratoria;
    private String oxigenacion;
    private String fechaHora;

    // Constructor
    public TomaSignos(int id, String frecuenciaCardiaca, String presionArterial, String temperatura,
                      String frecuenciaRespiratoria, String oxigenacion, String fechaHora) {
        this.id = id;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.presionArterial = presionArterial;
        this.temperatura = temperatura;
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
        this.oxigenacion = oxigenacion;
        this.fechaHora = fechaHora;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getFrecuenciaCardiaca() {
        return frecuenciaCardiaca;
    }

    public String getPresionArterial() {
        return presionArterial;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public String getFrecuenciaRespiratoria() {
        return frecuenciaRespiratoria;
    }

    public String getOxigenacion() {
        return oxigenacion;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    // Setters
    public void setFrecuenciaCardiaca(String frecuenciaCardiaca) {
        if (frecuenciaCardiaca == null || frecuenciaCardiaca.isEmpty()) {
            throw new IllegalArgumentException("La frecuencia cardíaca no puede ser nula o vacía.");
        }
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }
    public void setPresionArterial(String presionArterial) {
        if (presionArterial == null || presionArterial.isEmpty()) {
            throw new IllegalArgumentException("La presión arterial no puede ser nula o vacía.");
        }
        this.presionArterial = presionArterial;
    }
    

    public void setTemperatura(String temperatura) {
        if (temperatura == null || temperatura.isEmpty()) {
            throw new IllegalArgumentException("La temperatura no puede ser nula o vacía.");
        }
        this.temperatura = temperatura;
    }
    public void setFrecuenciaRespiratoria(String frecuenciaRespiratoria) {
        if (frecuenciaRespiratoria == null || frecuenciaRespiratoria.isEmpty()) {
            throw new IllegalArgumentException("La frecuencia respiratoria no puede ser nula o vacía.");
        }
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
    }

    public void setOxigenacion(String oxigenacion) {
        if (oxigenacion == null || oxigenacion.isEmpty()) {
            throw new IllegalArgumentException("La oxigenación no puede ser nula o vacía.");
        }
        this.oxigenacion = oxigenacion;
    }
    
    public void setFechaHora(String fechaHora) {
        if (fechaHora == null || fechaHora.isEmpty()) {
            throw new IllegalArgumentException("La fecha y hora no puede ser nula o vacía.");
        }
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return "TomaSignos{" +
                "id=" + id +
                ", frecuenciaCardiaca='" + frecuenciaCardiaca + '\'' +
                ", presionArterial='" + presionArterial + '\'' +
                ", temperatura='" + temperatura + '\'' +
                ", frecuenciaRespiratoria='" + frecuenciaRespiratoria + '\'' +
                ", oxigenacion='" + oxigenacion + '\'' +
                ", fechaHora='" + fechaHora + '\'' +
                '}';
    }
}
