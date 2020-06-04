
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Comment;

public interface CommentRepository extends CrudRepository<Comment, Integer> {

	@Query("SELECT c FROM Comment c WHERE c.user=:usernameid")
	Iterable<Comment> findAllByUserId(@Param("usernameid") String userId);

	@Query("SELECT c FROM Comment c WHERE c.id = ?1")
	Comment findCommentById(int commentId) throws DataAccessException;

	@Query("SELECT c FROM Comment c WHERE c.producto.id=:productId")
	List<Comment> findAllByProductId(@Param("productId") Integer productId);
}
