CREATE DATABASE IF NOT EXISTS biblioteca;
USE biblioteca;

DROP TABLE IF EXISTS utenti;
DROP TABLE IF EXISTS autori;
DROP TABLE IF EXISTS libri;
DROP TABLE IF EXISTS copie_libri;
DROP TABLE IF EXISTS prestiti;


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


CREATE TABLE libri (
    codice_isbn VARCHAR(20) PRIMARY KEY, 
    titolo VARCHAR(255) NOT NULL,
    id_autore INT NOT NULL,             
    sala VARCHAR(10) NOT NULL,  
    scaffale VARCHAR(10) NOT NULL,
    ripiano VARCHAR(10) NOT NULL
);

CREATE TABLE copie_libri (
    id_copia INT AUTO_INCREMENT PRIMARY KEY,
    codice_isbn VARCHAR(20) NOT NULL,
    stato_conservazione VARCHAR(50) DEFAULT 'Buono',
    disponibile BOOLEAN DEFAULT TRUE
);

CREATE TABLE prestiti (
    id_prestito INT AUTO_INCREMENT PRIMARY KEY,
    id_utente INT NOT NULL,
    id_copia INT NOT NULL,               
    data_inizio DATE NOT NULL,
    data_scadenza DATE NOT NULL,
    data_restituzione DATE DEFAULT NULL
);

INSERT INTO autori (id_autore, nome, cognome, data_nascita, data_morte) VALUES
(1, 'George', 'Orwell', '1903-06-25', '1950-01-21'),
(2, 'J.K.', 'Rowling', '1965-07-31', NULL),
(3, 'Alessandro', 'Manzoni', '1785-03-07', '1873-05-22'),
(4, 'Stephen', 'King', '1947-09-21', NULL),
(5, 'Giacomo', 'Leopardi', '1798-06-29', '1837-06-14'),
(6, 'Italo', 'Calvino', '1923-10-15', '1985-09-19'),
(7, 'Umberto', 'Eco', '1932-01-05', '2016-02-19'),
(8, 'Dante', 'Alighieri', '1265-05-01', '1321-09-14'),
(9, 'Agatha', 'Christie', '1890-09-15', '1796-01-12'),
(10, 'Ernest', 'Hemingway', '1899-07-21', '1961-07-02'),
(11, 'Virginia', 'Woolf', '1882-01-25', '1941-03-28'),
(12, 'Gabriel', 'García Márquez', '1927-03-06', '2014-04-17'),
(13, 'Haruki', 'Murakami', '1949-01-12', NULL),
(14, 'J.R.R.', 'Tolkien', '1892-01-03', '1973-09-02'),
(15, 'Fyodor', 'Dostoevsky', '1821-11-11', '1881-02-09'),
(16, 'Jane', 'Austen', '1775-12-16', '1817-07-18'),
(17, 'Isaac', 'Asimov', '1920-01-02', '1992-04-06'),
(18, 'Ken', 'Follett', '1949-06-05', NULL),
(19, 'Dan', 'Brown', '1964-06-22', NULL),
(20, 'Luigi', 'Pirandello', '1867-06-28', '1936-12-10');

