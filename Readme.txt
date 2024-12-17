Estructura del PROYECTO:
PROYECTOCAPASHOSPITAL
├── hospital
│   ├── src
│   │   ├── main
│   │   │   ├── java/com/hospital
│   │   │   │   ├── negocio         # Capa de Lógica de Negocio (Patrón Facade)
│   │   │   │   │   ├── CentroMedicoFacade.java
│   │   │   │   │   ├── EnfermerosFacade.java
│   │   │   │   │   ├── PacientesFacade.java
│   │   │   │   │   ├── SignoVitalFacade.java
│   │   │   │   │   ├── SignosPacientesFacade.java
│   │   │   │   │   ├── TomaSignosFacade.java
│   │   │   │   ├── repositorios    # Capa de Acceso a Datos (Patrón Repository)
│   │   │   │   │   ├── CentroMedicoRepository.java
│   │   │   │   │   ├── CentroMedicoRepositoryMySQL.java
│   │   │   │   │   ├── EnfermerosRepository.java
│   │   │   │   │   ├── EnfermerosRepositoryMySQL.java
│   │   │   │   │   ├── PacientesRepository.java
│   │   │   │   │   ├── PacientesRepositoryMySQL.java
│   │   │   │   │   ├── SignoVitalRepository.java
│   │   │   │   │   ├── SignoVitalRepositoryMySQL.java
│   │   │   │   │   ├── SignosPacientesRepository.java
│   │   │   │   │   ├── SignosPacientesRepositoryMySQL.java
│   │   │   │   │   ├── TomaSignosRepository.java
│   │   │   │   │   ├── TomaSignosRepositoryMySQL.java
│   │   │   │   ├── UI              # Capa de Presentación (Patrón MVC)
│   │   │   │   │   ├── CentroMedicoController.java
│   │   │   │   │   ├── EnfermerosController.java
│   │   │   │   │   ├── MainController.java
│   │   │   │   │   ├── PacientesController.java
│   │   │   │   │   ├── SignoVitalController.java
│   │   │   │   │   ├── SignosPacientesController.java
│   │   │   │   │   ├── TomaSignosController.java
│   │   │   │   ├── modelos         # Clases Modelo (Entidades)
│   │   │   │   │   ├── CentroMedico.java
│   │   │   │   │   ├── Enfermero.java
│   │   │   │   │   ├── Paciente.java
│   │   │   │   │   ├── SignoVital.java
│   │   │   │   │   ├── SignosPacientes.java
│   │   │   │   │   ├── TomaSignos.java
│   │   │   │   ├── utilidades      # Clases Auxiliares
│   │   │   │   │   ├── DataBaseConnection.java
│   │   │   │   │   ├── Validator.java
│   │   │   ├── resources/com/hospital
│   │   │   │   ├── ui              # Archivos FXML (Interfaz gráfica)
│   │   │   │   │   ├── centro_medico.fxml
│   │   │   │   │   ├── enfermeros.fxml
│   │   │   │   │   ├── main.fxml
│   │   │   │   │   ├── pacientes.fxml
│   │   │   │   │   ├── signo_vital.fxml
│   │   │   │   │   ├── signos_pacientes.fxml
│   │   │   │   │   ├── toma_signos.fxml
│   │   ├── test/java/com/hospital  # Pruebas Unitarias
│   │   │   ├── FacadeTest.java
├── pom.xml                          # Archivo Maven (Dependencias y configuración)
├── target                           # Carpeta generada tras compilar
├── README.txt                       # Archivo de documentación del proyecto


Descripción de las Capas y Archivos:

    1. Capa de Presentación (UI):
        Controladores Java (MVC):
        Manejan la interacción entre las vistas (FXML) y la lógica de negocio.
        Ejemplo: EnfermerosController.java maneja los eventos y funcionalidades de enfermeros.fxml.
        Archivos FXML:
        Diseñan la interfaz gráfica con JavaFX para cada funcionalidad específica.

    2. Capa de Lógica de Negocio (Facade):
        Fachadas (Java):
        Centralizan las reglas de negocio.
        Interactúan directamente con los repositorios para realizar operaciones complejas.
        Ejemplo: PacientesFacade.java administra operaciones como agregar, actualizar o eliminar pacientes.

    3. Capa de Acceso a Datos (Repository):
        Interfaces de Repositorios:
        Definen las operaciones CRUD para las entidades.
        Ejemplo: EnfermerosRepository.java contiene métodos como guardarEnfermero() y listarTodos().
        Implementaciones MySQL:
        Implementan la lógica para interactuar con la base de datos.
        Ejemplo: EnfermerosRepositoryMySQL.java usa JDBC para ejecutar consultas SQL.

    4. Clases Modelo (Entidades):
        Representan las tablas de la base de datos.
        Contienen atributos, getters, setters y validaciones básicas.
        Ejemplo: Enfermero.java modela la entidad de enfermeros.

    5. Utilidades:
        Clases auxiliares para funciones generales:
        DataBaseConnection.java: Gestiona conexiones con MySQL.
        Validator.java: Valida datos ingresados por el usuario.

    6. Pruebas Unitarias:
        Test de Fachadas:
        Validan el correcto funcionamiento de la lógica de negocio.
        Ejemplo: FacadeTest.java prueba métodos de las clases en la capa de negocio.

    7. Archivo Maven (pom.xml):
        Define las dependencias:
        JavaFX: Para la interfaz gráfica.
        MySQL Connector: Para la conexión con la base de datos.
        JUnit: Para pruebas unitarias.
