package org.springframework.samples.petclinic.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Comment;
import org.springframework.samples.petclinic.repository.springdatajpa.CommentRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CommentRepositoryTest {

	@Autowired
	CommentRepository commentRepository;

	@Test
	void shouldFindCommentId() {
		int id = 1;
		Comment comment = this.commentRepository.findCommentById(id);
		assertEquals("pepefer@gmail.com", comment.getEmail());
	}

	@Test
	void findAllByUserId() {
		String id = "prueba1";
		Iterable<Comment> comment = this.commentRepository.findAllByUserId(id);
		List<Comment> res = (List<Comment>) comment;
		assertEquals(1, res.size());
	}

	@Test
	void findAllByProductId() {
		int id = 1;
		List<Comment> comment = this.commentRepository.findAllByProductId(id);

		assertEquals(1, comment.size());
	}
	@Test
	void findAllByProductIdFail() {
		int id = 0;
		List<Comment> comment = this.commentRepository.findAllByProductId(id);
		
		assertEquals(0, comment.size());
	}
	@Test
	void shouldFindCommentIdFail() {
		int id = 0;
		Comment comment = this.commentRepository.findCommentById(id);
		assertEquals(comment, null);
	}
}
