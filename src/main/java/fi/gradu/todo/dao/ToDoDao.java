package fi.gradu.todo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import fi.gradu.todo.DatabaseConfig;
import fi.gradu.todo.dto.SearchResultDto;

/**
 * Tarjoaa tehtävien tietokantaoperaatiosta vastaavan DAO-luokan
 */
@Service
public class ToDoDao extends DatabaseConfig {
	
	/**
	 * Hakee kaikki Todo tehtävät
	 * @return
	 * @throws SQLException
	 */
	public List<SearchResultDto> findTodoList() throws SQLException {
		Connection con = openConnection();
		Statement stmt = con.createStatement();
		String query = "SELECT id, tehtava_otsikko, tehtava, luettu FROM todo ORDER BY luettu DESC, id DESC";
		ResultSet resultSet = stmt.executeQuery(query);
		return mapResultSetTodo(resultSet);
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
		Connection con = openConnection();
		Statement stmt = con.createStatement();
		String query = "SELECT id, tehtava_otsikko, tehtava, luettu FROM todo WHERE tehtava = '" + task + "'";
		ResultSet resultSet = stmt.executeQuery(query);
		return mapResultSetTodo(resultSet);
	}
	
	/**
	 * Hakee Todo tehtävän tehtävän ID:n perusteella
	 * @param id Haettavan tehtävän yksilöllinen ID
	 * @return
	 * @throws SQLException
	 */
	public List<SearchResultDto> findTodoTaskTitleById(String id) throws SQLException {
		Connection con = openConnection();
		Statement stmt = con.createStatement();
		String query = "SELECT id, tehtava_otsikko, tehtava, luettu FROM todo WHERE id = '" + id + "'";
		ResultSet resultSet = stmt.executeQuery(query);
		return mapResultSetTodo(resultSet);
	}
	
	/**
	 * Muuntaa SQL-kyselyn ResultSet -tulokset SearchResultDto -objekteiksi
	 * @param resultSet SQL-kyselyn tulokset
	 * @return
	 * @throws SQLException
	 */
	private List<SearchResultDto> mapResultSetTodo(ResultSet resultSet) throws SQLException {
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
	 * @throws SQLException
	 */
	public void updateTodoRead(Long id) throws SQLException {
		Connection con = openConnection();
		Statement stmt = con.createStatement();
		String updateQuery = "UPDATE todo SET luettu = NOT luettu WHERE id =" + id;
		stmt.executeUpdate(updateQuery);
	}

	/**
	 * Päivittää tehtävän tehtävä tietoa
	 * @param id Päivitettävän tehtävän yksilöllinen ID
	 * @param task Tehtävälle päivitettävä teksti
	 * @throws SQLException
	 */
	public void updateTodo(Long id, String task) throws SQLException {
		Connection con = openConnection();
		Statement stmt = con.createStatement();
		String updateQuery = "UPDATE todo SET tehtava = '" + task + "' WHERE id =" + id;
		stmt.executeUpdate(updateQuery);
	}

	/**
	 * Luo uuden Todo tehtävän
	 * @param taskTitle Tehtävän otsikko
	 * @param task Tehtävän sisältö
	 * @param userId
	 * @throws SQLException
	 */
	public void createNewTodo(String taskTitle, String task, Long userId) throws SQLException {
		Connection con = openConnection();
		Statement stmt = con.createStatement();
		String insertQuery = "INSERT INTO todo (tehtava_otsikko, tehtava, luettu, kayttaja_id) VALUES ('" + taskTitle + "', '" + task + "', false, " + userId + ")";
		stmt.executeUpdate(insertQuery);
	}	
}
