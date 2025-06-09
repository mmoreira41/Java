-- tipo_sala
INSERT INTO tipo_sala (nomeTipo, descricao) VALUES
('Standard', 'Sala padrão com recursos básicos'),
('Premium', 'Sala equipada com recursos adicionais'),
('VIP', 'Sala com todos os recursos e serviços');

-- recurso
INSERT INTO recurso (nome) VALUES
('Projetor'),
('Quadro Branco'),
('Internet'),
('Ar Condicionado'),
('Café');

-- cliente
INSERT INTO cliente (cpf, nome, tipoCliente) VALUES
('123.456.789-00', 'João Silva', TRUE),
('987.654.321-00', 'Maria Oliveira', FALSE),
('111.222.333-44', 'Empresa ABC Ltda', TRUE);

-- sala
INSERT INTO sala (codigoSala, local, capacidade, idTipoSala) VALUES
('SALA_001', 'Sala 1', 10, 1),
('SALA_002', 'Sala 2', 20, 2),
('SALA_003', 'Sala 3', 5, 3);

-- sala_recurso
INSERT INTO sala_recurso (codigoSala, idRecurso) VALUES
('SALA_001', 1), ('SALA_001', 2),
('SALA_002', 1), ('SALA_002', 3), ('SALA_002', 4),
('SALA_003', 1), ('SALA_003', 2), ('SALA_003', 3), ('SALA_003', 4), ('SALA_003', 5);

-- reserva (idCliente conforme a ordem de criação)
INSERT INTO reserva (data, horaInicio, horaFim, custo, codigoSala, idCliente, ativa) VALUES
('2024-03-20', '09:00:00', '10:00:00', 50.00, 'SALA_001', 1, TRUE),
('2024-03-20', '14:00:00', '16:00:00', 115.00, 'SALA_002', 2, TRUE),
('2024-03-21', '10:00:00', '12:00:00', 130.00, 'SALA_003', 3, TRUE);


SELECT * FROM cliente;
SELECT * FROM recurso;
SELECT * FROM reserva;
SELECT * FROM sala;
SELECT * FROM sala_recurso;
SELECT * FROM TipoSala;




