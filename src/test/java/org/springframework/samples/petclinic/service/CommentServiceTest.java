package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Comment;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CommentServiceTest {

	@Autowired
	CommentService commentService;
	@Autowired
	ProductService productService;


	@Test
	void shouldFindCommentId() {
		int id = 1;
		Comment comment = this.commentService.findCommentById(id);
		assertEquals("pepefer@gmail.com", comment.getEmail());
	}
	@Test
	@WithMockUser(username = "prueba1")
	void findAllByUserId() {
		
		Iterable<Comment> comment = this.commentService.findAllByUserId();
		List<Comment> res  = (List<Comment>) comment;
		assertEquals(1, res.size());
	}
	@Test
	void findAllByProductId() {
	Product p = this.productService.findProductById(1);
		Iterable<Comment> comment = this.commentService.findAllByProductId(p);
		List<Comment> res  = (List<Comment>) comment;
		
		assertEquals(1, res.size());
	}
	@Test
	void shouldDeleteComment() {
		Collection<Comment> com = (Collection<Comment>) commentService.findAll();
		int tamAntes = com.size() - 1;
		commentService.delete(commentService.findCommentById(1));
		Collection<Comment> commentsAfter = (Collection<Comment>) commentService.findAll();
		int tamDespues = commentsAfter.size() - 1;
		assertTrue(tamDespues != tamAntes);
	}

}
