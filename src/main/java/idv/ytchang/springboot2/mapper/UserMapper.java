package idv.ytchang.springboot2.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.userdetails.UserDetails;

import idv.ytchang.springboot2.entity.Role;
import idv.ytchang.springboot2.entity.User;

@Mapper
public interface UserMapper {

	public UserDetails getUserByUsername(String username);
	
	public List<Role> getRolesByUser(UserDetails user);
	
}
