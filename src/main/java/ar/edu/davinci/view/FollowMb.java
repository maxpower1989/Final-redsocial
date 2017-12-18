package ar.edu.davinci.view;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ar.edu.davinci.auth.AuthMb;
import ar.edu.davinci.controller.UserController;
import ar.edu.davinci.model.User;

@Named
@ConversationScoped
public class FollowMb implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AuthMb authMb;

	@Inject
	private UserController userCntr;

	private String otherUser = "";
	private User howIAmFollowing = new User();

	public void follow() {
		this.howIAmFollowing = userCntr.follow(authMb.getUser(), otherUser);
	}

	public String getOtherUser() {
		return otherUser;
	}

	public void setOtherUser(String otherUser) {
		this.otherUser = otherUser;
	}

	public User getHowIAmFollowing() {
		return howIAmFollowing;
	}

	public void setHowIAmFollowing(User howIAmFollowing) {
		this.howIAmFollowing = howIAmFollowing;
	}

}
