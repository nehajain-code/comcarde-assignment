package hello;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

	@Query("SELECT p FROM Product p WHERE p.currentStock > p.minStock and isBlocked = false")
    List<Product> findProductsWithRule();
}
