package com.hospital.modelos;

import javafx.beans.property.*;

public class SignoVital {
    private final IntegerProperty id;
    private final StringProperty frecuenciaCardiaca;
    private final StringProperty presionArterial;
    private final StringProperty temperatura;
    private final StringProperty frecuenciaRespiratoria;
    private final StringProperty oxigenacion;
    private final StringProperty fechaHora;

    public SignoVital(int id, String frecuenciaCardiaca, String presionArterial, 
                      String temperatura, String frecuenciaRespiratoria, String oxigenacion, String fechaHora) {
        this.id = new SimpleIntegerProperty(id);
        this.frecuenciaCardiaca = new SimpleStringProperty(frecuenciaCardiaca);
        this.presionArterial = new SimpleStringProperty(presionArterial);
        this.temperatura = new SimpleStringProperty(temperatura);
        this.frecuenciaRespiratoria = new SimpleStringProperty(frecuenciaRespiratoria);
        this.oxigenacion = new SimpleStringProperty(oxigenacion);
        this.fechaHora = new SimpleStringProperty(fechaHora);
    }

    // Getters y Properties
    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }

    public String getFrecuenciaCardiaca() { return frecuenciaCardiaca.get(); }
    public StringProperty frecuenciaCardiacaProperty() { return frecuenciaCardiaca; }

    public String getPresionArterial() { return presionArterial.get(); }
    public StringProperty presionArterialProperty() { return presionArterial; }

    public String getTemperatura() { return temperatura.get(); }
    public StringProperty temperaturaProperty() { return temperatura; }

    public String getFrecuenciaRespiratoria() { return frecuenciaRespiratoria.get(); }
    public StringProperty frecuenciaRespiratoriaProperty() { return frecuenciaRespiratoria; }

    public String getOxigenacion() { return oxigenacion.get(); }
    public StringProperty oxigenacionProperty() { return oxigenacion; }

    public String getFechaHora() { return fechaHora.get(); }
    public StringProperty fechaHoraProperty() { return fechaHora; }
}
