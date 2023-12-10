package idv.ytchang.springboot2.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Springboot2Controller {

	@RequestMapping("/")
	public String index(Model model) {
		
		model.addAttribute("now", new Date());
		return "index";
	}
	
}
