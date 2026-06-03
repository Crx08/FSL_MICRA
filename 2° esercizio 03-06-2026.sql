CREATE DATABASE IF NOT EXISTS scuola;
USE scuola;

DROP TABLE IF EXISTS iscrizioni;
DROP TABLE IF EXISTS corsi;
DROP TABLE IF EXISTS studenti;

CREATE TABLE studenti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cognome VARCHAR(255) NOT NULL,
    anno_nascita DATE NOT NULL
);

CREATE TABLE corsi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_corso VARCHAR(255) NOT NULL,
    docente VARCHAR(255) NOT NULL
);

CREATE TABLE iscrizioni (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_studente INT NOT NULL, 
    id_corso INT NOT NULL
);

INSERT INTO corsi(nome_corso, docente) VALUES('Applicazioni Grafiche', 'Rinoldi');
INSERT INTO corsi(nome_corso, docente) VALUES('Cucina', 'Cracco');
INSERT INTO corsi(nome_corso, docente) VALUES('Informatica', 'Batocchi');

INSERT INTO studenti(nome, cognome, anno_nascita) 
VALUES('Antonio', 'Rossi', '2002-05-15');   
INSERT INTO studenti(nome, cognome, anno_nascita) 
VALUES('Luca', 'Bianchi', '2000-02-01');    
INSERT INTO studenti(nome, cognome, anno_nascita) 
VALUES('Matteo', 'Antonelli', '1999-09-29'); 
INSERT INTO studenti(nome, cognome, anno_nascita) 
VALUES('Mattia', 'Ragni', '1997-04-26');    
INSERT INTO studenti(nome, cognome, anno_nascita) 
VALUES('Cesare', 'Sonaglia', '2008-07-26');   



INSERT INTO iscrizioni(id_studente, id_corso) 
VALUES(1, 3);
INSERT INTO iscrizioni(id_studente, id_corso) 
VALUES(2, 3); 
INSERT INTO iscrizioni(id_studente, id_corso) 
VALUES(3, 2); 
INSERT INTO iscrizioni(id_studente, id_corso) 
VALUES(4, 3); 
INSERT INTO iscrizioni(id_studente, id_corso) 
VALUES(5, 2); 



SELECT * FROM studenti;
SELECT * FROM studenti WHERE anno_nascita > '2000-12-31';
SELECT nome, cognome FROM studenti ORDER BY cognome, nome;


SELECT 
    studenti.nome AS Nome, 
    studenti.cognome AS Cognome, 
    corsi.nome_corso AS Corso, 
    corsi.docente AS Docente
FROM iscrizioni
JOIN studenti ON iscrizioni.id_studente = studenti.id
JOIN corsi ON iscrizioni.id_corso = corsi.id;



UPDATE studenti SET anno_nascita = '2002-02-01' WHERE id = 2;
DELETE FROM studenti WHERE id = 1;


SELECT * FROM studenti;