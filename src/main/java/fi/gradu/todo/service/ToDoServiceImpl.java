package fi.gradu.todo.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.gradu.todo.dao.ToDoDao;
import fi.gradu.todo.dto.SearchResultDto;

/**
 * Tehtäväpalveluiden toteutusluokaka, joka vastaa tehtäviin liittyvistä logiikasta
 */
@Service
public class ToDoServiceImpl implements ToDoServices {
	
	@Autowired
	private ToDoDao todoDao;

	@Override
	public List<SearchResultDto> findTodoList() throws SQLException {
		return todoDao.findTodoList();
	}
	
	@Override
	public List<SearchResultDto> findTodoListByTask(String task) throws SQLException {
		return todoDao.findTodoListByTask(task);
	}

	@Override
	public void updateTodoRead(Long id) throws SQLException {
		todoDao.updateTodoRead(id);	
	}

	@Override
	public void updateTodo(Long id, String task) throws SQLException {
		todoDao.updateTodo(id, task);
	}

	@Override
	public void createNewTodo(String taskTitle, String task, Long userId) throws SQLException {
		todoDao.createNewTodo(taskTitle, task, userId);
	}
	
	@Override
	public List<SearchResultDto> findTodoTaskTitleById(String id) throws SQLException {
		return todoDao.findTodoTaskTitleById(id);
	}
}
