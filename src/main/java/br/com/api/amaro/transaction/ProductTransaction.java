package br.com.api.amaro.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.amaro.model.ProductModel;

@Transactional(readOnly = true)
public interface ProductTransaction extends JpaRepository<ProductModel, Long> {

}
