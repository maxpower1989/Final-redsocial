package ar.edu.davinci.view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import ar.edu.davinci.controller.UserController;
import ar.edu.davinci.model.User;

@Named
public class RegistrationMb {
	
	@Inject
	private UserController userCntr;
	
	private User user = new User();
	
	@NotNull
	private String confirmPass;
	
	public String register(){
		try {
			if(!confirmPass.equals(user.getPassword())){
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contraseñas no coniciden", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return null;
			}
			userCntr.create(user);
			user = null;
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registró el usuario", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "login?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error interno", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getConfirmPass() {
		return confirmPass;
	}

	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}
	
}
