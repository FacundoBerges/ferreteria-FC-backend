package com.ferreteriafc.api.backend.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.ferreteriafc.api.backend.domain.service.ICategoryService;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Mock
    private ICategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        
    }

    @Test
    void testGetCategories() throws Exception {
//        when(categoryService.findAll()).thenReturn()

        mockMvc
            .perform(get("/categories"))
            .andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON)
            );
    }

}
