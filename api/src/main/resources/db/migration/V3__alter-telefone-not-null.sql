-- migration.sql

-- Passo 1: Atualiza os telefones nulos
UPDATE medicos
SET telefone = '00000000000'
WHERE telefone IS NULL;

-- Passo 2: Define a coluna como NOT NULL
ALTER TABLE medicos
ALTER COLUMN telefone SET NOT NULL;
