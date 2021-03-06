package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.h2.security.auth.AuthConfigException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(path = "/new")
	public String initCreationForm(Product product, ModelMap modelMap) {
		if (AuthoritiesService.checkAdmin() != true) {
			throw new AuthConfigException("Debe ser administrador");
		}
		String view = "products/editProduct";
		modelMap.addAttribute("product", new Product());
		return view;
	}

	@PostMapping(path = "/save")
	public String processCreationForm(@Valid Product product, BindingResult res, ModelMap modelMap) {
		if (res.hasErrors()) {
			modelMap.addAttribute("product", product);
			return "products/editProduct";
		} else {
			productService.save(product);
			modelMap.addAttribute("message", "Saved successfully");
		}
		return listadoProductos(modelMap);
	}

	@GetMapping()
	private String listadoProductos(ModelMap modelMap) {
		String vista = "products/productList";
		Iterable<Product> products = productService.findAll();
		modelMap.addAttribute("products", products);
		return vista;
	}

	@GetMapping(path = "delete/{productId}")
	public String borrarProducto(@PathVariable("productId") int productId, ModelMap modelMap) {
		Product product = productService.findProductById(productId);
		if (AuthoritiesService.checkAdmin() != true) {
			throw new AuthConfigException("Debe ser administrador");
		} else if (product != null) {
			productService.delete(product);
			modelMap.addAttribute("message", "Product successfully deleted");
		} else {
			modelMap.addAttribute("message", "Deletion failed");
		}
		return listadoProductos(modelMap);
	}

	@GetMapping("/{productId}")
	public ModelAndView showProduct(@PathVariable("productId") int productId) {
		ModelAndView mav = new ModelAndView("products/productDetails");
		mav.addObject("product", this.productService.findProductById(productId));
		return mav;
	}

}