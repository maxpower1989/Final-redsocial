package ar.edu.davinci.view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import ar.edu.davinci.auth.AuthMb;
import ar.edu.davinci.controller.UserController;
import ar.edu.davinci.exception.AuthenticationFailure;
import ar.edu.davinci.model.User;

@Named
public class LoginMb {

	@Inject
	private AuthMb authMb;

	@Inject
	private UserController userCntr;

	@NotNull
	private String username;

	@NotNull
	private String password;

	public String login() {
		try {
			User user = userCntr.authenticate(username, password);
			authMb.setUser(user);
			return "home?faces-redirect=true";
		} catch (AuthenticationFailure e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"No te pudiste logear, intentalo de nuevo " + e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
	}

	public String logout() {
		authMb.setUser(null);
		return "home?faces-redirect=true";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
