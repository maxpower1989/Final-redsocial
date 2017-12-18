package ar.edu.davinci.controller;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import ar.edu.davinci.exception.AuthenticationFailure;
import ar.edu.davinci.model.Follower;
import ar.edu.davinci.model.Image;
import ar.edu.davinci.model.User;

@Stateless
public class UserController {

	@PersistenceContext
	private EntityManager entityManager;

	public void create(User user) {
		entityManager.persist(user);
	}

	public User byId(int id) {
		return entityManager.find(User.class, id);
	}

	public User byName(String name) {
		String jpql = "Select u from User u where u.name = :name ";
		TypedQuery<User> q = entityManager.createQuery(jpql, User.class);
		q.setParameter("name", name);
		return q.getSingleResult();
	}

	public User follow(User me, String otherUser) {
		if (byName(otherUser).getName().equals(otherUser)) {
			Follower f = new Follower();
			f.setMe(me);
			f.setOtherUser(byName(otherUser));
			entityManager.persist(f);
			TypedQuery<Follower> fq = entityManager.createQuery(
					"Select f from Follower f where f.me =:me and f.otherUser =:otherUser", Follower.class);
			fq.setParameter("me", me);
			fq.setParameter("otherUser", byName(otherUser));
			if (fq.getResultList().size() < 1) {
				// entityManager.persist(f);
			} else {
				for (int i = 0; i < fq.getResultList().size(); i++) {
					entityManager.remove(fq.getResultList().get(i));
				}
				entityManager.persist(f);
			}
		}

		return byName(otherUser);
	}

	public User authenticate(String username, String password) throws AuthenticationFailure {
		try {
			String jpql = "Select u from User u where u.name = :username and u.password = :password";
			TypedQuery<User> q = entityManager.createQuery(jpql, User.class);
			q.setParameter("username", username);
			q.setParameter("password", password);
			return q.getSingleResult();
		} catch (PersistenceException e) {
			throw new AuthenticationFailure(e);
		}
	}

	public void changeProfilePicture(User u, Image img) {

		String jpql = "Update User u SET u.profilePicture = :img where u= :id";
		TypedQuery<User> q = entityManager.createQuery(jpql, User.class);
		q.setParameter("id", u);
		q.setParameter("img", img);
		q.executeUpdate();

	}

	public void changePassword(User u, String password) {

		String jpql = "Update User u SET u.password = :password where u= :id";
		TypedQuery<User> q = entityManager.createQuery(jpql, User.class);
		q.setParameter("id", u);
		q.setParameter("password", password);
		q.executeUpdate();

	}

}
