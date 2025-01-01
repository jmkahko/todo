package fi.gradu.todo.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.gradu.todo.dao.CustomerDao;

/**
 * Käyttäjäpalveluiden toteutusluokka, joka vastaa käyttäjiin liittyvästä logiikasta
 */
@Service
public class CustomerServiceImpl implements CustomerServices {
	
	@Autowired
	private CustomerDao customerDao;

	@Override
	public Long logIn(String username, String password) throws SQLException {
		return customerDao.logIn(username, password);
	}

}
