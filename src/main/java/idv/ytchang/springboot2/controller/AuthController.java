package idv.ytchang.springboot2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

	@GetMapping("/login")
	public String login(@RequestParam(value="logout", required=false) String logout, 
			@RequestParam(value="error", required=false) String error, 
			Model model) {
		
		if(logout != null) model.addAttribute("logout", true);
		if(error != null) model.addAttribute("error", true);		
		return "auth/login";
	}
	
	@GetMapping("/signout")
	public String signout() {
		
		return "auth/logout";
	}
}
