package ar.edu.davinci.auth;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import ar.edu.davinci.model.User;

@Named
@SessionScoped
public class AuthMb implements Serializable{

	private static final long serialVersionUID = -4803205115493366016L;
	
	private User user;

	public boolean isLogged() {
		return user != null;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
}
