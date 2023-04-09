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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void removeById(Long id){
        Product product = productRepository.findById(id).orElseThrow(()-> new
                ResourceNotFoundException(String.format(ErrorMessage.PRODUCT_NOT_FOUND_MESSAGE,id)));

        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Product product=productRepository.findById(id).orElseThrow(()->new
                ResourceNotFoundException(String.format(ErrorMessage.PRODUCT_NOT_FOUND_MESSAGE,id)));

        return productMapper.productToProductDTO(product);
    }

    public void updateProduct(Long id, String imageId, ProductDTO productDTO){
        Product foundProduct = productRepository.findById(id).orElseThrow(()-> new
                ResourceNotFoundException(String.format(ErrorMessage.PRODUCT_NOT_FOUND_MESSAGE,id)));

        ImageFile imFile = imageFileRepository.findById(imageId).orElseThrow(()->new
                ResourceNotFoundException(String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE,imageId)));

        Set<ImageFile> imgs = foundProduct.getImage();
        imgs.add(imFile);

        Product product = productMapper.productDTOToProduct(productDTO);

        product.setId(foundProduct.getId());
        product.setImage(imgs);
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllWithPage(Pageable pageable){
        return productRepository.findAllProductWithPage(pageable);
    }


}
