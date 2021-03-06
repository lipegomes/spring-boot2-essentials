package dev.filipegomes.springboot2.service;

import dev.filipegomes.springboot2.domain.Product;
import dev.filipegomes.springboot2.exception.BadRequestException;
import dev.filipegomes.springboot2.mapper.ProductMapper;
import dev.filipegomes.springboot2.repository.ProductRepository;
import dev.filipegomes.springboot2.requests.ProductPostRequestBody;
import dev.filipegomes.springboot2.requests.ProductPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Page<Product> listAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public List<Product> listAllNonPageable() {
        return productRepository.findAll();
    }

    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    public Product findByIdOrThrowBadRequestException(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Product not Found"));
    }

    @Transactional
    public Product save(ProductPostRequestBody productPostRequestBody) {
        return productRepository.save(ProductMapper.INSTANCE.toProduct(productPostRequestBody));
    }

    public void delete(long id) {
        productRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(ProductPutRequestBody productPutRequestBody) {
        Product savedProduct = findByIdOrThrowBadRequestException(productPutRequestBody.getId());
        Product product = ProductMapper.INSTANCE.toProduct(productPutRequestBody);
        product.setId(savedProduct.getId());
        productRepository.save(product);
    }
}
