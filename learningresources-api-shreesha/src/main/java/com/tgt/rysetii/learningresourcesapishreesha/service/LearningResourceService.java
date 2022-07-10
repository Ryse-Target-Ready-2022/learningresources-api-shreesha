package com.tgt.rysetii.learningresourcesapishreesha.service;

import com.tgt.rysetii.learningresourcesapishreesha.entity.LearningResource;
import com.tgt.rysetii.learningresourcesapishreesha.entity.LearningResourceStatus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LearningResourceService {
    private List<LearningResource> getLearningResources(){
        File learningResourceFile = new File("LearningResource.csv");
        List<LearningResource> learningResources = loadLearningResourcesFromCSV(learningResourceFile);
        return learningResources;
    }

    private List<LearningResource> loadLearningResourcesFromCSV(File learningResourceFile) {
        List<LearningResource> learningResources = new ArrayList<>();
        try{
            FileReader fileReader = new FileReader(learningResourceFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();
            while (line != null){
                String[] attributes = line.split(",");
                LearningResource learningResource = createLearningResource(attributes);
                learningResources.add(learningResource);
                line = bufferedReader.readLine();
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return learningResources;
    }

    private LearningResource createLearningResource(String[] attributes) {
        Integer learningResourceId = Integer.parseInt(attributes[0].replaceAll("\\D", ""));
        String learningResourceName = attributes[1];
        Double costPrice = Double.parseDouble(attributes[2]);
        Double sellingPrice = Double.parseDouble(attributes[3]);
        LearningResourceStatus learningResourceStatus = LearningResourceStatus.valueOf(attributes[4]);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate createdDate = LocalDate.parse(attributes[5], dateTimeFormatter);
        LocalDate publishedDate = LocalDate.parse(attributes[6], dateTimeFormatter);
        LocalDate retiredDate = LocalDate.parse(attributes[7], dateTimeFormatter);

        LearningResource learningResource = new LearningResource(
                learningResourceId, learningResourceName, costPrice, sellingPrice, learningResourceStatus,createdDate,publishedDate,retiredDate
        );

        return learningResource;
    }
}
