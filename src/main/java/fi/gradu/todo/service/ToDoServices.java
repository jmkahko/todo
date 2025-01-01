package fi.gradu.todo.service;

import java.sql.SQLException;
import java.util.List;

import fi.gradu.todo.dto.SearchResultDto;

/**
 * Rajapinta tehtäväpalveluiden määrittelyyn
 * Tarjoaa metodit tehtävien hallintaan
 */
public interface ToDoServices {

	/**
	 * Hakee kaikki Todo tehtävät
	 * @return
	 */
	public List<SearchResultDto> findTodoList();
	
	/**
	 * Hakee kaikki Todo tehtävät tehtävän nimellä
	 * @param task Tehtävän nimi
	 * @return
	 * @throws SQLException
	 */
	public List<SearchResultDto> findTodoListByTask(String task) throws SQLException;
	
	/**
	 * Hakee Todo tehtävän tehtävän yksilöllisellä ID:llä. Hakee yhden, mutta palautetaan lista muodossa
	 * @param id Tehtävän yksilöllinen ID
	 * @return
	 * @throws SQLException
	 */
	public List<SearchResultDto> findTodoTaskTitleById(String id) throws SQLException;
	
	/**
	 * Päivittää tehtävän luettu tietoa
	 * @param id Tehtävän yksilöllinen ID
	 */
	public void updateTodoRead(Long id);
	
	/**
	 * Päivittää tehtävän sisältöä
	 * @param id Tehtävän yksilöllinen ID
	 * @param task Tehtävän sisältö
	 */
	public void updateTodo(Long id, String task);
	
	/**
	 * Luoda uuden tehtävän
	 * @param taskTitle Tehtävän otsikko
	 * @param task Tehtävän sisältö
	 * @param userId
	 */
	public void createNewTodo(String taskTitle, String task, Long userId);
	

}
