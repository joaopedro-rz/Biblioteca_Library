CREATE TABLE Cliente (
        id SERIAL PRIMARY KEY,
        datanascimento DATE,
        datacadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        nome VARCHAR(45) NOT NULL,
        cpf VARCHAR(14) NOT NULL,
        telefone VARCHAR(20) NOT NULL,
        email VARCHAR(100),
        genero VARCHAR(1),
        status VARCHAR(1)
    );
CREATE TABLE Emprestimo (
    id SERIAL PRIMARY KEY,
    dataemprestimo TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    dataentrega DATE,
    idcliente INT,
    idlivro INT,
    status VARCHAR(1)
);
CREATE TABLE Multa (
    id SERIAL PRIMARY KEY,
    diasatrasado INT,
    valordia DECIMAL(10,2),
    idemprestimo INT
);
CREATE TABLE Livro (
    id SERIAL PRIMARY KEY,
    anopublicacao INT,
    sinopse TEXT,
    titulo VARCHAR(45),
    autor VARCHAR(45),
    isbn VARCHAR(20),
    editora VARCHAR(45),
    genero VARCHAR(45),
    idioma VARCHAR(45),
    estoque INT
);
CREATE TABLE HistoricoEmprestimo (
    id SERIAL PRIMARY KEY,
    dataemprestimo DATE,
    datadevolucao DATE,
    idcliente INT,
    idlivro INT
);
CREATE TABLE HistoricoMulta (
    id SERIAL PRIMARY KEY,
    diasatrasado INT,
    valortotal DECIMAL(10,2),
    idhistoricoemprestimo INT
);
-- atribuir chaves extrangeiras.
ALTER TABLE Emprestimo
ADD CONSTRAINT fk_cliente_emprestimo
FOREIGN KEY (idcliente) REFERENCES Cliente(id);
ALTER TABLE Emprestimo
ADD CONSTRAINT fk_livro_emprestimo
FOREIGN KEY (idlivro) REFERENCES Livro(id);
ALTER TABLE Multa
ADD CONSTRAINT fk_emprestimo_multa
FOREIGN KEY (idemprestimo) REFERENCES Emprestimo(id);
ALTER TABLE Historicoemprestimo
ADD CONSTRAINT fk_cliente_historicoemprestimo
FOREIGN KEY (idcliente) REFERENCES Cliente(id);
ALTER TABLE HistoricoEmprestimo
ADD CONSTRAINT fk_livro_historicoemprestimo
FOREIGN KEY (idlivro) REFERENCES Livro(id);
ALTER TABLE HistoricoMulta
ADD CONSTRAINT fk_historicoemprestimo_historicomulta
FOREIGN KEY(idhistoricoemprestimo) REFERENCES HistoricoEmprestimo(id);

-- Índices
--Cliente
CREATE INDEX idx_cliente_cpf ON Cliente(cpf);
--Emprestimo
CREATE INDEX idx_emprestimo_idCliente ON Emprestimo(idcliente);
--Multa
CREATE INDEX idx_multa_id_emprestimo ON Multa(idemprestimo);
--Livro
CREATE INDEX idx_livro_titulo ON Livro(titulo);

    --triggers/function
-- Possibilitar que a data de entrega seja automaticamente 10 dias após a data de empréstimo, verificando se o livro tem estoque e se o cliente está como Regular.
CREATE OR REPLACE FUNCTION before_insert_emprestimo()
RETURNS TRIGGER AS $$
DECLARE
    estoque_atual INT;
    status_cliente VARCHAR(1);
BEGIN
    SELECT estoque, status INTO estoque_atual, status_cliente
    FROM Livro l 
    JOIN Cliente c ON NEW.idcliente = c.id
    WHERE l.id = NEW.idlivro;

    IF estoque_atual > 0 AND status_cliente = 'R' THEN
        IF NEW.dataentrega IS NULL THEN
            NEW.dataentrega := NEW.dataemprestimo + INTERVAL '10 days';
        END IF;

        UPDATE Livro SET estoque = estoque_atual - 1 WHERE id = NEW.idlivro;
        UPDATE Cliente SET status = 'C' WHERE id = NEW.idcliente;
        RETURN NEW;
    ELSE
        RAISE EXCEPTION 'Livro fora de estoque/Cliente Irregular.';
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_before_insert_emprestimo
BEFORE INSERT ON Emprestimo
FOR EACH ROW
EXECUTE FUNCTION before_insert_emprestimo();

