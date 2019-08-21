package hello;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {

	@Id
	private Long productId;
	private String productDesc;
	private Long currentStock;
	private Long minStock;
	private boolean isBlocked;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public Long getMinStock() {
		return minStock;
	}

	public void setMinStock(Long minStock) {
		this.minStock = minStock;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public Long getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(Long currentStock) {
		this.currentStock = currentStock;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	
	protected Product() {
	}

	public Product(Long productId, String productDesc, Long minStock, Long currentStock, boolean isBlocked) {
		this.productId = productId;
		this.productDesc = productDesc;
		this.minStock = minStock;
		this.currentStock = currentStock;
		this.isBlocked = isBlocked;
	}

	@Override
	public String toString() {
		return String.format(
				"Product[productId=%d, productDesc='%s', minStock='%d', currentStock='%d', isBlocked='%b']",
				productId, productDesc, minStock, currentStock, isBlocked);
	}

}
