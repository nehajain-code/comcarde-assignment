package hello;

import static org.junit.Assert.assertSame;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTests {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ProductRepository product;

	@Test
	public void testInsertAndFindById() {
		Product pro = new Product(1L, "a", 4L, 10L, false);
		entityManager.persist(pro);

		Optional<Product> findProduct = product.findById(pro.getProductId());

		assertSame(pro, findProduct.get());
	}
	
	@Test
	public void testInsertAndFindAll() {
		Product pro = new Product(1L, "a", 4L, 10L, false);
		entityManager.persist(pro);
		Product pro1 = new Product(2L, "b", 4L, 10L, false);
		entityManager.persist(pro1);
		Product pro2 = new Product(3L, "c", 4L, 10L, false);
		entityManager.persist(pro2);

		List<Product> findProduct = (List<Product>) product.findAll();

		assertSame(3, findProduct.size());
	}
	
	@Test
	public void testInsertAndFindByRule() {
		Product pro = new Product(1L, "a", 4L, 10L, false);
		entityManager.persist(pro);
		Product pro1 = new Product(2L, "b", 11L, 10L, false);
		entityManager.persist(pro1);
		Product pro2 = new Product(3L, "c", 4L, 10L, true);
		entityManager.persist(pro2);

		List<Product> findProduct = product.findProductsWithRule();

		assertSame(1, findProduct.size());
	}
}