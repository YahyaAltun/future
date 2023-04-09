package com.future.future.repository;

import com.future.future.domain.Product;
import com.future.future.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select new com.future.future.dto.ProductDTO(product) from Product product")
    Page<ProductDTO> findAllProductWithPage(Pageable pageable);
}
