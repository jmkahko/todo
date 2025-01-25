package fi.gradu.todo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Service;

import fi.gradu.todo.DatabaseConfig;
import fi.gradu.todo.dto.CustomerResultDto;

/**
 * Tarjoaa käyttäjien tietokantaoperaatiosta vastaavan DAO-luokan
 */
@Service
public class CustomerDao extends DatabaseConfig {
	
    /**
     * Tarkistaa käyttäjän kirjautumistiedot ja palauttaa käyttäjän ID:n
     * 
     * @param username Käyttäjätunnus
     * @param password Salasana
     * @return
     * @throws SQLException
     */
	public Long logIn(String username, String password) throws SQLException {
		Long result = null;
		Connection con = openConnection();
		Statement stmt = con.createStatement();
		String query = "SELECT id FROM kayttaja WHERE tunnus = '" + username + "' AND salasana ='" + password + "'";
		ResultSet resultSet = stmt.executeQuery(query);
		while (resultSet.next()) {
			result = resultSet.getLong("id");
		}
		
		return result;
	}
	
	/**
	 * Tarkistaa käyttäjän tietokannasta tallennetulla PLSQL proseduurilla ja palauttaa käyttäjän ID:n tai virheilmoituksen jos käyttäjää ei löydy
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException, UserNotFoundException
	 */
	public Long checkUser(String username, String password) throws SQLException, UserException {
		Connection con = openConnection();
		CallableStatement c = con.prepareCall("CALL tarkista_kayttaja(?,?,?,?)");
		c.setString(1, username);
		c.setString(2, password);
		c.registerOutParameter(3, java.sql.Types.BIGINT);
		c.registerOutParameter(4, java.sql.Types.VARCHAR);
		c.execute();
		
		Long result = c.getLong(3);
		String errorMessage = c.getString(4);
		if (errorMessage != null) {
			System.out.println("Virhe : " + errorMessage);
			throw new UserException(errorMessage);
		}
		
		return result;
	}
	
	/**
	 * Luo uuden käyttäjän PLSQL proseduurilla ja palauttaa uuden käyttäjän ID:n tai jos käyttäjä löytyy samalla tunnuksella niin palautetaan virhe
	 * @param username
	 * @param fullname
	 * @param password
	 * @return
	 * @throws SQLException
	 * @throws UserFoundException
	 */
	public CustomerResultDto createNewUser(String username, String fullname, String password) throws SQLException, UserException {
		Connection con = openConnection();
		CallableStatement c = con.prepareCall("CALL luo_kayttaja(?,?,?,?,?)");
		c.setString(1, username);
		c.setString(2, password);
		c.setString(3, fullname);
		c.registerOutParameter(4, java.sql.Types.BIGINT);
		c.registerOutParameter(5, java.sql.Types.VARCHAR);
		c.execute();
		
		String errorMessage = c.getString(5);
		if (errorMessage != null) {
			System.out.println("Virhe : " + errorMessage);
			throw new UserException(errorMessage);
		}
		
		CustomerResultDto result = new CustomerResultDto();
		result.setId(c.getLong(4));
		result.setUsername(username);
		result.setFullname(fullname);
		
		return result;
	}

}
