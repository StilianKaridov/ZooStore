package com.tinqin.zoostore.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqin.zoostore.api.request.tag.TagCreateRequest;
import com.tinqin.zoostore.api.request.tag.TagUpdateRequest;
import com.tinqin.zoostore.data.entity.Tag;
import com.tinqin.zoostore.data.repository.TagRepository;
import com.tinqin.zoostore.exception.tag.NoSuchTagException;
import com.tinqin.zoostore.exception.tag.OccupiedTagTitleException;
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
        firstTestTag = new Tag();
        firstTestTag.setTitle("Test1");
        firstTestTag.setIsArchived(Boolean.FALSE);

        firstTestTag = tagRepository.save(firstTestTag);

        secondTestTag = new Tag();
        secondTestTag.setTitle("Test2");
        secondTestTag.setIsArchived(Boolean.FALSE);

        secondTestTag = tagRepository.save(secondTestTag);
    }

    @AfterEach
    void tearDown() {
        tagRepository.deleteAll();
    }

    @Test
    void test_CreateTag_Successfully() throws Exception {
        TagCreateRequest tag = new TagCreateRequest();
        tag.setTitle("TestTitle");

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
        TagCreateRequest occupied = new TagCreateRequest();
        occupied.setTitle("Test1");

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
        TagCreateRequest empty = new TagCreateRequest();
        empty.setTitle("   ");

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
        TagUpdateRequest tag = new TagUpdateRequest();
        tag.setOldTitle("Test1");
        tag.setNewTitle("Test0");

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
        TagUpdateRequest tag = new TagUpdateRequest();
        tag.setOldTitle("NoSuchTitle");
        tag.setNewTitle("Test1");

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
        TagUpdateRequest tag = new TagUpdateRequest();
        tag.setOldTitle("Test1");
        tag.setNewTitle("Test2");

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