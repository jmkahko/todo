-- Luo TODO tietokannan
CREATE DATABASE todo;

-- KAYTTAJA -taulun luontilause
CREATE TABLE kayttaja (
    id INTEGER GENERATED ALWAYS AS IDENTITY,
    tunnus VARCHAR(10),
	salasana VARCHAR(32),
	nimi VARCHAR(50),
    PRIMARY KEY(id)
);

-- TODO -taulun luontilause
CREATE TABLE todo (
    id INTEGER GENERATED ALWAYS AS IDENTITY,
    tehtava_otsikko VARCHAR(255),
    tehtava VARCHAR(4000),
    luettu BOOLEAN,
    kayttaja_id INT,
    PRIMARY KEY(id),
    CONSTRAINT FK_kayttaja_id FOREIGN KEY (kayttaja_id) REFERENCES kayttaja(id)
);

-- Lisää admin käyttäjän
INSERT INTO kayttaja (tunnus, salasana, nimi) VALUES ('admin', 'admin123', 'Admin käyttäjä');

-- Lisää toisen käyttäjän testaaja
INSERT INTO kayttaja (tunnus, salasana, nimi) VALUES ('testaaja', 'testaaja', 'Testikäyttäjä');

-- Luo TARKISTA_KAYTTAJA proseduurin. Tarkistaa löytyykö annetulla tunnuksella ja salasanalla käyttäjää
CREATE OR REPLACE PROCEDURE tarkista_kayttaja(input_tunnus VARCHAR, input_salasana VARCHAR) AS $$
DECLARE 
    found_id INTEGER;
BEGIN
    EXECUTE 'SELECT id FROM kayttaja WHERE tunnus = ''' || input_tunnus || ''' AND salasana = ''' || input_salasana || '''' INTO found_id;

    IF found_id IS NOT NULL THEN
        RAISE NOTICE 'Käyttäjä löytyi, ID: %', found_id;
    ELSE
        RAISE NOTICE 'Käyttäjää ei löytynyt parametreilla: % %', input_tunnus, input_salasana;
    END IF;
END;
$$ LANGUAGE plpgsql;

-- Luo KAYTTAJIA proseduurin. Palauttaa käyttäjien määrän KAYTTAJA -taulussa
CREATE OR REPLACE PROCEDURE kayttajia() AS $$
DECLARE 
    found_id INTEGER;
BEGIN
    EXECUTE 'SELECT COUNT(id) FROM kayttaja'
		INTO found_id;

    IF found_id IS NOT NULL THEN
        RAISE NOTICE 'Käyttäjiä löytyi: % kpl', found_id;
    ELSE
        RAISE NOTICE 'Käyttäjiä ei löytynyt';
    END IF;
END;
$$ LANGUAGE plpgsql;