package ar.edu.davinci.controller;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ar.edu.davinci.model.Image;
import ar.edu.davinci.model.Post;
import ar.edu.davinci.model.User;

@Stateless
public class PostController {

	@PersistenceContext
	private EntityManager entityManager;

	public void addPost(User user, String content, Image img) {
		Post p = new Post();
		p.setDate(new Date());
		p.setContent(content);
		p.setUser(user);
		p.setImage(img);
		entityManager.persist(p);
	}

	public List<Post> all(int from, int max) {
		TypedQuery<Post> q = entityManager.createQuery("Select p from Post p order by p.date desc", Post.class);
		q.setFirstResult(from);
		q.setMaxResults(max);
		return q.getResultList();
	}

	public List<Post> from(User user, int from, int max) {
		TypedQuery<Post> q = entityManager.createQuery("Select p from Post p where p.user = :user order by p.date desc",
				Post.class);
		q.setParameter("user", user);
		q.setFirstResult(from);
		q.setMaxResults(max);
		return q.getResultList();
	}

	public Post byId(int id) {
		return entityManager.find(Post.class, id);
	}

}
