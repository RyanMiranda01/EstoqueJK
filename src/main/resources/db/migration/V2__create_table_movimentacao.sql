CREATE TABLE movimentacoes
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    material_id BIGINT       NOT NULL,
    tipo        VARCHAR(10)  NOT NULL,
    quantidade  INT          NOT NULL,
    data_hora   DATETIME(6) NOT NULL,
    motivo      VARCHAR(255) NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT fk_movimentacoes_material
        FOREIGN KEY (material_id)
            REFERENCES material (id)
)