CREATE TABLE  material(
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(150) NOT NULL,
    modelo VARCHAR(100),
    unidade_medida VARCHAR(20) NOT NULL,
    estoque_minimo DECIMAL(12, 2) NOT NULL  DEFAULT 0,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    quantidade DECIMAL(12, 2) NOT NULL,

    PRIMARY KEY (id)
)