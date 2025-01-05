package fi.gradu.todo.dao;

/**
 * Tarjoaa muokatun poikkeuksen PLSQL haulle, jos käyttäjää ei löydy
 */
public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = -3912210880363635715L;
	
	public UserNotFoundException(String errorMessage) {
		super(errorMessage);
	}

}
