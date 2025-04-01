DROP SCHEMA IF EXISTS oblig3 CASCADE;

CREATE SCHEMA oblig3;
SET search_path TO oblig3;

CREATE TABLE ansatt (
    ansatt_id SERIAL PRIMARY KEY,
    brukernavn VARCHAR(50) UNIQUE NOT NULL,
    fornavn VARCHAR(50) NOT NULL,
    etternavn VARCHAR(50) NOT NULL,
    ansettelsesdato DATE NOT NULL,
    stilling VARCHAR(50) NOT NULL,
    manedslonn INT NOT NULL
);


-- Opprett Avdeling tabellen
CREATE TABLE avdeling (
    avdeling_id SERIAL PRIMARY KEY,
    navn VARCHAR(50) NOT NULL,
    sjef_id INT,
    CONSTRAINT fk_sjef FOREIGN KEY (sjef_id) REFERENCES ansatt(ansatt_id)
);

-- Legg til en fremmednøkkel for ansatt-tabellen som refererer til avdeling
ALTER TABLE ansatt ADD COLUMN avdeling_id INT;
ALTER TABLE ansatt ADD CONSTRAINT fk_avdeling FOREIGN KEY (avdeling_id) REFERENCES avdeling(avdeling_id);

CREATE TABLE Prosjekt (
    id SERIAL PRIMARY KEY,
    navn VARCHAR(100) NOT NULL,
    beskrivelse TEXT
);

CREATE TABLE Prosjektdeltagelse (
    ansatt_id INT REFERENCES Ansatt(ansatt_id),
    prosjekt_id INT REFERENCES Prosjekt(id),
    rolle VARCHAR(50),
    timer INT DEFAULT 0,
    PRIMARY KEY (ansatt_id, prosjekt_id)
);

-- Eksempeldata for Avdeling
INSERT INTO avdeling (navn) VALUES ('IT-avdeling');
INSERT INTO avdeling (navn) VALUES ('HR-avdeling');
INSERT INTO avdeling (navn) VALUES ('Økonomiavdeling');

-- Eksempeldata
INSERT INTO ansatt (brukernavn, fornavn, etternavn, ansettelsesdato, stilling, manedslonn, avdeling_id) VALUES
('jdoe', 'John', 'Doe', '2020-03-15', 'Utvikler', 55000, 1),
('asmith', 'Anna', 'Smith', '2018-07-22', 'Prosjektleder', 65000, 2),
('bjensen', 'Bjørn', 'Jensen', '2019-11-05', 'Tester', 48000, 3),
('thansen', 'Thomas', 'Hansen', '2021-06-12', 'Utvikler', 53000, 1),
('mlarsen', 'Maria', 'Larsen', '2017-09-30', 'HR-rådgiver', 60000, 2),
('okarlsen', 'Ole', 'Karlsen', '2016-01-15', 'Regnskapsfører', 58000, 3),
('lpettersen', 'Line', 'Pettersen', '2022-04-08', 'Utvikler', 51000, 1),
('hberg', 'Henrik', 'Berg', '2015-11-25', 'HR-sjef', 72000, 2),
('smoen', 'Silje', 'Moen', '2019-02-17', 'Økonomisjef', 75000, 3),
('aknudsen', 'Anders', 'Knudsen', '2023-07-19', 'Intern', 35000, 1);


UPDATE avdeling SET sjef_id = 1 WHERE avdeling_id = 1;  -- John Doe sjef for IT
UPDATE avdeling SET sjef_id = 8 WHERE avdeling_id = 2;  -- Henrik Berg sjef for HR
UPDATE avdeling SET sjef_id = 9 WHERE avdeling_id = 3;  -- Silje Moen sjef for Økonomi

UPDATE ansatt SET stilling = 'IT-sjef' WHERE ansatt_id = 1;
UPDATE ansatt SET stilling = 'HR-sjef' WHERE ansatt_id = 8;
UPDATE ansatt SET stilling = 'Økonomisjef' WHERE ansatt_id = 9;

INSERT INTO Prosjekt (navn, beskrivelse) VALUES
('Nytt intranett', 'Utvikling av et nytt intranett for selskapet'),
('Lønnssystem', 'Oppgradering av selskapets lønnssystem'),
('HR-portalen', 'Ny portal for HR-administrasjon');

-- John Doe (Utvikler) deltar i "Nytt intranett"-prosjektet som backend-utvikler
INSERT INTO Prosjektdeltagelse (ansatt_id, prosjekt_id, rolle, timer) VALUES
(1, 1, 'Backend-utvikler', 120);

-- Anna Smith (Prosjektleder) leder "Lønnssystem"-prosjektet
INSERT INTO Prosjektdeltagelse (ansatt_id, prosjekt_id, rolle, timer) VALUES
(2, 2, 'Prosjektleder', 150);

-- Bjørn Jensen (Tester) tester "HR-portalen"
INSERT INTO Prosjektdeltagelse (ansatt_id, prosjekt_id, rolle, timer) VALUES
(3, 3, 'Tester', 90);

-- Thomas Hansen (Utvikler) jobber på "Nytt intranett"
INSERT INTO Prosjektdeltagelse (ansatt_id, prosjekt_id, rolle, timer) VALUES
(4, 1, 'Frontend-utvikler', 100);

-- Maria Larsen (HR-rådgiver) gir innspill til "HR-portalen"
INSERT INTO Prosjektdeltagelse (ansatt_id, prosjekt_id, rolle, timer) VALUES
(5, 3, 'HR-konsulent', 50);

-- Ole Karlsen (Regnskapsfører) bidrar i "Lønnssystem"
INSERT INTO Prosjektdeltagelse (ansatt_id, prosjekt_id, rolle, timer) VALUES
(6, 2, 'Finanskonsulent', 80);

-- Flere ansatte bidrar i flere prosjekter
INSERT INTO Prosjektdeltagelse (ansatt_id, prosjekt_id, rolle, timer) VALUES
(7, 1, 'Utvikler', 110),
(8, 3, 'HR-sjef', 70),
(9, 2, 'Økonomisjef', 95),
(10, 1, 'Intern', 40);

