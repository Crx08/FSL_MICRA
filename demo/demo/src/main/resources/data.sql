-- 1. Autori
INSERT INTO autori (nome, cognome, data_nascita, data_morte) VALUES
('George', 'Orwell', '1903-06-25', '1950-01-21'),
('J.K.', 'Rowling', '1965-07-31', NULL),
('Alessandro', 'Manzoni', '1785-03-07', '1873-05-22'),
('Italo', 'Calvino', '1923-10-15', '1985-09-19');

-- 2. Libri
INSERT INTO libri (codice_isbn, titolo, id_autore, sala, scaffale, ripiano) VALUES
('9788845292613', '1984', 1, 'Sala A', 'Scaffale 3', 'Ripiano 2'),
('9788869183157', 'Harry Potter e la pietra filosofale', 2, 'Sala Fantasy', 'Scaffale 1', 'Ripiano 1'),
('9788817865227', 'I Promessi Sposi', 3, 'Sala Classici', 'Scaffale 5', 'Ripiano 4');

-- 3. Utenti
INSERT INTO utenti (nome, cognome, email, telefono) VALUES
('Cesare', 'Rossi', 'cesare@email.com', '333123456'),
('Marco', 'Verdi', 'marco@email.com', '333987654');

-- 4. Copie dei Libri
INSERT INTO copie_libri (codice_isbn, stato_conservazione, disponibile) VALUES
('9788845292613', 'Nuovo', true),       -- Copia 1 di 1984
('9788845292613', 'Rovinato', false),   -- Copia 2 di 1984 (già occupata)
('9788869183157', 'Ottimo', true);       -- Copia 3 di Harry Potter

-- 5. Prestito
INSERT INTO prestiti (id_utente, id_copia, data_inizio, data_scadenza, data_restituzione) VALUES
(1, 2, '2026-05-01', '2026-06-01', NULL);