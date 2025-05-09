DROP TABLE IF EXISTS doctor;
DROP TABLE IF EXISTS consultorio;
DROP TABLE IF EXISTS cita;

CREATE TABLE doctor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido_paterno VARCHAR(255) NOT NULL,
    apellido_materno VARCHAR(255) NOT NULL,
    especialidad VARCHAR(255) NOT NULL
);

CREATE TABLE consultorio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_consultorio INT NOT NULL,
    piso INT NOT NULL
);

CREATE TABLE cita (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    doctor_id BIGINT,
    consultorio_id BIGINT,
    horario_consulta TIMESTAMP,
    nombre_paciente VARCHAR(255),
    FOREIGN KEY (doctor_id) REFERENCES doctor(id),
    FOREIGN KEY (consultorio_id) REFERENCES consultorio(id)
); 