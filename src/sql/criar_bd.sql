-- Verificar se a database já existe antes de criar
DO $$
BEGIN
    IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'roombooking_db') THEN
        CREATE DATABASE roombooking_db;
    END IF;
END
$$;


-- Tabela tipo_sala
CREATE TABLE tipo_sala (
    idTipo SERIAL PRIMARY KEY,
    nomeTipo VARCHAR(50) NOT NULL,
    descricao TEXT
);

-- Tabela recurso
CREATE TABLE recurso (
    idRecurso SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

-- Tabela cliente
CREATE TABLE cliente (
    idCliente SERIAL PRIMARY KEY,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    nome VARCHAR(100) NOT NULL,
    tipoCliente BOOLEAN NOT NULL
);

-- Tabela sala
CREATE TABLE sala (
    codigoSala VARCHAR(20) PRIMARY KEY,
    local VARCHAR(100) NOT NULL,
    capacidade INT NOT NULL,
    idTipoSala INT NOT NULL,
    FOREIGN KEY (idTipoSala) REFERENCES tipo_sala(idTipo)
);

-- Tabela sala_recurso (N:N entre sala e recurso)
CREATE TABLE sala_recurso (
    codigoSala VARCHAR(20),
    idRecurso INT,
    PRIMARY KEY (codigoSala, idRecurso),
    FOREIGN KEY (codigoSala) REFERENCES sala(codigoSala),
    FOREIGN KEY (idRecurso) REFERENCES recurso(idRecurso)
);

-- Tabela reserva
CREATE TABLE reserva (
    id SERIAL PRIMARY KEY,
    data DATE NOT NULL,
    horaInicio TIME NOT NULL,
    horaFim TIME NOT NULL,
    custo DOUBLE PRECISION NOT NULL,
    codigoSala VARCHAR(20) NOT NULL,
    idCliente INT NOT NULL,
    ativa BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (codigoSala) REFERENCES sala(codigoSala),
    FOREIGN KEY (idCliente) REFERENCES cliente(idCliente)
);


DROP TABLE IF EXISTS reserva;
DROP TABLE IF EXISTS sala_recurso;
DROP TABLE IF EXISTS sala;
DROP TABLE IF EXISTS cliente;
DROP TABLE IF EXISTS recurso;
DROP TABLE IF EXISTS tiposala;
