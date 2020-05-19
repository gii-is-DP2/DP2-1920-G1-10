
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import javax.validation.Valid;

import org.h2.security.auth.AuthConfigException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Comment;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CommentService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	private CommentService	commentService;

	@Autowired
	private ProductService	productService;


	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(path = "/new/{productId}")
	public String initCreationForm(@PathVariable("productId") final int productId, final ModelMap modelMap) {
		Product product = productService.findProductById(productId);

		String view = "comments/editComment";
		modelMap.addAttribute("comment", new Comment());
		modelMap.addAttribute("product", product);
		return view;
	}

	@PostMapping(path = "/save")
	public String processCreationForm(@Valid final Comment comment, @RequestParam final int id, final BindingResult res, final ModelMap modelMap) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getName();
		String userId = principal.toString();

		if (res.hasErrors()) {
			modelMap.addAttribute("comment", comment);
			return "comments/editComment";
		} else {
			comment.setProducto(productService.findProductById(id));
			comment.setFecha(LocalDate.now());
			comment.setUser(userId);
			commentService.save(comment);
			modelMap.addAttribute("message", "Saved successfully");
		}
		return listadoComments(modelMap);
	}

	@GetMapping()
	private String listadoComments(final ModelMap modelMap) {
		String vista = "comments/commentList";
		if (AuthoritiesService.checkAdmin() == true) {

			Iterable<Comment> comments = commentService.findAll();
			modelMap.addAttribute("comments", comments);

		} else {
			Iterable<Comment> comments = commentService.findAllByUserId();
			modelMap.addAttribute("comments", comments);
		}
		return vista;
	}

	@GetMapping(path = "delete/{commentId}")
	public String borrarComment(@PathVariable("commentId") final int commentId, final ModelMap modelMap) {
		Comment comment = commentService.findCommentById(commentId);

		Object principal = SecurityContextHolder.getContext().getAuthentication().getName();
		String userId = principal.toString();

		if (comment.getUser() != userId && AuthoritiesService.checkAdmin() != true) {
			throw new AuthConfigException("No puedes");
		} else if (comment != null && (comment.getUser() == userId || AuthoritiesService.checkAdmin() == true)) {
			commentService.delete(comment);
			modelMap.addAttribute("message", "Comment successfully deleted");
		} else {
			modelMap.addAttribute("message", "Deletion failed");
		}
		return listadoComments(modelMap);
	}

	@GetMapping("/{commentId}")
	public ModelAndView showComment(@PathVariable("commentId") final int commentId) {
		ModelAndView mav = new ModelAndView("comments/commentDetails");
		mav.addObject("comment", this.commentService.findCommentById(commentId));
		return mav;
	}

}
