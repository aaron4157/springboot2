package idv.ytchang.springboot2.entity;

import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
public class Role implements GrantedAuthority {
	
	private String uuid;
	private String role_name;

	public Role() {
		// TODO Auto-generated constructor stub
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return role_name;
	}
	
	public void setAuthority(String roleName) {
		this.role_name = roleName;
	}

}
