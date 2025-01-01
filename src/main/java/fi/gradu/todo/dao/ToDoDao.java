package fi.gradu.todo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import fi.gradu.todo.dto.SearchResultDto;

/**
 * Tarjoaa tehtävien tietokantaoperaatiosta vastaavan DAO-luokan
 */
@Service
public class ToDoDao {
	
	private static final String URL = "jdbc:postgresql://localhost:5432/todo";
	private static final String USERNAME = "usernametodo";
	private static final String PASSWORD = "passwordtodo";
	
	/**
	 * Hakee kaikki Todo tehtävät
	 * @return
	 */
	public List<SearchResultDto> findTodoList() {
		List<SearchResultDto> result = new ArrayList<SearchResultDto>();
		try {
			Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Statement stmt = con.createStatement();
			String query = "SELECT * FROM todo ORDER BY luettu DESC, id DESC";
			ResultSet resultSet = stmt.executeQuery(query);
			while (resultSet.next()) {
				SearchResultDto dto = new SearchResultDto();
				dto.setId(resultSet.getLong("id"));
				dto.setTaskTitle(resultSet.getString("tehtava_otsikko"));
				dto.setTask(resultSet.getString("tehtava"));
				dto.setRead(resultSet.getBoolean("luettu"));
				result.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("Virhe : " + e);
		}

		return result;
	}
	
	/**
	 * Hakee Todo tehtävät nimen perusteella
	 * @param task Haettava tehtävän
	 * @return
	 * @throws SQLException
	 */
	public List<SearchResultDto> findTodoListByTask(String task) throws SQLException {
		if (task == null || task.isEmpty()) {
			return this.findTodoList();
		}
		List<SearchResultDto> result = new ArrayList<SearchResultDto>();
		Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		Statement stmt = con.createStatement();
		String query = "SELECT id, tehtava_otsikko, tehtava, luettu FROM todo WHERE tehtava = '" + task + "'";
		ResultSet resultSet = stmt.executeQuery(query);
		while (resultSet.next()) {
			SearchResultDto dto = new SearchResultDto();
			dto.setId(resultSet.getLong("id"));
			dto.setTaskTitle(resultSet.getString("tehtava_otsikko"));
			dto.setTask(resultSet.getString("tehtava"));
			dto.setRead(resultSet.getBoolean("luettu"));
			result.add(dto);
		}
		
		return result;
	}
	
	/**
	 * Hakee Todo tehtävän tehtävän ID:n perusteella
	 * @param id Haettavan tehtävän yksilöllinen ID
	 * @return
	 * @throws SQLException
	 */
	public List<SearchResultDto> findTodoTaskTitleById(String id) throws SQLException {
		Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		Statement stmt = con.createStatement();
		String query = "SELECT id, tehtava_otsikko, tehtava, luettu FROM todo WHERE id = '" + id + "'";
		ResultSet resultSet = stmt.executeQuery(query);
		List<SearchResultDto> result = new ArrayList<SearchResultDto>();
		while (resultSet.next()) {
			SearchResultDto dto = new SearchResultDto();
			dto.setId(resultSet.getLong("id"));
			dto.setTaskTitle(resultSet.getString("tehtava_otsikko"));
			dto.setTask(resultSet.getString("tehtava"));
			dto.setRead(resultSet.getBoolean("luettu"));
			result.add(dto);
		}
		
		return result;
	}
	
	/**
	 * Päivittää tehtävälle onko tehtävä luettu
	 * @param id Päivitettävän tehtävän yksilöllinen ID
	 */
	public void updateTodoRead(Long id) {
		try {
			Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Statement stmt = con.createStatement();
			String updateQuery = "UPDATE todo SET luettu = NOT luettu WHERE id =" + id;
			stmt.executeUpdate(updateQuery);
		} catch (SQLException e) {
			System.out.println("Virhe : " + e);
		}
	}

	/**
	 * Päivittää tehtävän tehtävä tietoa
	 * @param id Päivitettävän tehtävän yksilöllinen ID
	 * @param task Tehtävälle päivitettävä teksti
	 */
	public void updateTodo(Long id, String task) {
		try {
			Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Statement stmt = con.createStatement();
			String updateQuery = "UPDATE todo SET tehtava = '" + task + "' WHERE id =" + id;
			stmt.executeUpdate(updateQuery);
		} catch (SQLException e) {
			System.out.println("Virhe : " + e);
		}
	}

	/**
	 * Luo uuden Todo tehtävän
	 * @param taskTitle Tehtävän otsikko
	 * @param task Tehtävän sisältö
	 * @param userId
	 */
	public void createNewTodo(String taskTitle, String task, Long userId) {
		try {
			Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Statement stmt = con.createStatement();
			String insertQuery = "INSERT INTO todo (tehtava_otsikko, tehtava, luettu, kayttaja_id) VALUES ('" + taskTitle + "', '" + task + "', false, " + userId + ")";
			stmt.executeUpdate(insertQuery);
		} catch (SQLException e) {
			System.out.println("Virhe : " + e);
		}
	}	
}
