package fi.gradu.todo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
		String query = "SELECT id, tehtava_otsikko, tehtava, luettu FROM todo WHERE tehtava = ?";
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, task);
		ResultSet resultSet = pstmt.executeQuery();
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
		String query = "SELECT id, tehtava_otsikko, tehtava, luettu FROM todo WHERE id = ?";
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, id);
		ResultSet resultSet = pstmt.executeQuery();
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
		String updateQuery = "UPDATE todo SET luettu = NOT luettu WHERE id = ?";
		PreparedStatement pstmt = con.prepareStatement(updateQuery);
		pstmt.setLong(1, id);
		pstmt.executeUpdate();
	}

	/**
	 * Päivittää tehtävän tehtävä tietoa
	 * @param id Päivitettävän tehtävän yksilöllinen ID
	 * @param task Tehtävälle päivitettävä teksti
	 * @throws SQLException
	 */
	public void updateTodo(Long id, String task) throws SQLException {
		Connection con = openConnection();
		String updateQuery = "UPDATE todo SET tehtava = ? WHERE id = ?";
		PreparedStatement pstmt = con.prepareStatement(updateQuery);
		pstmt.setString(1, task);
		pstmt.setLong(2, id);
		pstmt.executeUpdate();
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
		String insertQuery = "INSERT INTO todo (tehtava_otsikko, tehtava, luettu, kayttaja_id) VALUES (?, ?, ?, ?)";
		PreparedStatement pstmt = con.prepareStatement(insertQuery);
		pstmt.setString(1, taskTitle);
		pstmt.setString(2, task);
		pstmt.setBoolean(3, false);
		pstmt.setLong(4, userId);
		pstmt.executeUpdate();
	}	
}
