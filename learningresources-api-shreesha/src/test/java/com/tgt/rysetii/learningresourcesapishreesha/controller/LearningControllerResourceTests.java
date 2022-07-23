package com.tgt.rysetii.learningresourcesapishreesha.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgt.rysetii.learningresourcesapishreesha.controllers.LearningResourceController;
import com.tgt.rysetii.learningresourcesapishreesha.entity.LearningResource;
import com.tgt.rysetii.learningresourcesapishreesha.entity.LearningResourceStatus;
import com.tgt.rysetii.learningresourcesapishreesha.service.LearningResourceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LearningResourceController.class)
public class LearningControllerResourceTests {
    @MockBean
    private LearningResourceService learningResourceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllTheAvailableLearningResources() throws Exception{
        List<LearningResource> learningResources = new ArrayList<>();
        LearningResource learningResource1 = new LearningResource(1311, "Test Name 1", 100.0, 120.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(5), LocalDate.now().plusYears(2));
        LearningResource learningResource2 = new LearningResource(1312, "Test Name 2", 120.0, 180.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(6), LocalDate.now().plusYears(3));
        learningResources.add(learningResource1);
        learningResources.add(learningResource2);

        String expectedResponse = objectMapper.writeValueAsString(learningResources);

        when(learningResourceService.getLearningResources()).thenReturn(learningResources);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/learningresource/v1/"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(expectedResponse,mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void saveTheGivenLearningResources() throws Exception{
        List<LearningResource> learningResources = new ArrayList<>();
        LearningResource learningResource1 = new LearningResource(1311, "Test Name 1", 100.0, 120.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(5), LocalDate.now().plusYears(2));
        LearningResource learningResource2 = new LearningResource(1312, "Test Name 2", 120.0, 180.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(6), LocalDate.now().plusYears(3));
        learningResources.add(learningResource1);
        learningResources.add(learningResource2);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/learningresource/v1/")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(learningResources)))
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteTheLearningResource() throws Exception {
        int inputLearningResourceId = 1310;
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/learningresource/v1/learningresource/" + inputLearningResourceId))
                .andExpect(status().isOk());
    }
}
