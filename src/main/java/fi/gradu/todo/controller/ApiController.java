package fi.gradu.todo.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fi.gradu.todo.dto.SearchResultDto;
import fi.gradu.todo.service.ToDoServices;

/**
 * Tarjoaa REST-rajapintakontrollerin Todo-tehtävien hakutoiminnoille
 */
@RestController
public class ApiController {

	@Autowired
	private ToDoServices todoServices;
	
    /**
     * Hakee Todo-tehtävän ID:n perusteella
     * 
     * @param id Haettavan tehtävän yksilöllinen tunniste
     * @return
     * @throws SQLException
     */
	@GetMapping("/todo/search/{id}")
	public List<SearchResultDto> todoSearchById(@PathVariable String id) {  
		List<SearchResultDto> result = null;
		try {
			result = todoServices.findTodoTaskTitleById(id);
		} catch (SQLException e) {
			System.out.println("Virhe : " + e);
		}
		return result;
	}
}
