package com.tinqin.zoostore.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqin.zoostore.api.exception.tag.NoSuchTagException;
import com.tinqin.zoostore.api.exception.tag.OccupiedTagTitleException;
import com.tinqin.zoostore.api.operations.tag.create.TagCreateRequest;
import com.tinqin.zoostore.api.operations.tag.update.TagUpdateRequest;
import com.tinqin.zoostore.persistence.entity.Tag;
import com.tinqin.zoostore.persistence.repository.TagRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Tag firstTestTag;

    private Tag secondTestTag;

    @BeforeEach
    void setUp() {
        firstTestTag = Tag
                .builder()
                .title("Test1")
                .isArchived(Boolean.FALSE).build();


        firstTestTag = tagRepository.save(firstTestTag);

        secondTestTag = Tag
                .builder()
                .title("Test2")
                .isArchived(Boolean.FALSE).build();

        secondTestTag = tagRepository.save(secondTestTag);
    }

    @AfterEach
    void tearDown() {
        tagRepository.deleteAll();
    }

    @Test
    void test_CreateTag_Successfully() throws Exception {
        TagCreateRequest tag = TagCreateRequest
                .builder()
                .title("TestTitle")
                .build();

        mockMvc.perform(
                        post("/createTag").
                                contentType(MediaType.APPLICATION_JSON).
                                content(objectMapper.writeValueAsString(tag)).
                                accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$['title']").value(tag.getTitle()));
    }

    @Test
    void test_CreateTag_WithExistingTagTitle_ShouldThrow_OccupiedTagTitleEx() throws Exception {
        TagCreateRequest occupied = TagCreateRequest
                .builder()
                .title("Test1")
                .build();

        mockMvc.perform(
                        post("/createTag")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(occupied))
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OccupiedTagTitleException));
    }

    @Test
    void test_CreateTag_WithEmptyTitle_ShouldThrow_MethodArgumentNotValidEx() throws Exception {
        TagCreateRequest empty = TagCreateRequest
                .builder()
                .title("    ")
                .build();

        mockMvc.perform(
                        post("/createTag")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(empty))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void test_UpdateTagTitle_Successfully() throws Exception {
        TagUpdateRequest tag = TagUpdateRequest
                .builder()
                .oldTitle("Test1")
                .newTitle("Test0")
                .build();

        mockMvc.perform(
                        patch("/updateTag")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(tag))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['title']").value("Test0"));
    }

    @Test
    void test_UpdateTagTitle_ShouldThrow_NoSuchTagEx() throws Exception {
        TagUpdateRequest tag = TagUpdateRequest
                .builder()
                .oldTitle("NoSuchTitle")
                .newTitle("Test1")
                .build();

        mockMvc.perform(
                        patch("/updateTag")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(tag))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchTagException));
    }

    @Test
    void test_UpdateTagTitle_ShouldThrow_OccupiedTagTitleEx() throws Exception {
        TagUpdateRequest tag = TagUpdateRequest
                .builder()
                .oldTitle("Test1")
                .newTitle("Test2")
                .build();
        
        mockMvc.perform(
                        patch("/updateTag")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(tag))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OccupiedTagTitleException));
    }
}