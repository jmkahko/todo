package fi.gradu.todo.dto;

/**
 * Tarjoaa Todo tehtävän DTO:n
 */
public class SearchResultDto {
	
	private Long id;
	private String taskTitle;
	private String task;
	private boolean read;
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTaskTitle() {
		return this.taskTitle;
	}
	
	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}
	
	public String getTask() {
		return this.task;
	}
	
	public void setTask(String task) {
		this.task = task;
	}

	public boolean isRead() {
		return this.read;
	}
	
	public void setRead(boolean read) {
		this.read = read;;
	}
}
