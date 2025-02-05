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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fi.gradu.todo.ValidatorHelper;
import fi.gradu.todo.dao.UserException;
import fi.gradu.todo.dto.CustomerResultDto;
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
		try {
			List<SearchResultDto> resultList = todoServices.findTodoList();
			model.addAttribute("resultList", resultList);
			model.addAttribute("isLoggedIn", session.getAttribute("isLoggedIn"));
		} catch (SQLException e) {
			model.addAttribute("error", "Tietokantavirhe: " + e);
			System.out.println("Virhe : " + e);
		}
		return "index";
	}
	
    /**
     * Käsittelee käyttäjän kirjautumisen
     * 
     * @param username Käyttäjätunnus
     * @param password Salasana
     * @param session HTTP-istunto
     * @param redirectAttributes RedirectAttributes-objekti virhe viestien näyttämiseen redirect komennon jälkeen
     * @return
     */
    @PostMapping("/user/login")
    public String login(@RequestParam String username,  @RequestParam String password,  HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            Long loginUserId = customerServices.logIn(username, password);
            if (loginUserId != null) {
                session.setAttribute("isLoggedIn", true);
                session.setAttribute("loginUserId", loginUserId);
                return "redirect:/";
            }
            redirectAttributes.addFlashAttribute("error", "Väärä käyttäjätunnus tai salasana");
            return "redirect:/";
        } catch (SQLException e) {
            redirectAttributes.addFlashAttribute("error", "Tietokantavirhe: " + e);
            System.out.println("Virhe : " + e);
            return "redirect:/";
        }
    }

    /**
     * Käsittelee käyttäjän uloskirjautumisen
     * 
     * @param session HTTP-istunto
     * @param model Model-objekti näkymän tietojen välittämiseen
     * @return
     */
    @PostMapping("/user/logout")
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
		try {
			List<SearchResultDto> resultList = todoServices.findTodoListByTask(searchTask);
			model.addAttribute("resultList", resultList);
			return "index";
		} catch (SQLException e) {
			model.addAttribute("error", "Tietokantavirhe: " + e);
			System.out.println("Virhe : " + e);
			return "index";
		}
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
		try {
			todoServices.updateTodoRead(id);
			return "redirect:/";
		} catch (SQLException e) {
			model.addAttribute("error", "Tietokantavirhe: " + e);
			System.out.println("Virhe : " + e);
			return "index";
		}
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
		try {
			todoServices.updateTodo(id, todoTask);
			return "redirect:/";
		} catch (SQLException e) {
			model.addAttribute("error", "Tietokantavirhe: " + e);
			System.out.println("Virhe : " + e);
			return "index";
		}
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
		try {
			Object loginUserId = session.getAttribute("loginUserId");
			Long loginId = null;
			if (loginUserId != null && loginUserId instanceof Long) {
				loginId = (Long) loginUserId;
			}
		    todoServices.createNewTodo(todoTitle, todoTask, loginId);
		    return "redirect:/";
		} catch (SQLException e) {
			model.addAttribute("error", "Tietokantavirhe: " + e);
			System.out.println("Virhe : " + e);
			return "index";
		}
	}
	
	/**
	 * Luo uuden käyttäjän
	 * @param username Käyttäjätunnus
	 * @param fullname Käyttäjän kokonimi
	 * @param password Käyttäjän salasana
	 * @param model Model-objekti näkymän tietojen välittämiseen
	 * @param session HTTP-istunto
	 * @param redirectAttributes RedirectAttributes-objekti info viestien näyttämiseen redirect komennon jälkeen
	 * @return
	 */
	@PostMapping("/user/createNewUser")
	public String createNewUser(@RequestParam String username, @RequestParam String fullname, @RequestParam String password, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		boolean isValid = true;
		String errorMessage = "";
		if (!ValidatorHelper.validateUsernameOrPassword(username) || !ValidatorHelper.validateStringLength(username, 3, 10)) {
			isValid = false;
			errorMessage += "Uuden käyttäjän lisäys epäonnistui! Yritä uudelleen\n";
			errorMessage += "- Käyttäjänimen pituus 3 - 10 merkkiä ja joka saa sisältää kirjaimia a-ö A-Ö ja numeroita 0-9";
		}
		
		if (!ValidatorHelper.validateFullname(fullname) || !ValidatorHelper.validateStringLength(fullname, 3, 50)) {
			isValid = false;
			if (errorMessage.length() > 0) {
				errorMessage += "\n";
			}
			errorMessage += "- Kokonimen pituus 3 - 50 merkkiä ja joka saa sisältää kirjaimia a-ö A-Ö, numeroita 0-9 ja välilyöntejä";
		}
		
		if (!ValidatorHelper.validateUsernameOrPassword(password) || !ValidatorHelper.validateStringLength(password, 8, 32)) {
			isValid = false;
			if (errorMessage.length() > 0) {
				errorMessage += "\n";
			}
			errorMessage += "- Salasanan pituus 8 - 32 merkkiä ja joka saa sisältää kirjaimia a-ö A-Ö ja numeroita 0-9";;
		}

		if (isValid) {
			try {
				CustomerResultDto newUser = customerServices.createNewUser(username, fullname, password);
				if (newUser.getId() > 0) {
		            redirectAttributes.addFlashAttribute("info", "Lisätty uusi käyttäjä : " + newUser.getFullname());
		            return "redirect:/";
				}
			} catch (SQLException e) {
				model.addAttribute("error", "Tietokantavirhe: " + e);
				System.out.println("Virhe : " + e);
			} catch (UserException e) {
				model.addAttribute("error", "Käyttäjän lisäys epäonnistui: " + e);
				System.out.println("Virhe : " + e);
			}
		} else {
			model.addAttribute("error", errorMessage);
		}

		return "index";
	}
	
}
