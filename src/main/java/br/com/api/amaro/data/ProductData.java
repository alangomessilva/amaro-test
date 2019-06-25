package br.com.api.amaro.data;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("ProductData")
public class ProductData implements Serializable {

	
	private static final long serialVersionUID = 1011379942568200671L;

	@ApiModelProperty(value = "ProductID")
	private Long id;

	@ApiModelProperty(value = "ProductName")
	private String name;

	@ApiModelProperty(value = "TAGs")
	private List<String> tags;

	@ApiModelProperty(value = "TAGsvectorInfo")
	private Integer[] tagsVector;

	@ApiModelProperty(value = "SimilarityInfo")
	private Double similarity;
	
	@ApiModelProperty(value = "ERROR's")
	private List<String> errors;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Integer[] getTagsVector() {
		return tagsVector;
	}

	public void setTagsVector(Integer[] tagsVector) {
		this.tagsVector = tagsVector;
	}

	public Double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(Double similarity) {
		this.similarity = similarity;
	}
	
	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
