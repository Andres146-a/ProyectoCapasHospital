package com.hospital.modelos;

import javafx.beans.property.*;

public class SignoVital {
    private final IntegerProperty id;
    private final StringProperty nombre;
    private final StringProperty unidadMedida;
    private final StringProperty valorNormal;
    private final StringProperty frecuenciaCardiaca;
    private final StringProperty presionArterial;
    private final StringProperty temperatura;
    private final StringProperty frecuenciaRespiratoria;
    private final StringProperty oxigenacion;
    private final StringProperty fechaHora;

    public SignoVital(int id, String nombre, String valorNormal) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.valorNormal = new SimpleStringProperty(valorNormal);
        this.unidadMedida = new SimpleStringProperty("");
        this.frecuenciaCardiaca = new SimpleStringProperty("");
        this.presionArterial = new SimpleStringProperty("");
        this.temperatura = new SimpleStringProperty("");
        this.frecuenciaRespiratoria = new SimpleStringProperty("");
        this.oxigenacion = new SimpleStringProperty("");
        this.fechaHora = new SimpleStringProperty("");
    }

    public SignoVital(int id, String nombre, String valorNormal, String frecuenciaCardiaca, String presionArterial,
                      String temperatura, String frecuenciaRespiratoria, String oxigenacion, String fechaHora) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.valorNormal = new SimpleStringProperty(valorNormal);
        this.unidadMedida = new SimpleStringProperty("");
        this.frecuenciaCardiaca = new SimpleStringProperty(frecuenciaCardiaca);
        this.presionArterial = new SimpleStringProperty(presionArterial);
        this.temperatura = new SimpleStringProperty(temperatura);
        this.frecuenciaRespiratoria = new SimpleStringProperty(frecuenciaRespiratoria);
        this.oxigenacion = new SimpleStringProperty(oxigenacion);
        this.fechaHora = new SimpleStringProperty(fechaHora);
    }

    // Getters
    public int getId() {
        return id.get();
    }

    public String getNombre() {
        return nombre.get();
    }

    public String getUnidadMedida() {
        return unidadMedida.get();
    }

    public String getValorNormal() {
        return valorNormal.get();
    }

    public String getFrecuenciaCardiaca() {
        return frecuenciaCardiaca.get();
    }

    public String getPresionArterial() {
        return presionArterial.get();
    }

    public String getTemperatura() {
        return temperatura.get();
    }

    public String getFrecuenciaRespiratoria() {
        return frecuenciaRespiratoria.get();
    }

    public String getOxigenacion() {
        return oxigenacion.get();
    }

    public String getFechaHora() {
        return fechaHora.get();
    }

    // Métodos adicionales para las propiedades (si son necesarios)
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public StringProperty unidadMedidaProperty() {
        return unidadMedida;
    }

    public StringProperty valorNormalProperty() {
        return valorNormal;
    }

    public StringProperty frecuenciaCardiacaProperty() {
        return frecuenciaCardiaca;
    }

    public StringProperty presionArterialProperty() {
        return presionArterial;
    }

    public StringProperty temperaturaProperty() {
        return temperatura;
    }

    public StringProperty frecuenciaRespiratoriaProperty() {
        return frecuenciaRespiratoria;
    }

    public StringProperty oxigenacionProperty() {
        return oxigenacion;
    }

    public StringProperty fechaHoraProperty() {
        return fechaHora;
    }

    @Override
    public String toString() {
        return "SignoVital{" +
                "id=" + id.get() +
                ", nombre='" + nombre.get() + '\'' +
                ", unidadMedida='" + unidadMedida.get() + '\'' +
                ", valorNormal='" + valorNormal.get() + '\'' +
                ", frecuenciaCardiaca='" + frecuenciaCardiaca.get() + '\'' +
                ", presionArterial='" + presionArterial.get() + '\'' +
                ", temperatura='" + temperatura.get() + '\'' +
                ", frecuenciaRespiratoria='" + frecuenciaRespiratoria.get() + '\'' +
                ", oxigenacion='" + oxigenacion.get() + '\'' +
                ", fechaHora='" + fechaHora.get() + '\'' +
                '}';
    }
}
