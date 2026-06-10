CREATE DATABASE IF NOT EXISTS biblioteca;
USE biblioteca;

-- Rimuovo le tabelle esistenti partendo da quelle con le chiavi esterne per evitare errori di vincolo
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
('Francesca', 'Marini', '1993-10-27', 'F', 'francesca.marini@email.com', '+393331234570'),
('Davide', 'Bruno', '1980-01-15', 'M', 'davide.bruno@email.com', '+393331234571'),
('Chiara', 'Gallo', '1992-06-21', 'F', 'chiara.gallo@email.com', '+393331234572'),
('Lorenzo', 'Conti', '1987-11-12', 'M', 'lorenzo.conti@email.com', '+393331234573'),
('Martina', 'De Luca', '1996-03-29', 'F', 'martina.deluca@email.com', '+393331234574'),
('Mattia', 'Costa', '1999-07-04', 'M', 'mattia.costa@email.com', '+393331234575'),
('Sara', 'Giordano', '1984-05-19', 'F', 'sara.giordano@email.com', '+393331234576'),
('Gabriele', 'Mancini', '1991-09-11', 'M', 'gabriele.mancini@email.com', '+393331234577'),
('Alice', 'Rizzo', '1997-12-02', 'F', 'alice.rizzo@email.com', '+393331234578'),
('Leonardo', 'Lombardi', '2003-02-24', 'M', 'leonardo.lombardi@email.com', '+393331234579'),
('Beatrice', 'Moretti', '1994-10-15', 'F', 'beatrice.moretti@email.com', '+393331234580'),
('Andrea', 'Barbieri', '1983-06-08', 'M', 'andrea.barbieri@email.com', '+393331234581'),
('Giorgia', 'Fontana', '2000-01-31', 'F', 'giorgia.fontana@email.com', '+393331234582'),
('Simone', 'Santoro', '1989-08-23', 'M', 'simone.santoro@email.com', '+393331234583'),
('Aurora', 'Mariani', '1996-04-12', 'F', 'aurora.mariani@email.com', '+393331234584'),
('Federico', 'Borghi', '1981-11-20', 'M', 'federico.borghi@email.com', '+393331234585'),
('Emma', 'Fabbri', '2002-09-05', 'F', 'emma.fabbri@email.com', '+393331234586'),
('Riccardo', 'Caruso', '1976-05-17', 'M', 'riccardo.caruso@email.com', '+393331234587'),
('Camilla', 'Rinaldi', '1995-10-24', 'F', 'camilla.rinaldi@email.com', '+393331234588'),
('Tommaso', 'Villa', '1990-03-03', 'M', 'tommaso.villa@email.com', '+393331234589'),
('Silvia', 'Ferri', '1986-07-14', 'F', 'silvia.ferri@email.com', '+393331234590'),
('Alessio', 'Monti', '1993-12-08', 'M', 'alessio.monti@email.com', '+393331234591'),
('Lucrezia', 'Cattaneo', '1998-02-19', 'F', 'lucrezia.cattaneo@email.com', '+393331234592'),
('Christian', 'Sala', '2004-06-30', 'M', 'christian.sala@email.com', '+393331234593'),
('Eleonora', 'Serra', '1988-09-13', 'F', 'eleonora.serra@email.com', '+393331234594'),
('Filippo', 'Crespi', '1985-04-25', 'M', 'filippo.crespi@email.com', '+393331234595'),
('Miriam', 'Molinari', '1991-01-07', 'F', 'miriam.molinari@email.com', '+393331234596'),
('Edoardo', 'Donati', '1997-08-16', 'M', 'edoardo.donati@email.com', '+393331234597'),
('Noemi', 'Parisi', '1999-11-22', 'F', 'noemi.parisi@email.com', '+393331234598'),
('Samuele', 'Longhi', '1980-05-28', 'M', 'samuele.longhi@email.com', '+393331234599'),
('Elisa', 'Benedetti', '1992-10-02', 'F', 'elisa.benedetti@email.com', '+393331234600'),
('Giacomo', 'Vitali', '1975-02-14', 'M', 'giacomo.vitali@email.com', '+393331234601'),
('Matilde', 'Gatti', '2001-07-09', 'F', 'matilde.gatti@email.com', '+393331234602'),
('Daniele', 'Pellegrini', '1987-12-19', 'M', 'daniele.pellegrini@email.com', '+393331234603'),
('Ludovica', 'Palumbo', '1996-06-03', 'F', 'ludovica.palumbo@email.com', '+393331234604'),
('Manuel', 'Sanna', '1994-03-26', 'M', 'manuel.sanna@email.com', '+393331234605'),
('Marta', 'Farina', '1989-09-15', 'F', 'marta.farina@email.com', '+393331234606'),
('Stefano', 'Rizzi', '1983-01-21', 'M', 'stefano.rizzi@email.com', '+393331234607'),
('Gaia', 'Montanari', '2000-04-11', 'F', 'gaia.montanari@email.com', '+393331234608'),
('Michele', 'Pagano', '1979-10-08', 'M', 'michele.pagano@email.com', '+393331234609'),
('Valentina', 'Comi', '1991-08-31', 'F', 'valentina.comi@email.com', '+393331234610'),
('Pietro', 'Calabrese', '1986-05-24', 'M', 'pietro.calabrese@email.com', '+393331234611'),
('Ilaria', 'Piras', '1995-11-04', 'F', 'ilaria.piras@email.com', '+393331234612'),
('Fabio', 'Guerra', '1973-07-17', 'M', 'fabio.guerra@email.com', '+393331234613'),
('Veronica', 'Testa', '1993-02-28', 'F', 'veronica.testa@email.com', '+393331234614'),
('Nicola', 'Bini', '1988-06-13', 'M', 'nicola.bini@email.com', '+393331234615'),
('Nicole', 'Marchi', '1998-10-19', 'F', 'nicole.marchi@email.com', '+393331234616'),
('Valerio', 'Rossetti', '1982-12-05', 'M', 'valerio.rossetti@email.com', '+393331234617'),
('Rebecca', 'Milani', '2002-03-14', 'F', 'rebecca.milani@email.com', '+393331234618'),
('Gianluca', 'Fiore', '1977-09-22', 'M', 'gianluca.fiore@email.com', '+393331234619'),
('Sofia', 'Galli', '1994-07-01', 'F', 'sofia.galli2@email.com', '+393331234620'),
('Claudio', 'De Angelis', '1984-04-09', 'M', 'claudio.deangelis@email.com', '+393331234621'),
('Alessia', 'Poli', '1997-01-26', 'F', 'alessia.poli@email.com', '+393331234622'),
('Roberto', 'Sorrentino', '1971-11-15', 'M', 'roberto.sorrentino@email.com', '+393331234623'),
('Federica', 'Neri', '1992-08-12', 'F', 'federica.neri@email.com', '+393331234624'),
('Enrico', 'Costantini', '1989-05-02', 'M', 'enrico.costantini@email.com', '+393331234625'),
('Arianna', 'Mazza', '2001-10-23', 'F', 'arianna.mazza@email.com', '+393331234626'),
('Paolo', 'Pellegrino', '1968-03-18', 'M', 'paolo.pellegrino@email.com', '+393331234627'),
('Chiara', 'Longo', '1996-09-07', 'F', 'chiara.longo@email.com', '+393331234628'),
('Giorgio', 'Giuliani', '1981-06-30', 'M', 'giorgio.giuliani@email.com', '+393331234629'),
('Bianca', 'Fonti', '1999-04-15', 'F', 'bianca.fonti@email.com', '+393331234630'),
('Marco', 'Riva', '1990-12-11', 'M', 'marco.riva@email.com', '+393331234631'),
('Greta', 'Orlando', '2003-07-28', 'F', 'greta.orlando@email.com', '+393331234632'),
('Salvatore', 'Leone', '1974-02-27', 'M', 'salvatore.leone@email.com', '+393331234633'),
('Annamaria', 'Ferretti', '1985-08-19', 'F', 'annamaria.ferretti@email.com', '+393331234634'),
('Massimo', 'Marchetti', '1980-01-04', 'M', 'massimo.marchetti@email.com', '+393331234635'),
('Erika', 'Angeli', '1993-05-25', 'F', 'erika.angeli@email.com', '+393331234636'),
('Luca', 'Palma', '1995-10-10', 'M', 'luca.palma@email.com', '+393331234637'),
('Giada', 'Castelli', '1998-03-03', 'F', 'giada.castelli@email.com', '+393331234638'),
('Emanuele', 'Amato', '1987-11-21', 'M', 'emanuele.amato@email.com', '+393331234639'),
('Manuela', 'Serra', '1983-06-14', 'F', 'manuela.serra2@email.com', '+393331234640'),
('Alberto', 'Messina', '1979-09-02', 'M', 'alberto.messina@email.com', '+393331234641'),
('Melissa', 'Dini', '2000-12-18', 'F', 'melissa.dini@email.com', '+393331234642'),
('Vincenzo', 'Gallo', '1972-04-13', 'M', 'vincenzo.gallo2@email.com', '+393331234643'),
('Caterina', 'Gori', '1996-07-07', 'F', 'caterina.gori@email.com', '+393331234644'),
('Diego', 'Bellini', '1991-02-22', 'M', 'diego.bellini@email.com', '+393331234645'),
('Serena', 'Vecchi', '1988-10-31', 'F', 'serena.vecchi@email.com', '+393331234646'),
('Carmine', 'Abate', '1984-05-16', 'M', 'carmine.abate@email.com', '+393331234647'),
('Angelica', 'Conte', '2002-01-08', 'F', 'angelica.conte@email.com', '+393331234648'),
('Domenico', 'Fazio', '1976-08-27', 'M', 'domenico.fazio@email.com', '+393331234649'),
('Livia', 'Martini', '1994-03-11', 'F', 'livia.martini@email.com', '+393331234650'),
('Antonio', 'Adamo', '1982-11-09', 'M', 'antonio.adamo@email.com', '+393331234651'),
('Nadia', 'Gentile', '1989-07-24', 'F', 'nadia.gentile@email.com', '+393331234652'),
('Giovanni', 'Basile', '1970-12-03', 'M', 'giovanni.basile@email.com', '+393331234653'),
('Irene', 'Pellegrino', '1997-05-19', 'F', 'irene.pellegrino@email.com', '+393331234654'),
('Luigi', 'De Rosa', '1985-02-15', 'M', 'luigi.derosa@email.com', '+393331234655'),
('Marta', 'Piras', '1991-09-28', 'F', 'marta.piras2@email.com', '+393331234656'),
('Matteo', 'Negri', '1993-04-06', 'M', 'matteo.negri@email.com', '+393331234657'),
('Laura', 'Riva', '1986-01-20', 'F', 'laura.riva@email.com', '+393331234658'),
('Angelo', 'Battaglia', '1978-06-12', 'M', 'angelo.battaglia@email.com', '+393331234659'),
('Debora', 'Caputo', '1995-10-04', 'F', 'debora.caputo@email.com', '+393331234660'),
('Paolo', 'Montalto', '1983-08-14', 'M', 'paolo.montalto@email.com', '+393331234661'),
('Cristina', 'Grassi', '1990-03-22', 'F', 'cristina.grassi@email.com', '+393331234662'),
('Stefano', 'Carbone', '1987-12-09', 'M', 'stefano.carbone@email.com', '+393331234663'),
('Silvia', 'Bernardi', '1992-05-17', 'F', 'silvia.bernardi@email.com', '+393331234664'),
('Raffaele', 'Fiorentino', '1981-11-26', 'M', 'raffaele.fiorentino@email.com', '+393331234665'),
('Tiziana', 'Marchetti', '1975-07-08', 'F', 'tiziana.marchetti@email.com', '+393331234666'),
('Alex', 'Fontana', '1999-02-11', 'M', 'alex.fontana@email.com', '+393331234667'),
('Camilla', 'Barone', '1996-10-29', 'F', 'camilla.barone@email.com', '+393331234668'),
('Filippo', 'D''Angelo', '1994-04-03', 'M', 'filippo.dangelo@email.com', '+393331234669'),
('Elena', 'Donati', '1988-08-25', 'F', 'elena.donati2@email.com', '+393331234670'),
('Mirko', 'Pinto', '1992-01-14', 'M', 'mirko.pinto@email.com', '+393331234671'),
('Giulia', 'Ferretti', '2001-06-19', 'F', 'giulia.ferretti@email.com', '+393331234672'),
('Claudio', 'Galli', '1980-09-05', 'M', 'claudio.galli2@email.com', '+393331234673'),
('Barbara', 'Russo', '1977-03-12', 'F', 'barbara.russo@email.com', '+393331234674'),
('Daniele', 'Basso', '1989-11-30', 'M', 'daniele.basso@email.com', '+393331234675'),
('Beatrice', 'Mari', '1998-05-22', 'F', 'beatrice.mari@email.com', '+393331234676'),
('Christian', 'Pagano', '1995-07-04', 'M', 'christian.pagano2@email.com', '+393331234677'),
('Eleonora', 'Mancini', '1993-12-15', 'F', 'eleonora.mancini2@email.com', '+393331234678'),
('Fabrizio', 'Villa', '1982-02-27', 'M', 'fabrizio.villa@email.com', '+393331234679'),
('Sara', 'Santi', '2000-10-09', 'F', 'sara.santi@email.com', '+393331234680');

