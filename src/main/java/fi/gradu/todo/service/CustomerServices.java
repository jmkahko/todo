package fi.gradu.todo.service;

import java.sql.SQLException;

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
	
}
