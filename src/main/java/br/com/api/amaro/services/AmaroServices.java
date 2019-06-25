package br.com.api.amaro.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.amaro.model.ProductModel;
import br.com.api.amaro.transaction.ProductTransaction;
import br.com.api.amaro.converter.Converter;
import br.com.api.amaro.data.ProductData;

@Service
public class AmaroServices implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1011379942568200671L;

	@Autowired
	private Converter converter;
	
	@Autowired
	private ProductTransaction productTransaction;
	
	public Double similarityBetweenTAGs(Integer[] tags1, Integer[] tags2) {
		double sum = 0.0;
		for (int i = 0; i < tags1.length; i++) {
			sum = sum + Math.pow((tags1[i] - tags2[i]), 2.0);
		}
		return 1 / (1 + Math.sqrt(sum));
	}

	public List<ProductData> productModelListToProductDataListSimilarity(ProductModel productRef,
			List<ProductModel> productList, String... attributes) {

		List<ProductData> productDataList = new ArrayList<>();
		productList.stream().filter(p -> p.getId().equals(productRef.getId())).forEach(product -> {

				ProductData productdata = converter.productModelToProductData(product, attributes);

				productdata.setSimilarity(similarityBetweenTAGs(productRef.getTagsVector(),
						product.getTagsVector()));

				productDataList.add(productdata);
		});

		return productDataList;

	}

	public List<ProductData> productDataListWithSimilarity(ProductModel product, List<ProductModel> productList) {

		List<ProductData> productDataList = productModelListToProductDataListSimilarity(product, productList, "tags",
				"tagsVector");
		
		return productDataList.stream().sorted(Comparator.comparingDouble(ProductData::getSimilarity)).limit(3).collect(Collectors.toList());

	}
	
	public List<ProductData> getProductSimilarityByID(Long id){
		Optional<ProductModel> productOptional = productTransaction.findById(id);

		if (productOptional.isPresent()) {

			List<ProductModel> products = productTransaction.findAll();

			return productDataListWithSimilarity(productOptional.get(), products);
		}
		
		throw new NullPointerException();
	}

}
