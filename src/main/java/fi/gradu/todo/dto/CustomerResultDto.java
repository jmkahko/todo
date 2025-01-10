package fi.gradu.todo.dto;

/**
 * Tarjoaa uuden käyttäjän tuloksen DTO:n
 */
public class CustomerResultDto {

	private Long id;
	private String username;
	private String fullname;
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getFullname() {
		return this.fullname;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
}
