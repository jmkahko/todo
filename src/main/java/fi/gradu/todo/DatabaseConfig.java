package fi.gradu.todo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Tarjoaa pääsyn application.properties tiedoston spring.datasource muuttujiin
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public abstract class DatabaseConfig {
	private String url;
	private String username;
	private String password;
	
	/**
	 * Hakee URL-osoitteen
	 * @return
	 */
	public String getUrl() {
		return this.url;
	}
	
	/**
	 * Asettaa URL-osoitteen
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * Hakee käyttäjänimen
	 * @return
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Asettaa käyttäjänimen
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Hakee salasanan
	 * @return
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Asettaa salasanan
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Luo tietokanta yhteyden
	 * @return
	 * @throws SQLException
	 */
	public Connection openConnection() throws SQLException {
		return DriverManager.getConnection(
				this.getUrl(), 
				this.getUsername(), 
				this.getPassword()
		);
	}
}
