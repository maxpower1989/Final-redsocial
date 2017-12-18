package ar.edu.davinci.controller;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import ar.edu.davinci.model.Likes;
import ar.edu.davinci.model.Post;
import ar.edu.davinci.model.User;

@Stateless
public class LikeController {

	@PersistenceContext
	private EntityManager entityManager;

	public void like(User user, Post post) {
		Likes l = new Likes();
		l.setPost(post);
		l.setUser(user);
		TypedQuery<Likes> q = entityManager.createQuery("Select l from Likes l where l.user =:user and l.post = :post",
				Likes.class);
		q.setParameter("user", user);
		q.setParameter("post", post);
		if (q.getResultList().equals(null)) {
			entityManager.persist(l);
		} else {
			entityManager.remove(q.getSingleResult());
			entityManager.persist(l);

		}
	}

	public int amount(Post post) {
		TypedQuery<Likes> q = entityManager.createQuery("Select l from Likes l where l.post = :post", Likes.class);
		q.setParameter("post", post);
		return q.getResultList().size();
	}
}
