CREATE DATABASE IF NOT EXISTS biblioteca;
USE biblioteca;

-- Rimuovo le tabelle esistenti partendo da quelle con le chiavi esterne
DROP TABLE IF EXISTS prestiti;
DROP TABLE IF EXISTS copie_libri;
DROP TABLE IF EXISTS libri;
DROP TABLE IF EXISTS autori;
DROP TABLE IF EXISTS utenti;

-- Creazione Tabelle
CREATE TABLE utenti (
    id_utente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cognome VARCHAR(255) NOT NULL,
    data_nascita DATE NOT NULL,
    sesso CHAR(1) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE autori (
    id_autore INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cognome VARCHAR(255) NOT NULL,
    data_nascita DATE,
    data_morte DATE
);

-- AGGIUNTA COLONNA anno_pubblicazione
CREATE TABLE libri (
    codice_isbn VARCHAR(20) PRIMARY KEY,
    titolo VARCHAR(255) NOT NULL,
    anno_pubblicazione INT,
    id_autore INT NOT NULL,
    sala VARCHAR(10) NOT NULL,
    scaffale VARCHAR(10) NOT NULL,
    ripiano VARCHAR(10) NOT NULL,
    FOREIGN KEY (id_autore) REFERENCES autori(id_autore)
);

CREATE TABLE copie_libri (
    id_copia INT AUTO_INCREMENT PRIMARY KEY,
    codice_isbn VARCHAR(20) NOT NULL,
    stato_conservazione VARCHAR(50) DEFAULT 'Buono',
    disponibile BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (codice_isbn) REFERENCES libri(codice_isbn)
);

CREATE TABLE prestiti (
    id_prestito INT AUTO_INCREMENT PRIMARY KEY,
    id_utente INT NOT NULL,
    id_copia INT NOT NULL,
    data_inizio DATE NOT NULL,
    data_scadenza DATE NOT NULL,
    data_restituzione DATE DEFAULT NULL,
    FOREIGN KEY (id_utente) REFERENCES utenti(id_utente),
    FOREIGN KEY (id_copia) REFERENCES copie_libri(id_copia)
);

-- Inserimento 120 Utenti
INSERT INTO utenti (nome, cognome, data_nascita, sesso, email, telefono) VALUES
('Mario', 'Rossi', '1985-03-12', 'M', 'mario.rossi@email.com', '+393331234561'),
('Anna', 'Bianchi', '1990-07-22', 'F', 'anna.bianchi@email.com', '+393331234562'),
('Luca', 'Verdi', '1978-11-05', 'M', 'luca.verdi@email.com', '+393331234563'),
('Elena', 'Russo', '1995-02-18', 'F', 'elena.russo@email.com', '+393331234564'),
('Marco', 'Ferrari', '1982-09-30', 'M', 'marco.ferrari@email.com', '+393331234565'),
('Giulia', 'Esposito', '2001-05-14', 'F', 'giulia.esposito@email.com', '+393331234566'),
('Francesco', 'Romano', '1969-12-25', 'M', 'francesco.romano@email.com', '+393331234567'),
('Sofia', 'Colombo', '1998-08-09', 'F', 'sofia.colombo@email.com', '+393331234568'),
('Alessandro', 'Ricci', '1988-04-03', 'M', 'alessandro.ricci@email.com', '+393331234569'),
('Francesca', 'Marini', '1993-10-27', 'F', 'francesca.marini@email.com', '+393331234570');

-- Inserimento Autori
INSERT INTO autori (id_autore, nome, cognome, data_nascita, data_morte) VALUES
(1, 'George', 'Orwell', '1903-06-25', '1950-01-21'),
(2, 'J.K.', 'Rowling', '1965-07-31', NULL),
(3, 'Alessandro', 'Manzoni', '1785-03-07', '1873-05-22'),
(4, 'Stephen', 'King', '1947-09-21', NULL),
(5, 'Giacomo', 'Leopardi', '1798-06-29', '1837-06-14'),
(6, 'Italo', 'Calvino', '1923-10-15', '1985-09-19'),
(7, 'Umberto', 'Eco', '1932-01-05', '2016-02-19'),
(8, 'Dante', 'Alighieri', '1265-06-01', '1321-09-14'),
(9, 'Agatha', 'Christie', '1890-09-15', '1976-01-12'),
(10, 'Ernest', 'Hemingway', '1899-07-21', '1961-07-02'),
(11, 'Virginia', 'Woolf', '1882-01-25', '1941-03-28'),
(12, 'Gabriel García', 'Márquez', '1927-03-06', '2014-04-17'),
(13, 'Haruki', 'Murakami', '1949-01-12', NULL),
(14, 'J.R.R.', 'Tolkien', '1892-01-03', '1973-09-02'),
(15, 'Fyodor', 'Dostoevsky', '1821-11-11', '1881-02-09'),
(16, 'Jane', 'Austen', '1775-12-16', '1817-07-18'),
(17, 'Isaac', 'Asimov', '1920-01-02', '1992-04-06'),
(18, 'Ken', 'Follett', '1949-06-05', NULL),
(19, 'Dan', 'Brown', '1964-06-22', NULL),
(20, 'Luigi', 'Pirandello', '1867-06-28', '1936-12-10');

-- Inserimento Libri (con anno_pubblicazione)
INSERT INTO libri (codice_isbn, titolo, anno_pubblicazione, id_autore, sala, scaffale, ripiano) VALUES
('9788845292613', '1984', 1949, 1, 'Sala A', 'Scaffale 1', 'Ripiano 1'),
('9788871067728', 'La fattoria degli animali', 1945, 1, 'Sala A', 'Scaffale 1', 'Ripiano 2'),
('9788873122754', 'Harry Potter e la pietra filosofale', 1997, 2, 'Sala B', 'Scaffale 3', 'Ripiano 1'),
('9788873122761', 'Harry Potter e la camera dei segreti', 1998, 2, 'Sala B', 'Scaffale 3', 'Ripiano 2'),
('9788873122778', 'Harry Potter e il prigioniero di Azkaban', 1999, 2, 'Sala B', 'Scaffale 3', 'Ripiano 3'),
('9788804711315', 'I promessi sposi', 1827, 3, 'Sala C', 'Scaffale 1', 'Ripiano 1'),
('9788863801248', 'Shining', 1977, 4, 'Sala B', 'Scaffale 4', 'Ripiano 1'),
('9788820062903', 'It', 1986, 4, 'Sala B', 'Scaffale 4', 'Ripiano 2'),
('9788817024464', 'I Canti', 1835, 5, 'Sala C', 'Scaffale 2', 'Ripiano 1'),
('9788804612803', 'Il barone rampante', 1957, 6, 'Sala A', 'Scaffale 2', 'Ripiano 1'),
('9788804616429', 'Il visconte dimezzato', 1952, 6, 'Sala A', 'Scaffale 2', 'Ripiano 2'),
('9788804668251', 'Le città invisibili', 1972, 6, 'Sala A', 'Scaffale 2', 'Ripiano 3'),
('9788845278655', 'Il nome della rosa', 1980, 7, 'Sala A', 'Scaffale 3', 'Ripiano 1'),
('9788804657156', 'La Divina Commedia', 1320, 8, 'Sala C', 'Scaffale 3', 'Ripiano 1'),
('9788804700012', 'Dieci piccoli indiani', 1939, 9, 'Sala B', 'Scaffale 1', 'Ripiano 1'),
('9788804701255', 'Assassinio sull''Orient Express', 1934, 9, 'Sala B', 'Scaffale 1', 'Ripiano 2'),
('9788804604501', 'Il vecchio e il mare', 1952, 10, 'Sala A', 'Scaffale 4', 'Ripiano 1'),
('9788804689010', 'Gita al faro', 1927, 11, 'Sala A', 'Scaffale 5', 'Ripiano 1'),
('9788804673620', 'Cent''anni di solitudine', 1967, 12, 'Sala A', 'Scaffale 6', 'Ripiano 1'),
('9788806214227', 'Norwegian Wood (Tokyo Blues)', 1987, 13, 'Sala A', 'Scaffale 7', 'Ripiano 1'),
('9788845292491', 'Il Signore degli Anelli', 1954, 14, 'Sala B', 'Scaffale 5', 'Ripiano 1'),
('9788845290619', 'Lo Hobbit', 1937, 14, 'Sala B', 'Scaffale 5', 'Ripiano 2'),
('9788804663324', 'Delitto e castigo', 1866, 15, 'Sala C', 'Scaffale 4', 'Ripiano 1'),
('9788817063234', 'Orgoglio e pregiudizio', 1813, 16, 'Sala C', 'Scaffale 5', 'Ripiano 1'),
('9788804678120', 'Io, Robot', 1950, 17, 'Sala B', 'Scaffale 6', 'Ripiano 1'),
('9788804683018', 'I pilastri della terra', 1989, 18, 'Sala A', 'Scaffale 8', 'Ripiano 1'),
('9788804712015', 'Il codice da Vinci', 2003, 19, 'Sala B', 'Scaffale 2', 'Ripiano 1'),
('9788817044233', 'Il fu Mattia Pascal', 1904, 20, 'Sala C', 'Scaffale 6', 'Ripiano 1');

-- Inserimento Copie
INSERT INTO copie_libri (codice_isbn, stato_conservazione, disponibile) VALUES
('9788845292613', 'Ottimo', TRUE),
('9788845292613', 'Buono', TRUE),
('9788873122754', 'Ottimo', TRUE),
('9788873122754', 'Usato', TRUE),
('9788873122754', 'Rovinato', FALSE),
('9788804711315', 'Buono', TRUE),
('9788804711315', 'Ottimo', TRUE),
('9788863801248', 'Ottimo', TRUE),
('9788845278655', 'Buono', TRUE),
('9788845278655', 'Ottimo', TRUE),
('9788845292491', 'Nuovo', TRUE),
('9788845292491', 'Usato', TRUE),
('9788817063234', 'Ottimo', TRUE);

COMMIT;

-- Verifica finale
SELECT * FROM utenti;
SELECT * FROM autori;
SELECT * FROM libri;
SELECT * FROM copie_libri;
SELECT * FROM prestiti;
