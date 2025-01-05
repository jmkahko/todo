package fi.gradu.todo.service;

import java.sql.SQLException;

import fi.gradu.todo.dao.UserNotFoundException;

/**
 * Rajapinta käyttäjäpalveluiden määrittelyyn
 * Tarjoaa metodit käyttäjien hallintaan
 */
public interface CustomerServices {

    /**
     * Käsittelee käyttäjän kirjautumisen
     * 
     * @param username Käyttäjätunnus
     * @param password Salasana
     * @return
     * @throws SQLException
     */
	public Long logIn(String username, String password) throws SQLException;
	
	/**
	 * Tarkistaa käyttäjän tietokannasta tallennetulla PLSQL proseduurilla ja palauttaa käyttäjän ID:n tai virheilmoituksen jos käyttäjää ei löydy
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException, UserNotFoundException
	 */
	public Long checkUser(String username, String password) throws SQLException, UserNotFoundException;
}
