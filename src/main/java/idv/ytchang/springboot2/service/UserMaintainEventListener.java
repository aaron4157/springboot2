package idv.ytchang.springboot2.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import idv.ytchang.springboot2.entity.User;
import idv.ytchang.springboot2.entity.UserCreateEvent;
import idv.ytchang.springboot2.entity.UserUpdateEvent;

@Component
public class UserMaintainEventListener {

	public UserMaintainEventListener() {
		
	}
	
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) // default 
	public void processUserCreatedEvent(UserCreateEvent event) {
		// TODO do something after user created
		System.out.println("create user commited!");
	}
	
	@TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK, fallbackExecution = false)
	public void processUserCreateFailEvent(UserCreateEvent event) {
		User failedUser = event.getUser();
		failedUser.setId(null);
		failedUser.setCreatedBy(null);
		failedUser.setLastUpdatedBy(null);		
		System.out.println("create user rolled back");
	}
	
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) // default 
	public void processUserUpdatedEvent(UserUpdateEvent event) {
		// TODO do something after user updated
		System.out.println("update user commited!");
	}
	
	@TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK, fallbackExecution = false)
	public void processUserUpdateFailEvent(UserUpdateEvent event) {
		User failedUser = event.getUser();
		failedUser.setLastUpdatedBy(null);		
		System.out.println("update user rolled back");
	}

}
