package ru.geekbrains.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.geekbrains.persist.repository.CategoryRepository;
import ru.geekbrains.service.dto.CategoryMapper;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    private CategoryService underTest;

    @Mock private CategoryRepository categoryRepository;
    @Mock private CategoryMapper categoryMapper;

    @BeforeEach
    public void setUp() {
        underTest = new CategoryServiceImpl(categoryRepository, categoryMapper);
    }

    @Test
    void testFindAll() {
        // when
        underTest.findAll();
        // then
        verify(categoryRepository).findAll();
    }
}