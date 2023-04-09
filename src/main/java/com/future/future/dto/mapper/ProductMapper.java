package com.future.future.dto.mapper;

import com.future.future.domain.Product;
import com.future.future.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id",ignore = true)
    @Mapping(target="image", ignore=true)
    Product productDTOToProduct(ProductDTO productDTO);
}
