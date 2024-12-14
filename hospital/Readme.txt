Estructura del PROYECTO:
PROYECTOCAPASHOSPITAL
├── hospital
│   ├── src
│   │   ├── main
│   │   │   ├── java/com/hospital
│   │   │   │   ├── negocio         # Lógica de Negocio (Patrón Facade)
│   │   │   │   │   ├── CentroMedicoFacade.java
│   │   │   │   │   ├── EnfermerosFacade.java
│   │   │   │   │   ├── PacientesFacade.java
│   │   │   │   │   ├── SignoVitalFacade.java
│   │   │   │   │   ├── SignosPacientesFacade.java
│   │   │   │   │   ├── TomaSignosFacade.java
│   │   │   │   ├── repositorios    # Acceso a Datos (Patrón Repository)
│   │   │   │   │   ├── CentroMedicoRepository.java
│   │   │   │   │   ├── EnfermerosRepository.java
│   │   │   │   │   ├── PacientesRepository.java
│   │   │   │   │   ├── SignoVitalRepository.java
│   │   │   │   │   ├── SignosPacientesRepository.java
│   │   │   │   │   ├── TomaSignosRepository.java
│   │   │   │   ├── UI              # Presentación (Patrón MVC)
│   │   │   │   │   ├── CentroMedicoController.java
│   │   │   │   │   ├── EnfermerosController.java
│   │   │   │   │   ├── PacientesController.java
│   │   │   │   │   ├── SignoVitalController.java
│   │   │   │   │   ├── SignosPacientesController.java
│   │   │   │   │   ├── TomaSignosController.java
│   │   │   │   ├── utilidades      # Clases Auxiliares
│   │   │   │   │   ├── DataBaseConnection.java
│   │   │   │   │   ├── Validator.java
│   │   │   ├── resources/com/hospital
│   │   │   │   ├── ui              # Archivos FXML (Interfaz gráfica)
│   │   │   │   │   ├── centro_medico.fxml
│   │   │   │   │   ├── enfermeros.fxml
│   │   │   │   │   ├── pacientes.fxml
│   │   │   │   │   ├── signo_vital.fxml
│   │   │   │   │   ├── signos_pacientes.fxml
│   │   │   │   │   ├── toma_signos.fxml
│   │   ├── test/java/com/hospital  # Pruebas Unitarias
│   │   │   ├── FacadeTest.java
├── pom.xml                          # Archivo Maven (Dependencias y configuración)
├── target                           # Carpeta generada tras compilar
├── README.txt                       # Archivo de documentación del proyecto


Descripción de los Archivos
1. Capa de Presentación (UI)

    Archivos Java (Controladores):
        CentroMedicoController.java: Maneja eventos para la vista del centro médico.
        EnfermerosController.java: Gestiona eventos relacionados con enfermeros.
        PacientesController.java: Gestiona la interacción de la vista de pacientes.
        SignoVitalController.java: Controlador para registrar un signo vital.
        SignosPacientesController.java: Controlador para listar signos de un paciente.
        TomaSignosController.java: Controlador para registrar una toma de signos vitales.

    Archivos FXML (Vistas):
        centro_medico.fxml: Vista gráfica para la gestión de un centro médico.
        enfermeros.fxml: Vista gráfica para gestionar enfermeros.
        pacientes.fxml: Vista gráfica para registrar y listar pacientes.
        signo_vital.fxml: Vista para registrar un signo vital.
        signos_pacientes.fxml: Vista para listar los signos vitales de un paciente.
        toma_signos.fxml: Vista para registrar una toma de signos.

2. Capa de Lógica de Negocio (LB)
    Archivos Java (Fachadas):
        CentroMedicoFacade.java: Implementa la lógica de negocio del centro médico.
        EnfermerosFacade.java: Lógica para la gestión de enfermeros.
        PacientesFacade.java: Lógica para el registro y gestión de pacientes.
        SignoVitalFacade.java: Lógica para registrar y validar signos vitales.
        SignosPacientesFacade.java: Lógica para listar los signos de pacientes.
        TomaSignosFacade.java: Lógica para registrar una toma de signos vitales.

3. Capa de Acceso a Datos (DA)
    Archivos Java (Repositorios):
        CentroMedicoRepository.java: CRUD para la entidad de centro médico.
        EnfermerosRepository.java: CRUD para la entidad de enfermeros.
        PacientesRepository.java: CRUD para la entidad de pacientes.
        SignoVitalRepository.java: CRUD para la entidad de signos vitales.
        SignosPacientesRepository.java: CRUD para la relación entre signos y pacientes.
        TomaSignosRepository.java: CRUD para la entidad de toma de signos vitales.

4. Clases Auxiliares
    Archivos Java:
        DataBaseConnection.java: Clase para gestionar la conexión a la base de datos con MySQL.
        Validator.java: Clase para validar datos como entradas de usuarios y formatos.

5. Archivos de Pruebas Unitarias
    Archivos Java:
        FacadeTest.java: Clase para probar las operaciones de las fachadas de la lógica de negocio.

6. Archivo Maven (pom.xml)
    Este archivo incluye las configuraciones del proyecto Maven y las dependencias necesarias, como:

JavaFX (para la interfaz gráfica).
MySQL Connector (para la conexión con la base de datos).
JUnit (para pruebas unitarias)