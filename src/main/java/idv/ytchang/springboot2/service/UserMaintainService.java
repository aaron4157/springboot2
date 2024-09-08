package idv.ytchang.springboot2.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import idv.ytchang.springboot2.entity.*;
import idv.ytchang.springboot2.mapper.UserMapper;

@Service
public class UserMaintainService {
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private FileService fileService;
	
	public UserMaintainService() {
		
	}
	
	public List<Role> queryRoleList() {
		return userMapper.queryRoles();
	}
	
	public User getUser(String uuid) {
		User user =  userMapper.getUserById(uuid);
		user.setAuthorities(userMapper.getRolesByUser(user));
		return user;
	}
	
	@Transactional
	public String registerUser(User user, MultipartFile file) throws IllegalStateException, IOException {
		String message = "";
		
		UUID uuid = UUID.randomUUID();
		user.setId(uuid.toString());
		String passwordCrypted = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(passwordCrypted);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		user.setCreatedBy(authentication.getName());
		user.setLastUpdatedBy(authentication.getName());
		
		userMapper.insertUser(user);
		userMapper.addRoles(user);
		applicationEventPublisher.publishEvent(new UserCreateEvent(user));		
		if(file != null && !file.isEmpty()) {
			fileService.uploadFile(file, user.getUsername());
		}
		message = "register success";		
		return message;
		
	}
	
	@Transactional
	public String updateUser(User user, MultipartFile file) throws NullPointerException, IllegalStateException, IOException {
		String message = "";
		
		String password = user.getPassword();
		if(password != null && !"".equals(password)) {
			String passwordCrypted = bCryptPasswordEncoder.encode(user.getPassword());
			user.setPassword(passwordCrypted);
		}		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		user.setLastUpdatedBy(authentication.getName());
		
		userMapper.updateUser(user);
		userMapper.initializeRoles(user);
		userMapper.addRoles(user);
		applicationEventPublisher.publishEvent(new UserUpdateEvent(user));
		if(file != null && !file.isEmpty()) {
			fileService.uploadFile(file, user.getUsername());
		}
		
		message = "update success";
		return message;
	}

}
