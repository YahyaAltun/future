package com.future.future.controller;

import com.future.future.dto.ProductDTO;
import com.future.future.dto.response.FutureResponse;
import com.future.future.dto.response.ResponseMessage;
import com.future.future.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @PostMapping("/{imageId}/add")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<FutureResponse> saveCar(@PathVariable String imageId,
                                                  @Valid @RequestBody ProductDTO productDTO){

        productService.saveProduct(productDTO, imageId);

        FutureResponse response=new FutureResponse();
        response.setMessage(ResponseMessage.PRODUCT_SAVED_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
