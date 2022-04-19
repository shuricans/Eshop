package ru.geekbrains.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.geekbrains.persist.model.Brand;
import ru.geekbrains.persist.repository.BrandRepository;
import ru.geekbrains.persist.repository.CategoryRepository;
import ru.geekbrains.persist.repository.ProductRepository;
import ru.geekbrains.persist.model.Category;
import ru.geekbrains.persist.model.Product;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private SimpMessagingTemplate template;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        brandRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    public void testFindAll() throws Exception {
        Category category = new Category();
        category.setName("Category");
        category = categoryRepository.save(category);

        Brand brand = new Brand();
        brand.setName("Brand");
        brand = brandRepository.save(brand);

        Product product = new Product();
        product.setName("Product");
        product.setDescription("Description");
        product.setPrice(new BigDecimal(1234));
        product.setCategory(category);
        product.setBrand(brand);

        product = productRepository.save(product);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/v1/product/all")
                .param("categoryId", category.getId().toString())
                .param("page", "1")
                .param("size", "5")
                .param("sortField", "id")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", is(product.getName())));
    }

    @Test
    public void testFindById() throws Exception {
        Category category = new Category();
        category.setName("Category");
        category = categoryRepository.save(category);

        Brand brand = new Brand();
        brand.setName("Brand");
        brand = brandRepository.save(brand);

        Product product = new Product();
        product.setName("Product");
        product.setDescription("Description");
        product.setPrice(new BigDecimal(1234));
        product.setCategory(category);
        product.setBrand(brand);

        product = productRepository.save(product);

        final long productId = product.getId();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/v1/product/" + productId)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(product.getName())));
    }
}
