package fi.gradu.todo.dao;

/**
 * Tarjoaa muokatun poikkeuksen käyttäjien PLSQL hauille
 */
public class UserException extends Exception {

	private static final long serialVersionUID = -3912210880363635715L;
	
	public UserException(String errorMessage) {
		super(errorMessage);
	}

}
