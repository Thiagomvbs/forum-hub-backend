CREATE TYPE categoria_curso AS ENUM (
    'PROGRAMACAO', 'IA', 'FRONTEND', 'DADOS', 'INOVACAO', 'MARKETING', 'DESIGN'
);

CREATE TABLE cursos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL UNIQUE,
    categoria categoria_curso NOT NULL
);