INSERT INTO libri (codice_isbn, titolo, id_autore, sala, scaffale, ripiano) VALUES
-- George Orwell (1)
('9788845292613', '1984', 1, 'Sala A', 'Scaffale 1', 'Ripiano 1'),
('9788871067728', 'La fattoria degli animali', 1, 'Sala A', 'Scaffale 1', 'Ripiano 2'),
('9788817072557', 'Omaggio alla Catalogna', 1, 'Sala A', 'Scaffale 1', 'Ripiano 3'),
-- J.K. Rowling (2)
('9788873122754', 'Harry Potter e la pietra filosofale', 2, 'Sala B', 'Scaffale 3', 'Ripiano 1'),
('9788873122761', 'Harry Potter e la camera dei segreti', 2, 'Sala B', 'Scaffale 3', 'Ripiano 2'),
('9788873122778', 'Harry Potter e il prigioniero di Azkaban', 2, 'Sala B', 'Scaffale 3', 'Ripiano 3'),
('9788873122785', 'Harry Potter e il calice di fuoco', 2, 'Sala B', 'Scaffale 3', 'Ripiano 4'),
('9788869183157', 'Il seggio vacante', 2, 'Sala B', 'Scaffale 3', 'Ripiano 5'),
-- Alessandro Manzoni (3)
('9788804711315', 'I promessi sposi', 3, 'Sala C', 'Scaffale 1', 'Ripiano 1'),
('9788817064231', 'Adelchi', 3, 'Sala C', 'Scaffale 1', 'Ripiano 2'),
-- Stephen King (4)
('9788863801248', 'Shining', 4, 'Sala B', 'Scaffale 4', 'Ripiano 1'),
('9788820062903', 'It', 4, 'Sala B', 'Scaffale 4', 'Ripiano 2'),
('9788845292019', 'Misery', 4, 'Sala B', 'Scaffale 4', 'Ripiano 3'),
('97888 sperone1', 'Il miglio verde', 4, 'Sala B', 'Scaffale 4', 'Ripiano 4'),
('9788804664536', 'The Stand (L''ombra dello scorpione)', 4, 'Sala B', 'Scaffale 4', 'Ripiano 5'),
-- Giacomo Leopardi (5)
('9788817024464', 'I Canti', 5, 'Sala C', 'Scaffale 2', 'Ripiano 1'),
('9788817122146', 'Operette morali', 5, 'Sala C', 'Scaffale 2', 'Ripiano 2'),
-- Italo Calvino (6)
('9788804612803', 'Il barone rampante', 6, 'Sala A', 'Scaffale 2', 'Ripiano 1'),
('9788804616429', 'Il visconte dimezzato', 6, 'Sala A', 'Scaffale 2', 'Ripiano 2'),
('9788804612810', 'Il cavaliere inesistente', 6, 'Sala A', 'Scaffale 2', 'Ripiano 3'),
('9788804668251', 'Le città invisibili', 6, 'Sala A', 'Scaffale 2', 'Ripiano 4'),
-- Umberto Eco (7)
('9788845278655', 'Il nome della rosa', 7, 'Sala A', 'Scaffale 3', 'Ripiano 1'),
('9788845246258', 'Il pendolo di Foucault', 7, 'Sala A', 'Scaffale 3', 'Ripiano 2'),
('9788845275814', 'Baudolino', 7, 'Sala A', 'Scaffale 3', 'Ripiano 3'),
-- Dante Alighieri (8)
('9788804657156', 'La Divina Commedia', 8, 'Sala C', 'Scaffale 3', 'Ripiano 1'),
('9788817023399', 'Vita Nuova', 8, 'Sala C', 'Scaffale 3', 'Ripiano 2'),
-- Agatha Christie (9)
('9788804700012', 'Dieci piccoli indiani', 9, 'Sala B', 'Scaffale 1', 'Ripiano 1'),
('9788804701255', 'Assassinio sull''Orient Express', 9, 'Sala B', 'Scaffale 1', 'Ripiano 2'),
('9788804703016', 'Sipario, l''ultima avventura di Poirot', 9, 'Sala B', 'Scaffale 1', 'Ripiano 3'),
-- Ernest Hemingway (10)
('9788804604501', 'Il vecchio e il mare', 10, 'Sala A', 'Scaffale 4', 'Ripiano 1'),
('9788804608127', 'Per chi suona la campana', 10, 'Sala A', 'Scaffale 4', 'Ripiano 2'),
-- Virginia Woolf (11)
('9788804689010', 'Gita al faro', 11, 'Sala A', 'Scaffale 5', 'Ripiano 1'),
('9788817065405', 'Mrs Dalloway', 11, 'Sala A', 'Scaffale 5', 'Ripiano 2'),
-- Gabriel García Márquez (12)
('9788804673620', 'Cent''anni di solitudine', 12, 'Sala A', 'Scaffale 6', 'Ripiano 1'),
('9788804680055', 'L''amore ai tempi del colera', 12, 'Sala A', 'Scaffale 6', 'Ripiano 2'),
-- Haruki Murakami (13)
('9788806214227', 'Norwegian Wood (Tokyo Blues)', 13, 'Sala A', 'Scaffale 7', 'Ripiano 1'),
('9788806228194', 'Kafka sulla spiaggia', 13, 'Sala A', 'Scaffale 7', 'Ripiano 2'),
('9788806200237', '1Q84', 13, 'Sala A', 'Scaffale 7', 'Ripiano 3'),
-- J.R.R. Tolkien (14)
('9788845292491', 'Il Signore degli Anelli', 14, 'Sala B', 'Scaffale 5', 'Ripiano 1'),
('9788845290619', 'Lo Hobbit', 14, 'Sala B', 'Scaffale 5', 'Ripiano 2'),
('9788845291241', 'Il Silmarillion', 14, 'Sala B', 'Scaffale 5', 'Ripiano 3'),
-- Fyodor Dostoevsky (15)
('9788804663324', 'Delitto e castigo', 15, 'Sala C', 'Scaffale 4', 'Ripiano 1'),
('9788817053259', 'I fratelli Karamazov', 15, 'Sala C', 'Scaffale 4', 'Ripiano 2'),
('9788817022139', 'L''idiota', 15, 'Sala C', 'Scaffale 4', 'Ripiano 3'),
-- Jane Austen (16)
('9788817063234', 'Orgoglio e pregiudizio', 16, 'Sala C', 'Scaffale 5', 'Ripiano 1'),
('9788817069151', 'Ragione e sentimento', 16, 'Sala C', 'Scaffale 5', 'Ripiano 2'),
-- Isaac Asimov (17)
('9788804678120', 'Io, Robot', 17, 'Sala B', 'Scaffale 6', 'Ripiano 1'),
('9788804719229', 'Cronache della galassia (Fondazione)', 17, 'Sala B', 'Scaffale 6', 'Ripiano 2'),
-- Ken Follett (18)
('9788804683018', 'I pilastri della terra', 18, 'Sala A', 'Scaffale 8', 'Ripiano 1'),
('9788804634225', 'La caduta dei giganti', 18, 'Sala A', 'Scaffale 8', 'Ripiano 2'),
-- Dan Brown (19)
('9788804712015', 'Il codice da Vinci', 19, 'Sala B', 'Scaffale 2', 'Ripiano 1'),
('9788804715023', 'Angeli e demoni', 19, 'Sala B', 'Scaffale 2', 'Ripiano 2'),
-- Luigi Pirandello (20)
('9788817044233', 'Il fu Mattia Pascal', 20, 'Sala C', 'Scaffale 6', 'Ripiano 1'),
('9788817053150', 'Uno, nessuno e centomila', 20, 'Sala C', 'Scaffale 6', 'Ripiano 2');

INSERT INTO copie_libri (codice_isbn, stato_conservazione, disponibile) VALUES
-- Copie per 1984
('9788845292613', 'Ottimo', TRUE),
('9788845292613', 'Buono', TRUE),
-- Copie per Harry Potter 1
('9788873122754', 'Ottimo', TRUE),
('9788873122754', 'Usato', TRUE),
('9788873122754', 'Rovinato', FALSE),
-- Copie per I Promessi Sposi
('9788804711315', 'Buono', TRUE),
('9788804711315', 'Ottimo', TRUE),
-- Copie per Shining
('9788863801248', 'Ottimo', TRUE),
-- Copie per Il Nome della Rosa
('9788845278655', 'Buono', TRUE),
('9788845278655', 'Ottimo', TRUE),
-- Copie per Il Signore degli Anelli
('9788845292491', 'Nuovo', TRUE),
('9788845292491', 'Usato', TRUE),
-- Copie per Orgoglio e Pregiudizio
('9788817063234', 'Ottimo', TRUE);

SELECT * FROM utenti;
SELECT * FROM autori;
SELECT * FROM libri;
SELECT * FROM copie_libri;
SELECT * FROM prestiti;
