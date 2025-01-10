package fi.gradu.todo.service;

import java.sql.SQLException;

import fi.gradu.todo.dao.UserException;
import fi.gradu.todo.dto.CustomerResultDto;

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
	public Long checkUser(String username, String password) throws SQLException, UserException;
	
	/**
	 * Luo uuden käyttäjän PLSQL proseduurilla ja palauttaa uuden käyttäjän ID:n tai jos käyttäjä löytyy samalla tunnuksella niin palautetaan virhe
	 * @param username
	 * @param fullname
	 * @param password
	 * @return
	 * @throws SQLException
	 * @throws UserFoundException
	 */
	public CustomerResultDto createNewUser(String username, String fullname, String password) throws SQLException, UserException;
}
