package ar.edu.davinci.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Follower {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotNull
	@ManyToOne
	private User me;

	@NotNull
	@ManyToOne
	private User otherUser;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getMe() {
		return me;
	}

	public void setMe(User me) {
		this.me = me;
	}

	public User getOtherUser() {
		return otherUser;
	}

	public void setOtherUser(User otherUser) {
		this.otherUser = otherUser;
	}

}
