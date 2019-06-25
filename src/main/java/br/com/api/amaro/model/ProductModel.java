package br.com.api.amaro.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class ProductModel implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1011379942568200671L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "tags")
	private Integer[] tagsVector;

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

	public Integer[] getTagsVector() {
		return tagsVector;
	}

	public void setTagsVector(Integer[] tagsVector) {
		this.tagsVector = tagsVector;
	}

	

}
