package idv.ytchang.springboot2.entity;

public class UserUpdateEvent {
	
	private final User user;

	public UserUpdateEvent(User user) {
		this.user = user;		
	}

	public User getUser() {
		return user;
	}	

}
