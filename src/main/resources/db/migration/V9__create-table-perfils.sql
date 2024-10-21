CREATE TABLE perfis (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE usuarios_perfis (
    usuario_id BIGINT NOT NULL,
    perfil_id BIGINT NOT NULL,

    PRIMARY KEY (usuario_id, perfil_id),
    CONSTRAINT fk_usuario_perfil FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_perfil_usuario FOREIGN KEY (perfil_id) REFERENCES perfis(id) ON DELETE CASCADE
);

INSERT INTO perfis (nome) VALUES ('ESTUDANTE');
INSERT INTO perfis (nome) VALUES ('INSTRUTOR');
INSERT INTO perfis (nome) VALUES ('MODERADOR');
INSERT INTO perfis (nome) VALUES ('ADMIN');
