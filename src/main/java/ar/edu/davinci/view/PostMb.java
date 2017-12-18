package ar.edu.davinci.view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ar.edu.davinci.auth.AuthMb;
import ar.edu.davinci.controller.ImageController;
import ar.edu.davinci.controller.LikeController;
import ar.edu.davinci.controller.PostController;
import ar.edu.davinci.model.Image;
import ar.edu.davinci.model.Post;

@Named
@MultipartConfig(location = "/tmp", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024
		* 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class PostMb {

	@Inject
	private AuthMb authMb;

	@Inject
	private FollowMb folloeeMb;

	@Inject
	private PostController postCntl;

	@Inject
	private ImageController imgCntl;

	@Inject
	private LikeController likeCntl;

	private Part file;

	@NotNull
	@Size(min = 2, max = 255)
	private String content;

	public void like(Post p) {
		likeCntl.like(authMb.getUser(), p);

	}

	public String amount(Post p) {
		return likeCntl.amount(p) + "";
	}

	public void submitPost() {
		try {
			Image img = null;
			if (file != null && file.getSize() > 0 && file.getContentType().startsWith("image/")) {
				img = imgCntl.upload(file);
			}
			postCntl.addPost(authMb.getUser(), content, img);
			content = null;
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se a podido postear el contenido",
					null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public List<Post> getPostList() {
		try {
			return postCntl.all(0, 10);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error interno", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
	}

	public List<Post> getMyPostList() {
		try {
			return postCntl.from(authMb.getUser(), 0, 10);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error interno", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
	}

	public List<Post> howIFollowPostList() {
		try {
			return postCntl.from(folloeeMb.getHowIAmFollowing(), 0, 10);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error interno", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

}
