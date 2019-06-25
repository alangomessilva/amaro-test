package br.com.api.amaro.converter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import br.com.api.amaro.data.ProductData;
import br.com.api.amaro.model.ProductModel;
import br.com.api.amaro.model.TagEnumarator;

@Service
public class Converter implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1011379942568200671L;

	public List<ProductModel> productDataListToProductModelList(List<ProductData> productDataList, String... attributes) {

		List<ProductModel> productModelList = new ArrayList<>();

		productDataList.forEach(productData -> {

			productModelList.add(productDataToProductModel(productData, attributes));

		});

		return productModelList;

	}

	public ProductModel productDataToProductModel(ProductData productData, String... attributes) {

		ProductModel productModel = new ProductModel();

		BeanUtils.copyProperties(productData, productModel, attributes);

		productModel.setTagsVector(convertTagListToReferenceList(productData.getId().intValue(), productData.getTags()));
	
		return productModel;

	}

	public ProductData productModelToProductData(ProductModel productModel, String... attributes) {

		ProductData productData = new ProductData();

		BeanUtils.copyProperties(productModel, productData, attributes);

		productData.setTags(convertVectorToStringList(productModel.getId().intValue(), productModel.getTagsVector()));
		
		List<String> tags = new ArrayList<>();
		
		for (int i = 0; i < productModel.getTagsVector().length; i++) {

			if (productModel.getTagsVector()[i] == 1) {

				try {
					tags.add(TagEnumarator.getByPosition(i).name());
				}catch(Exception e) {
					productData.getErrors().add(e.getMessage());
					System.out.println("ERROR productModelToProductData in \n Product id: " + productModel.getId()  + "\n error:" + e.getMessage() + "\n");
				}
			}

		}
		
		productData.setTags(tags);
		return productData;

	}

	public List<ProductData> productListToProductDataList(List<ProductModel> productModelList, String... attributes) {

		List<ProductData> productDataList = new ArrayList<>();

		productModelList.forEach(product -> {

			productDataList.add(productModelToProductData(product, attributes));

		});

		return productDataList;

	}

	public Integer[] convertTagListToReferenceList(int productId, List<String> tagStringList) {

		Integer[] tags = new Integer[20];
		Arrays.fill(tags, new Integer(0));

		tagStringList.forEach(tag -> {
			try {
				tags[TagEnumarator.valueOf(tag).position] = 1;
			}catch(Exception e) {
				System.out.println("ERROR productDataToProductModel in \n Product id: " + productId + "\n error:" + e.getMessage() + "\n");
			}
			
		});
		
		return tags;

	}

	public List<String> convertVectorToStringList(int productId, Integer[] tagVector) {

		List<String> tags = new ArrayList<>();

		for (int i = 0; i < tagVector.length; i++) {

			if (tagVector[i] == 1) {

				try {
					tags.add(TagEnumarator.getByPosition(i).name());
				}catch(Exception e) {
					System.out.println("ERROR productModelToProductData in \n Product id: " + productId  + "\n error:" + e.getMessage() + "\n");
				}
			}

		}
		
		return tags;

	}

}
