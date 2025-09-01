# CPU Simulator Backend

> Microservicios para la simulación de CPU, persistencia de programas y orquestación de APIs.

## 📂 Estructura del proyecto

```
.
├── express/      # API Gateway / Orquestador
│   ├── api/
│   │   ├── controllers/
│   │   └── routes/
│   └── node_modules/
├── spring/       # CPU Service (Spring Boot)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/flavio/spring/
│   │   │   │   ├── config/
│   │   │   │   ├── controllers/
│   │   │   │   ├── models/
│   │   │   │   ├── repositories/
│   │   │   │   └── services/
│   │   │   └── resources/
│   │   │       ├── static/
│   │   │       └── templates/
│   │   └── test/
├── storage/      # Storage Service (Node.js)
│   ├── api/
│   │   ├── controllers/
│   │   └── routes/
│   └── node_modules/
└── README.md
```

## 🛠 Tecnologías

* **Frontend:** React (en desarrollo)
* **API Gateway / Orquestador:** Node.js + Express
* **CPU Service:** Spring Boot
* **Storage Service:** Node.js + Express
* **Base de datos:** PostgreSQL o MongoDB

## ⚙️ Microservicios

### 1. API Gateway (`express/`)

* Expone endpoints REST/GraphQL al frontend.
* Orquesta llamadas entre CPU Service y Storage Service.
* Maneja autenticación y autorización si es necesario.

### 2. CPU Service (`spring/`)

* Contiene la lógica principal de simulación de la CPU.
* Ejecuta instrucciones paso a paso, mantiene registros y memoria.
* Expone endpoints REST para ejecutar, resetear y consultar el estado.

### 3. Storage Service (`storage/`)

* Gestiona la persistencia de programas, estados y logs.
* Permite que CPU Service y API Gateway guarden o recuperen información.
* Puede conectarse a PostgreSQL o MongoDB según necesidades.

## 🚀 Instalación

1. Clonar el repositorio:

```bash
git clone <repo-url>
cd backend
```

2. Instalar dependencias de Node:

```bash
cd express
npm install
cd ../storage
npm install
```

3. Ejecutar servicios Node:

```bash
cd express
npm start
cd ../storage
npm start
```

4. Ejecutar CPU Service (Spring Boot):

```bash
cd ../spring
./mvnw spring-boot:run
```

> Nota: Asegúrate de tener la base de datos configurada antes de iniciar los servicios.

## 🔗 Flujo de datos

1. Frontend envía peticiones al **API Gateway**.
2. API Gateway llama a **CPU Service** para ejecutar instrucciones.
3. CPU Service puede solicitar o guardar datos en **Storage Service**.
4. Storage Service maneja la persistencia en la base de datos.

## 📌 Convenciones

* Cada microservicio mantiene su **propia carpeta `node_modules` o `target`**.
* Controladores (`controllers`) manejan las rutas y la interacción con servicios.
* Servicios (`services`) contienen la lógica de negocio principal.
* Models (`models`) representan entidades y estructuras de datos.
* Repositories (`repositories`) gestionan la persistencia.

## 📝 Próximos pasos

* Integrar **React frontend** consumiendo la API Gateway.
* Añadir autenticación JWT en API Gateway.
* Implementar pruebas unitarias y de integración en todos los servicios.
