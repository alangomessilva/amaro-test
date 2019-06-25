package br.com.api.amaro.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;

import br.com.api.amaro.converter.Converter;
import br.com.api.amaro.data.ProductData;
import br.com.api.amaro.data.Products;
import br.com.api.amaro.model.ProductModel;
import br.com.api.amaro.services.AmaroServices;
import br.com.api.amaro.transaction.ProductTransaction;

@RestController
@RequestMapping("/api/amaro")
@CrossOrigin(origins = "*")
public class ProductController {

	@Autowired
	private AmaroServices amaroServices;
	
	@Autowired
	private Converter converter;

	@Autowired
	private ProductTransaction productTransaction;

	@PostMapping("/add/all")
	public  ResponseEntity<List<ProductData>>addAllProducts(@RequestBody String jsonProducts) {
		
		Gson g = new Gson(); 
		Products products = g.fromJson(jsonProducts, Products.class);
				
		List<ProductModel> productList = converter.productDataListToProductModelList(products.getProducts(), "tags",
				"tagsVector");

		productTransaction.saveAll(productList);

		try { 
			return new ResponseEntity<>(converter.productListToProductDataList(productList, "similarity"), HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/add")
	public  ResponseEntity<ProductData> addProduct(@RequestBody ProductData productData) {

		try { 
			ProductModel productModel = converter.productDataToProductModel(productData, "tags", "tagsVector");
	
			productTransaction.save(productModel);
	
			return new ResponseEntity<>(converter.productModelToProductData(productModel, "similarity"), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/similarity")
	public ResponseEntity<List<ProductData>> getProductSimilarity(@RequestParam(name = "id") Long id) {

		try { 
			return new ResponseEntity<>(amaroServices.getProductSimilarityByID(id), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