-- Possibilitar um histórico de empréstimo assim que um empréstimo ativo for finalizado, retornando o estoque do livro emprestado.
CREATE OR REPLACE FUNCTION before_delete_emprestimo()
RETURNS TRIGGER AS $$
DECLARE
    idHistoricoEmprestimo INT;
    diasAtrasadoVar INT;
    valorDiaVar DECIMAL(10,2);
BEGIN
    INSERT INTO HistoricoEmprestimo (dataEmprestimo, dataDevolucao, idCliente, idLivro)
    VALUES (OLD.dataemprestimo, CURRENT_DATE, OLD.idcliente, OLD.idlivro)
    RETURNING id INTO idHistoricoEmprestimo;

    IF EXISTS (SELECT 1 FROM Multa WHERE idemprestimo = OLD.id) THEN
        SELECT diasatrasado, valordia INTO diasatrasadoVar, valordiavar
        FROM Multa
        WHERE idemprestimo = OLD.id;

        INSERT INTO HistoricoMulta (diasatrasado, valortotal, idhistoricoemprestimo)
        VALUES(diasatrasadovar, valordiavar * diasatrasadoVar, idhistoricoemprestimo);
        DELETE FROM Multa WHERE idemprestimo = OLD.id;
    END IF;

    UPDATE Livro SET estoque = estoque + 1 WHERE id = OLD.idlivro;
    UPDATE Cliente SET status = 'R' WHERE id = OLD.idcliente;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_before_delete_emprestimo
BEFORE DELETE ON Emprestimo
FOR EACH ROW
EXECUTE FUNCTION before_delete_emprestimo();

-- Gerar automaticamente uma multa caso o empréstimo passe a ser Irregular (ultrapassou prazo de entrega)
CREATE OR REPLACE FUNCTION after_update_status_emprestimo()
RETURNS TRIGGER AS $$
DECLARE
    dias_de_atraso INT;
BEGIN
    IF OLD.status = 'R' AND NEW.status = 'I' THEN
        dias_de_atraso := CURRENT_DATE - NEW.dataEntrega;
        INSERT INTO Multa (diasatrasado, valordia, idemprestimo)
        VALUES (dias_de_atraso, 2, NEW.id);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_after_update_status_emprestimo
AFTER UPDATE ON Emprestimo
FOR EACH ROW
EXECUTE FUNCTION after_update_status_emprestimo();

-- Views
CREATE VIEW vw_emprestimo_info AS
SELECT
    e.id AS emprestimo_id,
    e.dataemprestimo AS data_emprestimo,
    e.dataentrega AS data_entrega,
    c.nome AS cliente_nome,
    c.cpf AS cliente_cpf,
    l.titulo AS livro_titulo,
    l.autor AS livro_autor,
    l.isbn AS livro_isbn,
    e.status AS emprestimo_status,
    CASE
        WHEN e.status = 'R' THEN 'N'
        WHEN e.status = 'I' THEN 'S'
    END AS multa_status
FROM
    Emprestimo e
JOIN cliente c ON e.idcliente =c.id
JOIN livro l ON e.idlivro = l.id;
-- Inserção de dados na view
CREATE OR REPLACE FUNCTION instead_of_insert_vw_emprestimo_info()
RETURNS TRIGGER AS $$
DECLARE
    livro_id INT;
BEGIN
    SELECT id INTO livro_id FROM Livro WHERE titulo = NEW.livro_titulo;

    IF livro_id IS NULL THEN
        INSERT INTO Livro (titulo) VALUES (NEW.livro_titulo) RETURNING id INTO livro_id;
    END IF;

    INSERT INTO Emprestimo (idcliente, idlivro, status)
    VALUES (NEW.idcliente, livro_id, NEW.emprestimo_status);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_insert_vw_emprestimo_info
INSTEAD OF INSERT ON vw_emprestimo_info
FOR EACH ROW
EXECUTE FUNCTION instead_of_insert_vw_emprestimo_info();

 -- Usuários
CREATE USER random WITH PASSWORD '12345';
GRANT SELECT ON TABLE Cliente TO random;
GRANT SELECT ON TABLE Emprestimo TO random;
GRANT SELECT ON TABLE Multa TO random;
GRANT SELECT ON TABLE Livro TO random;
GRANT SELECT ON TABLE HistoricoEmprestimo TO random;
GRANT SELECT ON TABLE HistoricoMulta TO random;
-- Concedendo permissões ao usuário
GRANT SELECT, INSERT ON vw_emprestimo_info TO random;

