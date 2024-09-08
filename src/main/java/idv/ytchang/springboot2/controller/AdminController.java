package idv.ytchang.springboot2.controller;

import java.io.IOException;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import idv.ytchang.springboot2.entity.User;
import idv.ytchang.springboot2.service.UserMaintainService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	UserMaintainService userService;
	
	@RequestMapping("")
	public String index(Model model) {
		
		return "admin/index";
	}
	
	@RequestMapping("/addUser")
	public String addUser(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("roleList", userService.queryRoleList());
		model.addAttribute("mode", "create");
		return "admin/userForm";
	}
	
	@PostMapping("/insertUser")
	public String insertUser(User user, String[] authority, MultipartFile photo, Model model, RedirectAttributes attrs) {
		String message = "";		
		String templateUrl = "redirect:../editUser/{id}";
		try {
			user.setAuthorities(authority);
			message = userService.registerUser(user, photo);
			attrs.addAttribute("id", user.getId());
			attrs.addFlashAttribute("message", message);
		} catch (PersistenceException e1) {
			e1.printStackTrace();
			message = "db operation failed";
			templateUrl = "admin/userForm";
			model.addAttribute("roleList", userService.queryRoleList());
		} catch (IOException e2) {
			e2.printStackTrace();
			message = "photo upload failed";
			templateUrl = "admin/userForm";
			model.addAttribute("roleList", userService.queryRoleList());
		} catch (Exception e) {			
			e.printStackTrace();
			message ="something wrong in registrating";
			templateUrl = "admin/userForm";
			model.addAttribute("roleList", userService.queryRoleList());
		} finally {
			user.setPassword(null);
		}
		model.addAttribute("user", user);
		model.addAttribute("mode", "create");
		model.addAttribute("message", message);
		return templateUrl;
	}
	
	@RequestMapping("/editUser/{uuid}")
	public String editUser(String uuid, Model model) {
		User user = userService.getUser(uuid);
		model.addAttribute("user", user);
		model.addAttribute("roleList", userService.queryRoleList());
		model.addAttribute("mode", "edit");
		return "admin/userForm";
	}

	@PostMapping("/updateUser")
	public String updateUser(User user, String[] authority, MultipartFile photo, Model model) {
		String message = "";		
		try {
			user.setAuthorities(authority);
			message = userService.registerUser(user, photo);
		} catch (PersistenceException e1) {
			e1.printStackTrace();
			message = "db operation failed";
		} catch (IOException e2) {
			e2.printStackTrace();
			message = "photo upload failed";
		} catch (Exception e) {			
			e.printStackTrace();
			message ="something wrong in registrating";
		} finally {
			user.setPassword(null);
		}
		model.addAttribute("user", user);
		model.addAttribute("roleList", userService.queryRoleList());
		model.addAttribute("mode", "edit");
		model.addAttribute("message", message);
		return "admin/userForm";
	}
	
}
