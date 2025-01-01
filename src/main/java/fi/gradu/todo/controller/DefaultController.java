package fi.gradu.todo.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fi.gradu.todo.dto.SearchResultDto;
import fi.gradu.todo.service.CustomerServices;
import fi.gradu.todo.service.ToDoServices;
import jakarta.servlet.http.HttpSession;

/**
 * Tarjoaa Todo web sivustolle kontrollerit
 */
@Controller
@SessionAttributes("isLoggedIn")
public class DefaultController {

	@Autowired
	private ToDoServices todoServices;
	
	@Autowired
	private CustomerServices customerServices;
	
    /**
     * Näyttää sovelluksen etusivun ja listaa Todo-tehtävät
     * 
     * @param model Model-objekti näkymän tietojen välittämiseen
     * @param session HTTP-istunto kirjautumistietojen hallintaan
     * @return
     */
	@GetMapping("/")
	public String index(Model model, HttpSession session) {  
		List<SearchResultDto> resultList = todoServices.findTodoList();
		model.addAttribute("resultList", resultList);
		model.addAttribute("isLoggedIn", session.getAttribute("isLoggedIn"));
		return "index";
	}
	
    /**
     * Käsittelee käyttäjän kirjautumisen
     * 
     * @param username Käyttäjätunnus
     * @param password Salasana
     * @param session HTTP-istunto
     * @param model Model-objekti näkymän tietojen välittämiseen
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,  @RequestParam String password,  HttpSession session, Model model) {
    	Long loginUserId;
		try {
			loginUserId = customerServices.logIn(username, password);
	        if (loginUserId != null) {
	            session.setAttribute("isLoggedIn", true);
	            session.setAttribute("loginUserId", loginUserId);
	            return "redirect:/";
	        }
	        model.addAttribute("error", "Väärä käyttäjätunnus tai salasana");
		} catch (SQLException e) {
			model.addAttribute("error", "Tietokantavirhe: " + e);
			System.out.println("Virhe : " + e);
		}

        return "index";
    }

    /**
     * Käsittelee käyttäjän uloskirjautumisen
     * 
     * @param session HTTP-istunto
     * @param model Model-objekti näkymän tietojen välittämiseen
     * @return
     */
    @PostMapping("/logout")
    public String logout(HttpSession session, Model model) {
        session.removeAttribute("isLoggedIn");
        model.addAttribute("isLoggedIn", null);
        return "redirect:/";
    }
	
    /**
     * Hakee Todo-tehtäviä annetulla hakusanalla
     * 
     * @param searchTask Hakusana
     * @param model Model-objekti näkymän tietojen välittämiseen
     * @return
     */
	@PostMapping("/todo/search")
	public String todoSearch(@RequestParam String searchTask, Model model) {  
		List<SearchResultDto> resultList;
		try {
			resultList = todoServices.findTodoListByTask(searchTask);
			model.addAttribute("resultList", resultList);
		} catch (SQLException e) {
			model.addAttribute("error", "Tietokantavirhe: " + e);
			System.out.println("Virhe : " + e);
		}

		return "index";
	}
	
    /**
     * Päivittää Todo-tehtävän lukukuittauksen
     * 
     * @param id Päivitettävän tehtävän ID
     * @param model Model-objekti näkymän tietojen välittämiseen
     * @return
     */
	@PostMapping("/todo/updateRead")
	public String updateRead(@RequestParam Long id, Model model) {
		todoServices.updateTodoRead(id);
		return "redirect:/";
	}
	
    /**
     * Päivittää Todo-tehtävän tehtävä tiedon
     * 
     * @param id Päivitettävän tehtävän ID
     * @param todoTask Uusi tehtävän sisältö
     * @param model Model-objekti näkymän tietojen välittämiseen
     * @return
     */
	@PostMapping("/todo/updateTodo")
	public String updateTodo(@RequestParam Long id, @RequestParam String todoTask, Model model) {
		todoServices.updateTodo(id, todoTask);
		return "redirect:/";
	}
	
    /**
     * Luo uuden Todo-tehtävän
     * 
     * @param todoTitle Tehtävän otsikko
     * @param todoTask Tehtävän sisältö
     * @param model Model-objekti näkymän tietojen välittämiseen
     * @param session HTTP-istunto käyttäjätietojen hakemiseen
     * @return
     */
	@PostMapping("/todo/createNew")
	public String saveNewTodoSave(@RequestParam String todoTitle, @RequestParam String todoTask, Model model, HttpSession session) {
		Object loginUserId = session.getAttribute("loginUserId");
		Long loginId = null;
		if (loginUserId != null && loginUserId instanceof Long) {
			loginId = (Long) loginUserId;
		}
	    todoServices.createNewTodo(todoTitle, todoTask, loginId);
	    return "redirect:/";
	}
	
}
