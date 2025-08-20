package com.monocept.service.impl;

import com.monocept.entity.Product;
import com.monocept.exception.ResourceNotFoundException;
import com.monocept.repository.ProductRepository;
import com.monocept.request.ProductDTO;
import com.monocept.service.ProductService;
import com.monocept.util.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class productServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product addProduct(ProductDTO productDTO) {
        return productRepository.save(Converter.convertProductDtoToEntity(productDTO));
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Product not found with ID: " + productId));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(Long productId, ProductDTO productDTO) {
        Product product = getProductById(productId);
        product.setName(productDTO.name());
        product.setCategory(productDTO.category());
        product.setPrice(productDTO.price());
        product.setSupplierContact(productDTO.supplierContact());
        product.setSupplierName(productDTO.supplierName());
        product.setQuantityInStock(productDTO.quantityInStock());
        return productRepository.save(product);
}

@Override
public boolean deleteProduct(Long productId) {
    productRepository.deleteById(productId);
    return productRepository.existsById(productId);
}
}
