package idv.ytchang.springboot2.entity;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@SuppressWarnings("serial")
public class User implements UserDetails {
	
	private String uuid;
	private String username;
	private String password;
	private boolean is_account_non_expired;
	private boolean is_account_non_locked;
	private boolean is_credentials_non_expired;
	private boolean is_enabled;
	private Date date_created;
	private String created_by;
	private Date last_updated;
	private String last_updated_by;
	private List<Role> authorities;
	

	public User() {
		
	}
	
	public User(String username, String password, List<Role> authorities, String createdBy) {
		this.username = username;
		this.password = password;
		this.is_account_non_expired = true;
		this.is_account_non_locked = true;
		this.is_credentials_non_expired = false;
		this.is_enabled = false;
		this.date_created = this.last_updated = new Date();
		this.created_by = this.last_updated_by = createdBy;
		this.authorities = authorities;
	}
	

	public String getId() {
		return uuid;
	}


	public void setId(String uuid) {
		this.uuid = uuid;
	}


	public Date getDateCreated() {
		return date_created;
	}


	public void setDateCreated(Date dateCreated) {
		this.date_created = dateCreated;
	}


	public String getCreatedBy() {
		return created_by;
	}


	public void setCreatedBy(String createdBy) {
		this.created_by = createdBy;
	}


	public Date getLastUpdated() {
		return last_updated;
	}


	public void setLastUpdated(Date lastUpdated) {
		this.last_updated = lastUpdated;
	}


	public String getLastUpdatedBy() {
		return last_updated_by;
	}


	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.last_updated_by = lastUpdatedBy;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	public void setAuthorities(Collection<? extends GrantedAuthority> roles) {
		this.authorities = (List<Role>) roles;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return is_account_non_expired;
	}
	
	public void setIsAccountNonExpired(boolean isAccountNonLocked) {
		this.is_account_non_expired = isAccountNonLocked;
	}

	@Override
	public boolean isAccountNonLocked() {
		return is_account_non_locked;
	}
	
	public void setIsAccountNonLocked(boolean isAccountNonLocked) {
		this.is_account_non_locked = isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return is_credentials_non_expired;
	}
	
	public void setIsCredentialsNonExpired(boolean isCredentialsNonExpired) {
		this.is_credentials_non_expired = isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return is_enabled;
	}
	
	public void setIsEnabled(boolean isEnabled) {
		this.is_enabled = isEnabled;
	}

}
