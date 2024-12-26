package com.hospital.modelos;

import javafx.beans.property.*;
import java.util.*;

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
    private StringProperty enfermedad;
    private final List<TomaSignos> registroS; // Lista para almacenar signos vitales
    private final List<String> antecedentes; // Lista de antecedentes
    private Map<String, String> parametrosAlertas; // Mapa de alertas personalizadas
    private SimpleBooleanProperty activo;

    public Paciente(int id, String nombre, String apellido, int edad, String enfermedades,
                    String frecuenciaCardiaca, String presionArterial, String temperatura, String alerta, 
                    String antecedentes, boolean activo) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.edad = new SimpleIntegerProperty(edad);
        this.enfermedades = new SimpleStringProperty(enfermedades);
        this.frecuenciaCardiaca = new SimpleStringProperty(frecuenciaCardiaca);
        this.presionArterial = new SimpleStringProperty(presionArterial);
        this.temperatura = new SimpleStringProperty(temperatura);
        this.alerta = new SimpleStringProperty(alerta != null ? alerta : "Sin alerta");
        this.activo = new SimpleBooleanProperty(activo);
    
        this.registroS = new ArrayList<>();
        this.antecedentes = new ArrayList<>();
        this.parametrosAlertas = new HashMap<>();
    }
    
    public boolean getActivo() {
        return activo.get();
    }
    
    public SimpleBooleanProperty activoProperty() {
        return activo;
    }
    
    public void setActivo(boolean activo) {
        this.activo.set(activo);
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
    public String getEnfermedad() {
        return enfermedad.get();
    }
    // Métodos para trabajar con 'registroS'
    public List<TomaSignos> getRegistroS() {
        return new ArrayList<>(registroS); // Devuelve una copia para evitar modificaciones externas.
    }

    public void agregarRegistroSignos(TomaSignos tomaSignos) {
        if (tomaSignos == null) {
            throw new IllegalArgumentException("El registro de signos no puede ser nulo.");
        }
        registroS.add(tomaSignos);
    }

    public void eliminarRegistroSignos(TomaSignos tomaSignos) {
        registroS.remove(tomaSignos);
    }

    public TomaSignos getRegistroSignosReciente() {
        if (registroS.isEmpty()) {
            return null;
        }
        return registroS.get(registroS.size() - 1);
    }

    // Métodos para antecedentes
    public String getAntecedentesAsString() {
        if (antecedentes == null || antecedentes.isEmpty()) {
            return "Sin antecedentes registrados.";
        }
        return String.join(", ", antecedentes); // Concatena los antecedentes separados por comas
    }
    
    public void agregarAntecedente(String antecedente) {
        if (antecedente == null || antecedente.trim().isEmpty()) {
            throw new IllegalArgumentException("El antecedente no puede estar vacío.");
        }
        antecedentes.add(antecedente);
        System.out.println("Antecedente agregado: " + antecedente); // Depuración
    }
    
    public void eliminarAntecedente(String antecedente) {
        antecedentes.remove(antecedente);
    }
   
    // Métodos para parámetros de alertas personalizados
    public Map<String, String> getParametrosAlertas() {
        return parametrosAlertas;
    }
   
    
    public void setParametrosAlertas(Map<String, String> parametrosAlertas) {
        if (parametrosAlertas == null) {
            throw new IllegalArgumentException("Los parámetros de alerta no pueden ser nulos.");
        }
        this.parametrosAlertas = new HashMap<>(parametrosAlertas); // Copia los parámetros para evitar referencias directas
    }

    // Método para agregar o actualizar una alerta en el mapa de alertas
    public void agregarOActualizarAlerta(String clave, String valor) {
        if (clave == null || clave.isEmpty() || valor == null || valor.isEmpty()) {
            throw new IllegalArgumentException("La clave y el valor de la alerta no pueden estar vacíos.");
        }
        this.parametrosAlertas.put(clave, valor);
    }
    public void eliminarAlerta(String clave) {
        this.parametrosAlertas.remove(clave);
    }
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
                ", antecedentes=" + antecedentes +
                ", parametrosAlertas=" + parametrosAlertas +
                ", registroS=" + registroS +
                '}';
    }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre.set(nombre);
    }
    // Setter para 'apellido'
public void setApellido(String apellido) {
    if (apellido == null || apellido.isEmpty()) {
        throw new IllegalArgumentException("El apellido no puede estar vacío.");
    }
    this.apellido.set(apellido);
}
public List<String> getAntecedentes() {
    return antecedentes; // Devuelve la lista de antecedentes
}
public void setAntecedentes(List<String> antecedentes) {
    this.antecedentes.clear();
    if (antecedentes != null) {
        this.antecedentes.addAll(antecedentes);
    }
}

// Setter para 'edad'
public void setEdad(int edad) {
    if (edad <= 0) {
        throw new IllegalArgumentException("La edad debe ser mayor a 0.");
    }
    this.edad.set(edad);
}
public void setAntecedentes(String antecedentes) {
    System.out.println("Asignando antecedentes al paciente: " + antecedentes);
    if (antecedentes != null && !antecedentes.isEmpty()) {
        // Divide la cadena en una lista separada por comas
        this.antecedentes.clear(); // Limpia la lista existente antes de agregar nuevos datos
        this.antecedentes.addAll(Arrays.asList(antecedentes.split(",\\s*")));
    } else {
        this.antecedentes.clear(); // Si el string es vacío, vacía la lista
    }
}


// Setter para 'alerta'
public void setAlerta(String alerta) {
    if (alerta == null || alerta.isEmpty()) {
        throw new IllegalArgumentException("La alerta no puede estar vacía.");
    }
    this.alerta.set(alerta);
}

public void setEnfermedad(String enfermedad) {
    this.enfermedad.set(enfermedad);
}
public StringProperty enfermedadProperty() {
    return enfermedad;
}


}
