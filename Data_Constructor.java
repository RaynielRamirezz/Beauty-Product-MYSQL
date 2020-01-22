package pos;

import java.math.BigDecimal;

public class Data_Constructor implements interfaceConstructor
{

	private int id;
	private String productName;
	private int amountProduct;
	private String categoryProducts;
	private BigDecimal cost;
	
	
	public Data_Constructor(int id, String productName, 
			int amountProduct, String categoryProducts, BigDecimal cost) {
		//super();
		this.id = id;
		this.productName = productName;
		this.categoryProducts = categoryProducts;
		this.amountProduct = amountProduct;
		this.cost = cost;
	}


	public int getamountProduct() {
		return amountProduct;
	}


	public String getProductName() {
		return productName;
	}


	public String getcategoryProducts() {
		return categoryProducts;
	}

	
	public int getId() {
		return id;
	}


	public BigDecimal getcost() {
		return cost;
	}
	
		
}
