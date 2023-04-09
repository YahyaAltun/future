package com.future.future.dto;

import com.future.future.domain.ImageFile;
import com.future.future.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @Size(max = 30)
    @NotNull(message = "Please provide your product name")
    private String name;

    @NotNull(message = "Please provide product price")
    private Integer price;

    @Size(max = 300, message = "Your description cannot exceed ${value} characters")
    @NotNull(message = "Please provide your address")
    private String notes;

    private Set<String> image;

    public ProductDTO(Product product) {
        this.name=product.getName();
        this.price=product.getPrice();
        this.notes=product.getNotes();
        this.image=getImageId(product.getImage());

    }

    public Set<String> getImageId(Set<ImageFile> images){
        Set<String> imgStrSet=new HashSet<>();
        imgStrSet=images.stream().map(image->image.getId().toString()).collect(Collectors.toSet());
        return imgStrSet;
    }
}
