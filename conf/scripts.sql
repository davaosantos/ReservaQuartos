CREATE TABLE QUARTO
(
    ID         INT AUTO_INCREMENT PRIMARY KEY,
    NUMERO     INT NOT NULL UNIQUE,
    DESCRICAO  VARCHAR(255),
    CAPACIDADE INT NOT NULL
);

CREATE TABLE HOSPEDE
(
    ID   INT AUTO_INCREMENT PRIMARY KEY,
    NOME VARCHAR(255) NOT NULL
);

CREATE TABLE LIMPEZA
(
    ID          INT AUTO_INCREMENT PRIMARY KEY,
    ID_QUARTO   INT       NOT NULL,
    DATA_INICIO TIMESTAMP NOT NULL,
    DATA_FIM    TIMESTAMP NOT NULL,
    FOREIGN KEY (ID_QUARTO) REFERENCES QUARTO (ID)
);

CREATE TABLE RESERVA
(
    ID          INT AUTO_INCREMENT PRIMARY KEY,
    ID_QUARTO   INT  NOT NULL,
    ID_HOSPEDE  INT  NOT NULL,
    DATA_INICIO DATE NOT NULL,
    DATA_FIM    DATE NOT NULL,
    FOREIGN KEY (ID_QUARTO) REFERENCES QUARTO (id),
    FOREIGN KEY (ID_HOSPEDE) REFERENCES HOSPEDE (id),
    UNIQUE (ID_QUARTO, DATA_INICIO, DATA_FIM)
);