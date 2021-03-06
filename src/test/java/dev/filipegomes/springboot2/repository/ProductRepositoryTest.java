package dev.filipegomes.springboot2.repository;

import dev.filipegomes.springboot2.domain.Product;
import dev.filipegomes.springboot2.util.ProductCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for product repository")
@Log4j2
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Saves creates anime when Successful")
    void save_PersistProduct_WhenSuccessful() {
        Product productToBeSaved = ProductCreator.createProductToBeSaved();

        Product productSaved = this.productRepository.save(productToBeSaved);

        Assertions.assertThat(productSaved).isNotNull();

        Assertions.assertThat(productSaved.getName()).isEqualTo(productToBeSaved.getName());
    }

    @Test
    @DisplayName("Saves updates product when Successful")
    void save_UpdatesProduct_WhenSuccessful() {
        Product productToBeSaved = ProductCreator.createProductToBeSaved();

        Product productSaved = this.productRepository.save(productToBeSaved);

        productSaved.setName("Gerador portátil Branco B4T-3500 3500W monofásico com tecnologia AVR 110V/220V");

        Product productUpdated = this.productRepository.save(productSaved);

        Assertions.assertThat(productUpdated).isNotNull();

        Assertions.assertThat(productUpdated.getId()).isNotNull();

        Assertions.assertThat(productUpdated.getName()).isEqualTo(productSaved.getName());
    }

    @Test
    @DisplayName("Delete removes product when Successful")
    void delete_RemovesProduct_WhenSuccessful() {
        Product productToBeSaved = ProductCreator.createProductToBeSaved();

        Product productSaved = this.productRepository.save(productToBeSaved);

        this.productRepository.delete(productSaved);

        Optional<Product> productOptional = this.productRepository.findById(productSaved.getId());

        Assertions.assertThat(productOptional).isEmpty();
    }

    @Test
    @DisplayName("Find by Name returns list of removes product when Successful")
    void findByName_ReturnsListOfProduct_WhenSuccessful() {
        Product productToBeSaved = ProductCreator.createProductToBeSaved();

        Product productSaved = this.productRepository.save(productToBeSaved);

        String name = productSaved.getName();

        List<Product> products = this.productRepository.findByName(name);

        Assertions.assertThat(products).isNotEmpty();

        Assertions.assertThat(products)
                .contains(productSaved)
                .contains(productSaved);
    }

    @Test
    @DisplayName("Find by Name returns empty list when no product is not found")
    void findByName_ReturnsEmptyList_WhenProductIsNotFound() {
        List<Product> products = this.productRepository.findByName("");

        Assertions.assertThat(products).isEmpty();
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is empty")
    void save_ThrowsConstraintViolationException_WhenNameIsEmpty() {
        Product product = new Product();
         // Assertions.assertThatThrownBy(() -> this.productRepository.save(product))
         // .isInstanceOf(ConstraintViolationException.class);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.productRepository.save(product))
                .withMessageContaining("The product name cannot be empty");
    }
}
