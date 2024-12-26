package com.hospital.negocio;

import com.hospital.modelos.Paciente;
import java.util.Map;
public class AlertasFacade {
    public String generarAlerta(Paciente paciente, Map<String, String> parametros) {
        StringBuilder alerta = new StringBuilder();

        // Validar presión arterial
        String presionArterial = paciente.getPresionArterial();
        if (presionArterial != null && presionArterial.contains("/")) {
            try {
                String[] presion = presionArterial.split("/");
                int sistolica = Integer.parseInt(presion[0].trim());
                int diastolica = Integer.parseInt(presion[1].trim());
                if (sistolica > Integer.parseInt(parametros.get("presionSistolica")) ||
                        diastolica > Integer.parseInt(parametros.get("presionDiastolica"))) {
                    alerta.append("Hipertensión ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error al procesar presión arterial: " + presionArterial);
            }
        }

        // Validar frecuencia cardíaca
        String frecuenciaCardiaca = paciente.getFrecuenciaCardiaca();
        if (frecuenciaCardiaca != null && frecuenciaCardiaca.contains("lpm")) {
            try {
                int frecuencia = Integer.parseInt(frecuenciaCardiaca.replace(" lpm", "").trim());
                if (frecuencia < Integer.parseInt(parametros.get("frecuenciaMin"))) {
                    alerta.append("Bradicardia ");
                } else if (frecuencia > Integer.parseInt(parametros.get("frecuenciaMax"))) {
                    alerta.append("Taquicardia ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error al procesar frecuencia cardíaca: " + frecuenciaCardiaca);
            }
        }

        // Validar temperatura
        String temperatura = paciente.getTemperatura();
        if (temperatura != null && temperatura.contains("°C")) {
            try {
                double temp = Double.parseDouble(temperatura.replace("°C", "").trim());
                if (temp > Double.parseDouble(parametros.get("temperaturaMax"))) {
                    alerta.append("Fiebre ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error al procesar temperatura: " + temperatura);
            }
        }

        return alerta.toString().trim();
    }
}
