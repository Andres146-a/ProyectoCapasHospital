package com.hospital.modelos;

import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.List;

public class Paciente {
    private final IntegerProperty id;
    private final StringProperty nombre;
    private final StringProperty apellido;
    private final IntegerProperty edad;
    private final StringProperty enfermedades;
    private final StringProperty frecuenciaCardiaca;
    private final StringProperty presionArterial;
    private final StringProperty temperatura;
    private final StringProperty alerta;

    private final List<TomaSignos> registroS;

    // Constructor principal para todos los casos
    public Paciente(int id, String nombre, String apellido, int edad, String enfermedades,
                    String frecuenciaCardiaca, String presionArterial, String temperatura, String alerta) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.edad = new SimpleIntegerProperty(edad);
        this.enfermedades = new SimpleStringProperty(enfermedades);
        this.frecuenciaCardiaca = new SimpleStringProperty(frecuenciaCardiaca);
        this.presionArterial = new SimpleStringProperty(presionArterial);
        this.temperatura = new SimpleStringProperty(temperatura);
        this.alerta = new SimpleStringProperty(alerta);

        this.registroS = new ArrayList<>();
    }

    // Métodos Property (necesarios para TableView)
    public IntegerProperty idProperty() { return id; }
    public StringProperty nombreProperty() { return nombre; }
    public StringProperty apellidoProperty() { return apellido; }
    public IntegerProperty edadProperty() { return edad; }
    public StringProperty enfermedadesProperty() { return enfermedades; }
    public StringProperty frecuenciaCardiacaProperty() { return frecuenciaCardiaca; }
    public StringProperty presionArterialProperty() { return presionArterial; }
    public StringProperty temperaturaProperty() { return temperatura; }
    public StringProperty alertaProperty() { return alerta; }

    // Métodos Getter
    public int getId() { return id.get(); }
    public String getNombre() { return nombre.get(); }
    public String getApellido() { return apellido.get(); }
    public int getEdad() { return edad.get(); }
    public String getEnfermedades() { return enfermedades.get(); }
    public String getFrecuenciaCardiaca() { return frecuenciaCardiaca.get(); }
    public String getPresionArterial() { return presionArterial.get(); }
    public String getTemperatura() { return temperatura.get(); }
    public String getAlerta() { return alerta.get(); }

    @Override
    public String toString() {
        return "Paciente{" +
                "id=" + id.get() +
                ", nombre='" + nombre.get() + '\'' +
                ", apellido='" + apellido.get() + '\'' +
                ", edad=" + edad.get() +
                ", enfermedades='" + enfermedades.get() + '\'' +
                ", frecuenciaCardiaca='" + frecuenciaCardiaca.get() + '\'' +
                ", presionArterial='" + presionArterial.get() + '\'' +
                ", temperatura='" + temperatura.get() + '\'' +
                ", alerta='" + alerta.get() + '\'' +
                '}';
    }
}
