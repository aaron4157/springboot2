package idv.ytchang.springboot2.entity;

public class UserCreateEvent {
	
	private final User user;

	public UserCreateEvent(User user) {
		this.user = user;		
	}

	public User getUser() {
		return user;
	}	

}
