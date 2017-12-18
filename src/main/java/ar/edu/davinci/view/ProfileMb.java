package ar.edu.davinci.view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import ar.edu.davinci.auth.AuthMb;
import ar.edu.davinci.controller.ImageController;
import ar.edu.davinci.controller.UserController;
import ar.edu.davinci.model.Image;

@Named
@MultipartConfig(location = "/tmp", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024
		* 5, maxRequestSize = 1024 * 1024 * 5 * 5)

public class ProfileMb {
	@Inject
	private AuthMb authMb;

	@Inject
	private UserController userCntr;

	@Inject
	private ImageController imgCntl;

	private Part file;
	private String newPassword;

	public void changePassword() {
		userCntr.changePassword(authMb.getUser(), newPassword);
	}

	public void changePorfilePicture() {
		try {
			Image img = null;
			if (file != null && file.getSize() > 0 && file.getContentType().startsWith("image/")) {
				img = imgCntl.upload(file);
				userCntr.changeProfilePicture(authMb.getUser(), img);
				authMb.getUser().setProfilePicture(img);
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Se a cambiado la foto de perfil",
						null);
				FacesContext.getCurrentInstance().addMessage(null, msg);

			}

		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"No pudimos subir el erchivo de imagen" + authMb.getUser().toString() + e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, msg);

		}

	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
