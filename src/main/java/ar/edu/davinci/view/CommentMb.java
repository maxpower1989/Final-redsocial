package ar.edu.davinci.view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ar.edu.davinci.auth.AuthMb;
import ar.edu.davinci.controller.CommentController;
import ar.edu.davinci.model.Comment;
import ar.edu.davinci.model.Post;
import ar.edu.davinci.model.User;

@Named
public class CommentMb {

	@Inject
	private CommentController commentCntrl;

	@Inject
	private AuthMb authMb;

	@NotNull
	@Size(min = 2, max = 255)
	private String comment;

	public void create(Post post) {
		User user = authMb.getUser();
		commentCntrl.create(user, post, comment);
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nuevo comentario", null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<Comment> listByPost(Post post) {
		return commentCntrl.byPost(post);
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
