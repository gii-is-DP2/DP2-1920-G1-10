
package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Comment;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.repository.springdatajpa.CommentRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;


	@Transactional
	public Iterable<Comment> findAll() {
		return commentRepository.findAll();
	}

	@Transactional
	public void save(final Comment comment) {
		commentRepository.save(comment);
	}

	@Transactional(readOnly = true)
	public Comment findCommentById(final int id) throws DataAccessException {
		return commentRepository.findById(id).get();
	}
	@Transactional
	public Iterable<Comment> findAllByProductId(final Product product) {
		return commentRepository.findAllByProductId(product.getId());
	}
	@Transactional
	public Iterable<Comment> findAllByUserId() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getName();
		String userId = principal.toString();
		return commentRepository.findAllByUserId(userId);
	}
	@Transactional
	public void delete(final Comment comment) {

		commentRepository.delete(comment);
	}

}
