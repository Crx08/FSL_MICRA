CREATE DATABASE biblioteca;
USE biblioteca;
CREATE TABLE libri (
	id INT AUTO_INCREMENT PRIMARY KEY,
    titolo VARCHAR(255) NOT NULL,
    autore VARCHAR(255) NOT NULL,
    disponibile BOOLEAN DEFAULT TRUE
);
INSERT INTO libri(titolo, autore, disponibile)
VALUES('Il nome della rosa', 'Umberto Eco', TRUE);

INSERT INTO libri(titolo, autore, disponibile)
VALUES('La divina commedia', 'Dante Alighieri', FALSE);

SELECT * FROM libri;

UPDATE libri SET disponibile = TRUE WHERE id = 2;

DELETE FROM libri WHERE id =1;