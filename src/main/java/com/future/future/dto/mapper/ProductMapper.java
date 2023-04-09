package com.future.future.dto.mapper;

import com.future.future.domain.ImageFile;
import com.future.future.domain.Product;
import com.future.future.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id",ignore = true)
    @Mapping(target="image", ignore=true)
    Product productDTOToProduct(ProductDTO productDTO);

    @Mapping(source="image", target="image", qualifiedByName="getImageAsString")
    ProductDTO productToProductDTO(Product product);

    @Named("getImageAsString")
    public static Set<String> getImageId(Set<ImageFile> images){
        Set<String> imgs=new HashSet<>();
        imgs=images.stream().map(image->image.getId().toString()).collect(Collectors.toSet());

        return imgs;
    }
}
