package fi.gradu.todo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Service;

/**
 * Tarjoaa käyttäjien tietokantaoperaatiosta vastaavan DAO-luokan
 */
@Service
public class CustomerDao {
	
	private static final String URL = "jdbc:postgresql://localhost:5432/todo";
	private static final String USERNAME = "usernametodo";
	private static final String PASSWORD = "passwordtodo";
	
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
		Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		Statement stmt = con.createStatement();
		String query = "SELECT id FROM kayttaja WHERE tunnus = '" + username + "' AND salasana ='" + password + "'";
		ResultSet resultSet = stmt.executeQuery(query);
		while (resultSet.next()) {
			result = resultSet.getLong("id");
		}
		
		return result;
	}

}
