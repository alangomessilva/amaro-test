package br.com.api.amaro;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;

import br.com.api.amaro.converter.Converter;
import br.com.api.amaro.data.ProductData;
import br.com.api.amaro.data.Products;
import br.com.api.amaro.model.ProductModel;
import br.com.api.amaro.services.AmaroServices;
import br.com.api.amaro.transaction.ProductTransaction;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServicesAmaroTests {

	@Autowired
	private Converter converter;
	
	@Autowired
	private AmaroServices amaroServices;
	
	@Autowired
	private ProductTransaction productTransaction;

	private static String productName = "VESTIDO TESTE API";
	private static Long productId = (long) 1234;
	
	@Test
	public void saveProducts() {
		List<ProductModel> products = mockModel();
		
		productTransaction.saveAll(products);
		
		List<ProductData> productDataList = amaroServices.getProductSimilarityByID(productId);
	
	    Assert.assertTrue(productDataList.size() > 0);
	    productDataList.forEach(product -> {
	    	Assert.assertTrue(product.getSimilarity() != null); 	
	    });
	   
	    productTransaction.deleteAll(products);
	}
	
	@Test
	public void productListToProductDataList() {
			
		List<ProductData> productList = converter.productListToProductDataList(mockModel(), "tags",
				"tagsVector");
		
		Assert.assertEquals(1, productList.size());
		Assert.assertEquals(productName, productList.get(0).getName());
		Assert.assertEquals(productId, productList.get(0).getId());
	    Assert.assertTrue(productList.get(0).getTags().size() > 1);
	}	
	
	@Test
	public void productDataListToProductModelList() {
		Products product = mockData();
		List<ProductModel> productList = converter.productDataListToProductModelList(product.getProducts(), "tags",
				"tagsVector");
	
		Assert.assertEquals(1, productList.size());
		Assert.assertEquals(productName, productList.get(0).getName());
		Assert.assertEquals(productId, productList.get(0).getId());
		Assert.assertTrue(productList.get(0).getTagsVector().length > 1);
	}
	
	public Products mockData() {
		String jsonProducts = "{ \"products\": [ { \"id\": 1234, \"name\": \"VESTIDO TESTE API\", \"tags\": [\"balada\", \"neutro\", \"delicado\", \"festa\"] } ] }";		
			Gson g = new Gson(); 
			Products products = g.fromJson(jsonProducts, Products.class);		
			return products;
	}
	
	public List<ProductModel>  mockModel() {
		Products product = mockData();
		return converter.productDataListToProductModelList(product.getProducts(), "tags",
				"tagsVector");
	}
	
}
