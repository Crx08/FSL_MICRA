DROP DATABASE scuola;
CREATE DATABASE scuola;
USE scuola;
CREATE TABLE studenti (
	id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cognome VARCHAR(255) NOT NULL,
    anno_nascita DATE NOT NULL
);
INSERT INTO studenti(nome, cognome, anno_nascita)
VALUES('Antonio', 'Rossi', 15-05-2002);

INSERT INTO studenti(nome, cognome, anno_nascita)
VALUES('Luca', 'Bianchi', 01-02-2000);

INSERT INTO studenti(nome, cognome, anno_nascita)
VALUES('Matteo', 'Antonelli', 29-09-1999);

INSERT INTO studenti(nome, cognome, anno_nascita)
VALUES('Mattia', 'Ragni', 26-04-1997);

INSERT INTO studenti(nome, cognome, anno_nascita)
VALUES('Cesare', 'Sonaglia', 26-07-2008);

SELECT * FROM studenti;

SELECT * FROM studenti WHERE anno_nascita > '31-12-2000';

SELECT nome, cognome FROM studenti ORDER BY cognome, nome;

UPDATE studenti SET anno_nascita = 2002 WHERE id = 2;
SELECT * FROM studenti;

DELETE FROM studenti WHERE id =1;

