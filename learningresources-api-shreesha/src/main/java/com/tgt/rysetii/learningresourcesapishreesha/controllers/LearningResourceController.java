package com.tgt.rysetii.learningresourcesapishreesha.controllers;

import com.tgt.rysetii.learningresourcesapishreesha.entity.LearningResource;
import com.tgt.rysetii.learningresourcesapishreesha.service.LearningResourceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/learningresource/v1")
public class LearningResourceController {
    private final LearningResourceService learningResourceService;

    public LearningResourceController(LearningResourceService learningResourceService) {
        this.learningResourceService = learningResourceService;
    }

    @GetMapping("/" )
    public List<LearningResource> getAllLearningResource(){
        return learningResourceService.getLearningResources();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveLearningResources(@RequestBody List<LearningResource> learningResources){
        learningResourceService.saveLearningResources(learningResources);
    }

    @DeleteMapping("/learningresource/{learningResourceId}")
    public void deleteLearningResource(@PathVariable int learningResourceId){
        learningResourceService.deleteLearningResourceById(learningResourceId);
    }
}