-- Inserções de 5 linhas nas tabelas
INSERT INTO Cliente (datanascimento, nome, cpf, telefone, email, genero, status)
VALUES
    ('1990-05-15', 'Maria Silva', '123.456.789-01', '(11) 98765-4321', 'maria@email.com', 'M', 'R'),
    ('1985-10-22', 'João Santos', '987.654.321-02', '(21) 98765-1234', 'joao@email.com', 'H', 'R'),
    ('1998-03-08', 'Ana Oliveira', '234.567.890-03', '(31) 98765-5678', 'ana@email.com', 'M', 'R'),
    ('1976-12-01', 'Pedro Rocha', '345.678.901-04', '(41) 98765-8765', 'pedro@email.com', 'H', 'R'),
    ('1995-07-18', 'Carla Lima', '456.789.012-05', '(51) 98765-4321', 'carla@email.com', 'M', 'R'),
    ('1988-02-28', 'Lucas Oliveira', '567.890.123-06', '(61) 98765-8765', 'lucas@email.com', 'H', 'R'),
    ('1992-09-14', 'Mariana Santos', '678.901.234-07', '(71) 98765-4321', 'mariana@email.com', 'M', 'R'),
    ('1983-06-30', 'Ricardo Lima', '789.012.345-08', '(81) 98765-8765', 'ricardo@email.com', 'H', 'R'),
    ('1997-11-05', 'Amanda Silva', '890.123.456-09', '(91) 98765-4321', 'amanda@email.com', 'M', 'R'),
    ('1980-04-20', 'Gabriel Rocha', '901.234.567-10', '(01) 98765-8765', 'gabriel@email.com', 'H', 'R');

INSERT INTO Livro (anopublicacao, sinopse, titulo, autor, isbn, editora, genero, idioma, estoque)
VALUES
    (2010, 'Um livro emocionante sobre aventuras', 'Aventuras na Floresta', 'Carlos Silva', '978-1234567890', 'Editora A', 'Aventura', 'Português', 1),
    (2005, 'Uma história de amor e suspense', 'O Mistério do Lago', 'Ana Oliveira', '978-9876543210', 'Editora B', 'Romance', 'Português', 1),
    (2018, 'Um thriller psicológico envolvente', 'Entre a Luz e as Sombras', 'Pedro Rocha', '978-2345678901', 'Editora C', 'Suspense', 'Português', 1),
    (2021, 'Uma jornada épica de fantasia', 'O Reino Perdido', 'Carla Lima', '978-3456789012', 'Editora D', 'Fantasia', 'Português', 1),
    (2015, 'Uma história inspiradora de superação', 'Além dos Limites', 'João Santos', '978-4567890123', 'Editora E', 'Autoajuda', 'Português', 1),
    (2012, 'Uma história de ficção científica', 'Viagem ao Futuro', 'Lucas Oliveira', '978-1111111111', 'Editora F', 'Ficção Científica', 'Português', 3),
    (2008, 'Um romance clássico', 'Amor Eterno', 'Mariana Santos', '978-2222222222', 'Editora G', 'Romance', 'Português', 2),
    (2015, 'Uma aventura empolgante', 'O Tesouro Perdido', 'Ricardo Lima', '978-3333333333', 'Editora H', 'Aventura', 'Português', 5),
    (2019, 'Suspense psicológico', 'Além das Sombras', 'Amanda Silva', '978-4444444444', 'Editora I', 'Suspense', 'Português', 1),
    (2017, 'História de superação', 'Vida Renovada', 'Gabriel Rocha', '978-5555555555', 'Editora J', 'Autoajuda', 'Português', 4);

INSERT INTO Emprestimo (idcliente, idLivro, status)
VALUES
    (1, 3, 'R'),
    (2, 1, 'R'),
    (3, 4, 'R'),
    (4, 2, 'R'),
    (5, 5, 'R'),
    (6, 6, 'R'),
    (7, 7, 'R'),
    (8, 8, 'R'),
    (9, 9, 'R'),
    (10, 10, 'R');

UPDATE Emprestimo SET status = 'I' WHERE id IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
DELETE FROM Emprestimo WHERE id IN (6, 7, 8, 9,10);