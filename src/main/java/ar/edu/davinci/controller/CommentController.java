package ar.edu.davinci.controller;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ar.edu.davinci.model.Comment;
import ar.edu.davinci.model.Post;
import ar.edu.davinci.model.User;

@Stateless
public class CommentController {

	@PersistenceContext
	private EntityManager entityManager;
	
	public void create(User user, Post post,String content){
		post = entityManager.merge(post);
		Comment c = new Comment();
		c.setDate(new Date());
		c.setPost(post);
		c.setUser(user);
		c.setContent(content);
		entityManager.persist(c);
	}
	
	
	public List<Comment> byPost(Post post){
		String jpql = "Select c From Comment c where c.post = :post";
		TypedQuery<Comment> q = entityManager.createQuery(jpql, Comment.class);
		q.setParameter("post", post);
		return q.getResultList();
	}
}