-- Inserimento Autori
INSERT INTO autori (id_autore, nome, cognome, data_nascita, data_morte) VALUES
(1, 'George', 'Orwell', '1903-06-25', '1950-01-21'),
(2, 'J.K.', 'Rowling', '1965-07-31', NULL),
(3, 'Alessandro', 'Manzoni', '1785-03-07', '1873-05-22'),
(4, 'Stephen', 'King', '1947-09-21', NULL),
(5, 'Giacomo', 'Leopardi', '1798-06-29', '1837-06-14'),
(6, 'Italo', 'Calvino', '1923-10-15', '1985-09-19'),
(7, 'Umberto', 'Eco', '1932-01-05', '2016-02-19'),
(8, 'Dante', 'Alighieri', '1265-05-01', '1321-09-14'),
(9, 'Agatha', 'Christie', '1890-09-15', '1976-01-12'),
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

-- Inserimento Libri
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
('9788845246259', 'Il miglio verde', 4, 'Sala B', 'Scaffale 4', 'Ripiano 4'),
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

-- Salva le modifiche in modo definitivo
COMMIT;

-- Verifica finale (Mostra i dati inseriti nel Workbench)
SELECT * FROM utenti;
SELECT * FROM autori;
SELECT * FROM libri;
SELECT * FROM copie_libri;
SELECT * FROM prestiti;