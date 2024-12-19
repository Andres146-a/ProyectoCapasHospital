package com.hospital.utilidades;

public enum View {
    APERTURA_FICHA("/com/hospital/ui/apertura_ficha.fxml"),
    TOMA_SIGNOS("/com/hospital/ui/toma_signos.fxml"),
    CONSULTA_MEDICA("/com/hospital/ui/consulta_medica.fxml"),
    REPORTES_MEDICOS("/com/hospital/ui/reportes_medicos.fxml"),
    CONSULTA_ANTECEDENTES("/com/hospital/ui/consulta_antecedentes.fxml"),
    ENFERMEROS_DISPONIBLES("/com/hospital/ui/enfermeros_disponibles.fxml"),
    PACIENTES_ACTIVOS("/com/hospital/ui/pacientes_activos.fxml");

    private final String fxmlPath;

    View(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }
}
