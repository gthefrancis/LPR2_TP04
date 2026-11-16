-- 1. Cria e seleciona o banco de dados
CREATE DATABASE aulajava;
GO
USE aulajava;
GO

-- 2. Cria a tabela principal (Cargos)
CREATE TABLE tbcargos (
    cd_cargo SMALLINT NOT NULL PRIMARY KEY,
    ds_cargo CHAR(20) NULL
);
GO

-- 3. Cria a tabela dependente (Funcionários)
CREATE TABLE tbfuncs (
    cod_func DECIMAL(9, 0) NOT NULL PRIMARY KEY,
    nome_func CHAR(30) NULL,
    sal_func MONEY NULL,
    cod_cargo SMALLINT NULL
);
GO

-- 4. Cria a relação de Chave Estrangeira (Foreign Key)
ALTER TABLE tbfuncs
ADD CONSTRAINT FK_Func_Cargo 
FOREIGN KEY (cod_cargo) REFERENCES tbcargos(cd_cargo);
GO

-- 5. Insere os cargos primeiro (Regra da FK)
INSERT INTO tbcargos (cd_cargo, ds_cargo) VALUES (1, 'Administrativo');
INSERT INTO tbcargos (cd_cargo, ds_cargo) VALUES (2, 'Desenvolvedor');
INSERT INTO tbcargos (cd_cargo, ds_cargo) VALUES (3, 'Gerente');
INSERT INTO tbcargos (cd_cargo, ds_cargo) VALUES (4, 'Suporte Técnico');
GO

-- 6. Insere a galera toda na tabela de funcionários
INSERT INTO tbfuncs (cod_func, nome_func, sal_func, cod_cargo) VALUES 
(1001, 'Marcelo', 2000.00, 1),
(1002, 'Ana Paula Silva', 6500.50, 2),
(1003, 'Carlos de Souza', 9200.00, 3),
(1004, 'Beatriz Lima', 3100.00, 4),
(1005, 'João Pedro', 2500.00, 1),
(1006, 'Mariana Costa', 7000.00, 2),
(1007, 'Lucas Almeida', 2800.00, 4),
(1008, 'Davi Souza', 8500.00, 2),
(1009, 'Henrique Meira', 8500.00, 2),
(1010, 'Igo Lima', 8500.00, 2),
(1011, 'Diego Martins', 6800.00, 2),
(1012, 'Camila Rocha', 4500.00, 2),
(1013, 'Bruno Henrique', 7200.00, 2),
(1014, 'Juliana Mendes', 2600.00, 1);
GO