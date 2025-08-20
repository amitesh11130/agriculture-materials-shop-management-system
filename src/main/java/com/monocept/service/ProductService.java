package com.monocept.service;

import com.monocept.entity.Product;
import com.monocept.request.ProductDTO;

import java.util.List;

public interface ProductService {

    Product addProduct(ProductDTO productDTO);

    Product getProductById(Long productId);

    List<Product> getAllProducts();

    Product updateProduct(Long productId, ProductDTO productDTO);

    boolean deleteProduct(Long productId);

}
