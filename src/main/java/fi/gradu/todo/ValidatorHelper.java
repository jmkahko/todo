package fi.gradu.todo;

import org.springframework.stereotype.Component;

/**
 * Tarjoaa valmiita validaattoreita
 */
@Component
public class ValidatorHelper {
	
	/**
	 * Sallii kirjat pienet ja isot kirjat a-ö A-Ö, numerot 0-9 ja välilyönnit
	 */
	private static final String FULLNAME_PATTERN = "^[a-öA-Ö0-9\\s]+$";
	
	/**
	 * Sallii kirjat pienet ja isot kirjat a-ö A-Ö ja numerot 0-9
	 */
	private static final String PASSWORD_USERNAME_PATTERN = "^[a-öA-Ö0-9]+$";

	/**
	 * Validoi merkkijonon mallilla joka sallii kirjat a-ö A-Ö ja numerot 0-9
	 * @param value merkkijono
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean validateUsernameOrPassword(String value) {
		return value.matches(PASSWORD_USERNAME_PATTERN);
	}
	
	/**
	 * Validoi merkkijonon mallilla joka sallii kirjat a-ö A-Ö, numerot 0-9 ja välilyönnin
	 * @param value merkkijono
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean validateFullname(String value) {
		return value.matches(FULLNAME_PATTERN);
	}
	
	/**
	 * Validoi merkkijonon pituuden
	 * @param value merkkijono
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean validateStringLength(String value, int min, int max) {
		return value != null && value.length() >= min && value.length() <= max;
	}
}
