# wfh

This project is a backend application developed using Java, designed to streamline the Work From Home (WFH) request and approval process. It provides a user-friendly interface for employees to submit, view, and manage their WFH requests, and for managers to review and approve or reject these requests.

[Admin.webm](https://github.com/user-attachments/assets/ea7025f3-18d6-43ff-905f-d2f6298d9585)
[Manager.webm](https://github.com/user-attachments/assets/b7125163-0551-4b73-88d5-a91d5a4242e5)
[Employee.webm](https://github.com/user-attachments/assets/ba3bc43d-ef4f-4f60-9512-666396fc3ab5)


**WFH frondend project repository:** [WFH-Frontend project repository](https://github.com/ankitverma10203/wfh-frontend)

## Features

- **Java/Springboot**: The core programming language used for backend development.
- **Docker**: Utilized for containerizing the application, ensuring consistent environments across different stages of development and deployment.

## Getting Started

To set up and run this project locally, follow these steps:

1. **Clone the repository**:

   ```bash
   git clone https://github.com/ankitverma10203/wfh.git
   cd wfh
   ```

2. **Build the project**:

   Use Maven to build the project. If Maven is not installed, you can use the provided wrapper script:

   ```bash
   ./mvnw clean install
   ```

3. **Run the application**:

   After a successful build, run the application using:

   ```bash
   java -jar target/wfh-0.0.1-SNAPSHOT.jar
   ```

   This will start the backend server, typically accessible at `http://localhost:8080`.

## Docker Support

The project includes a `Dockerfile` for building a Docker image:

1. **Build the Docker image**:

   ```bash
   docker build -t wfh-backend .
   ```

2. **Run the Docker container**:

   ```bash
   docker run -p 8080:8080 wfh-backend
   ```

   This command runs the application inside a Docker container and maps port 8080 of the container to port 8080 on your host machine.
