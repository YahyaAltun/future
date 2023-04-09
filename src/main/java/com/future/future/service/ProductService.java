package com.future.future.service;

import com.future.future.domain.ImageFile;
import com.future.future.domain.Product;
import com.future.future.dto.ProductDTO;
import com.future.future.dto.mapper.ProductMapper;
import com.future.future.exception.ResourceNotFoundException;
import com.future.future.exception.message.ErrorMessage;
import com.future.future.repository.ImageFileRepository;
import com.future.future.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;
    private ImageFileRepository imageFileRepository;
    private ProductMapper productMapper;

    public void  saveProduct(ProductDTO productDTO, String imageId){
        ImageFile imFile = imageFileRepository.findById(imageId).orElseThrow(()-> new
                ResourceNotFoundException(String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE,imageId)));

        Product product= productMapper.productDTOToProduct(productDTO);


        Set<ImageFile> imFiles=new HashSet<>();
        imFiles.add(imFile);
        product.setImage(imFiles);

        productRepository.save(product);
    }
}
