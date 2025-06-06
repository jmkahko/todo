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
CREATE OR REPLACE PROCEDURE tarkista_kayttaja(
	IN input_tunnus VARCHAR, 
	IN input_salasana VARCHAR, 
	OUT output_id BIGINT, 
	OUT output_virheilmoitus VARCHAR) AS $$
BEGIN
    SELECT k.id INTO output_id FROM kayttaja k WHERE k.tunnus = input_tunnus AND k.salasana = input_salasana;

    IF output_id IS NOT NULL THEN
		output_virheilmoitus := NULL;
        RAISE NOTICE 'Käyttäjä löytyi, ID: %', output_id;
    ELSE
        output_virheilmoitus := 'Käyttäjää ei löytynyt parametreilla: ' || input_tunnus || ' ' || input_salasana;
        RAISE NOTICE 'Käyttäjää ei löytynyt parametreilla: % %', input_tunnus, input_salasana;
    END IF;

EXCEPTION
	WHEN OTHERS THEN
		output_id := NULL;
		output_virheilmoitus := 'Virhe käyttäjän tarkistuksessa: ' || SQLERRM;
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

-- Luo LUO_KAYTTAJA proseduurin. Tarkistaa löytyykö jo käyttäjä samalla nimellä
CREATE OR REPLACE PROCEDURE luo_kayttaja(
	IN input_tunnus VARCHAR, 
	IN input_kokonimi VARCHAR,
	IN input_salasana VARCHAR, 
	OUT output_id BIGINT, 
	OUT output_virheilmoitus VARCHAR) AS $$
DECLARE
	loytyi_id BIGINT;
	output_id BIGINT;
BEGIN
    SELECT k.id INTO loytyi_id FROM kayttaja k WHERE k.tunnus = input_tunnus;

    IF loytyi_id IS NOT NULL THEN
		output_virheilmoitus := 'Käyttäjä löytyi annetulla tunnuksella: ' || input_tunnus;
        RAISE NOTICE 'Käyttäjä löytyi: %', input_tunnus;
    ELSE
        INSERT INTO kayttaja (tunnus, salasana, nimi) VALUES (
        	input_tunnus, 
        	input_salasana, 
        	input_kokonimi) RETURNING id INTO output_id;
        RAISE NOTICE 'Uusi käyttäjä lisätty: % %', input_tunnus, input_kokonimi;
    END IF;

EXCEPTION
	WHEN OTHERS THEN
		output_id := NULL;
		output_virheilmoitus := 'Virhe käyttäjän luonnissa: ' || SQLERRM;
END;
$$ LANGUAGE plpgsql;