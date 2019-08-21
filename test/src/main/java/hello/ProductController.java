package hello;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/products")
	public List<Product> retrieveAllProducts() {
		return (List<Product>) productRepository.findAll();
	}

	@GetMapping("/productsByRule")
	public List<Product> retrieveproductsByRule() {
		return (List<Product>) productRepository.findProductsWithRule();
	}

	@GetMapping("/products/{id}/{quantity}")
	public Product retrieveproductsWithPidAndQuantity(@PathVariable long id, @PathVariable long quantity) {
		Optional<Product> product = productRepository.findById(id);
		
		if (!product.isPresent())
			throw new ProductException("Product id- " + id + " doesnot exists. ");
		
		Product pro = product.get();
		
		if (pro.isBlocked()) {
			throw new ProductException("Product id - " + id + " is blocked and cannot be re-ordered.");
		} else if (pro.getCurrentStock() < pro.getMinStock()) {
			throw new ProductException("Product id - " + id + " stock is low.");
		} else if (pro.getCurrentStock() < quantity) {
			throw new ProductException("Product id - " + id + " stock level is "+ pro.getCurrentStock());
		} else {
			return pro;
		}
	}

	@GetMapping("/products/{id}")
	public Product retrieveProduct(@PathVariable long id) {
		Optional<Product> product = productRepository.findById(id);

		if (!product.isPresent())
			throw new ProductException("Product id- " + id + " doesnot exists. ");

		return product.get();
	}

	@DeleteMapping("/products/{id}")
	public void deleteProduct(@PathVariable long id) {
		productRepository.deleteById(id);
	}

	@PostMapping("/products")
	public ResponseEntity<Object> createProduct(@RequestBody Product product) {
		Optional<Product> product1 = productRepository.findById(product.getProductId());

		if (product1.isPresent())
			throw new ProductException("Product id- " + product.getProductId() + " already exists. ");

		Product savedProduct = productRepository.save(product);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{productId}")
				.buildAndExpand(savedProduct.getProductId()).toUri();

		return ResponseEntity.created(location).build();

	}

	@PutMapping("/products/{id}")
	public ResponseEntity<Object> updateProduct(@RequestBody Product product, @PathVariable long id) {

		Optional<Product> productOptional = productRepository.findById(id);

		if (!productOptional.isPresent())
			return ResponseEntity.notFound().build();

		product.setProductId(id);

		productRepository.save(product);

		return ResponseEntity.noContent().build();
	}
}