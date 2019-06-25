package br.com.api.amaro.data;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Products")
public class Products implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1011379942568200671L;

	@ApiModelProperty(value = "Products")
	private List<ProductData> products;

	public List<ProductData> getProducts() {
		return products;
	}

	public void setProducts(List<ProductData> products) {
		this.products = products;
	}

}
