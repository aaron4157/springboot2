package idv.ytchang.springboot2.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import idv.ytchang.springboot2.entity.Role;
import idv.ytchang.springboot2.entity.User;
import idv.ytchang.springboot2.mapper.UserMapper;

@Service
public class SpringUserService implements UserDetailsService {
	
	@Autowired
	private UserMapper userMapper;

	public SpringUserService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserDetails appUser = userMapper.getUserByUsername(username);
		
		if(appUser == null) throw new UsernameNotFoundException(String.format("Username invalid: %s", username));
		else {
			List<Role> roles = userMapper.getRolesByUser(appUser);
			((User) appUser).setAuthorities(roles);
		}
		
//		System.out.println("login: "+appUser.getUsername());
//		System.out.println("roles:"+appUser.getAuthorities().stream().map(obj->obj.getAuthority()).collect(Collectors.toList()));
		
		return appUser;
	}

}
