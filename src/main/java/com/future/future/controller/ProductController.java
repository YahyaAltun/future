package com.future.future.controller;

import com.future.future.domain.Product;
import com.future.future.dto.ProductDTO;
import com.future.future.dto.response.FutureResponse;
import com.future.future.dto.response.ResponseMessage;
import com.future.future.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<FutureResponse> saveProduct(@PathVariable String imageId,
                                                  @Valid @RequestBody ProductDTO productDTO){

        productService.saveProduct(productDTO, imageId);

        FutureResponse response=new FutureResponse();
        response.setMessage(ResponseMessage.PRODUCT_SAVED_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<FutureResponse> deleteProduct(@PathVariable ("id") Long id){
        productService.removeById(id);

        FutureResponse response = new FutureResponse();
        response.setMessage(ResponseMessage.PRODUCT_DELETED_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
        ProductDTO productDTO=productService.findById(id);

        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/pages")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<Page<ProductDTO>> getAllWithPage(@RequestParam("page") int page,
                                                        @RequestParam("size") int size,
                                                        @RequestParam("sort") String prop,
                                                        @RequestParam("direction") Sort.Direction direction){

        Pageable pageable= PageRequest.of(page, size, Sort.by(direction,prop));
        Page<ProductDTO> carPage= productService.findAllWithPage(pageable);

        return ResponseEntity.ok(carPage);
    }

    @PutMapping("/customer/auth")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<FutureResponse> updateProduct(@RequestParam("id") Long id,
                                                @RequestParam("imageId") String imageId,
                                                @Valid @RequestBody ProductDTO productDTO){

        productService.updateProduct(id, imageId, productDTO);
        FutureResponse response = new FutureResponse();
        response.setMessage(ResponseMessage.PRODUCT_UPDATED_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }
}
