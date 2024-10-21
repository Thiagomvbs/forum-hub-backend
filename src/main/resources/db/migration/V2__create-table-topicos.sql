CREATE TYPE status_topico AS ENUM ('NAO_RESPONDIDO', 'RESPONDIDO', 'RESOLVIDO');

CREATE TABLE topicos (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    mensagem TEXT NOT NULL,
    autor VARCHAR(255) NOT NULL,
    categoria categoria_curso NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status status_topico NOT NULL DEFAULT 'NAO_RESPONDIDO',
    aberto BOOLEAN NOT NULL DEFAULT TRUE,
    quantidade_respostas INT NOT NULL DEFAULT 0,
    curso_id BIGINT,
    CONSTRAINT fk_curso FOREIGN KEY (curso_id) REFERENCES cursos(id) ON DELETE SET NULL
);