package com.tgt.rysetii.learningresourcesapishreesha.service;

import com.tgt.rysetii.learningresourcesapishreesha.entity.LearningResource;
import com.tgt.rysetii.learningresourcesapishreesha.entity.LearningResourceStatus;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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

    public void saveLearningResources(List<LearningResource> learningResources){
        populateLearningResourcesToCSV(learningResources);
    }

    private void populateLearningResourcesToCSV(List<LearningResource> learningResources) {
        final String csvDelimiter = ",";
        try{
            File learningResourcesFile = new File("LearningResources.csv");
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(learningResourcesFile.getName(), true) );
            for (LearningResource learningResource:learningResources) {
                bufferedWriter.newLine();
                StringBuffer singleLine = new StringBuffer();
                singleLine.append(learningResource.getLearningResourceId());
                singleLine.append(csvDelimiter);
                singleLine.append(learningResource.getLearningResourceName());
                singleLine.append(csvDelimiter);
                singleLine.append(learningResource.getCostPrice());
                singleLine.append(csvDelimiter);
                singleLine.append(learningResource.getSellingPrice());
                singleLine.append(csvDelimiter);
                singleLine.append(learningResource.getLearningResourceStatus());
                singleLine.append(csvDelimiter);
                singleLine.append(learningResource.getCreatedDate());
                singleLine.append(csvDelimiter);
                singleLine.append(learningResource.getPublishedDate());
                singleLine.append(csvDelimiter);
                singleLine.append(learningResource.getRetiredDate());
                singleLine.append(csvDelimiter);
                bufferedWriter.write(learningResource.toString());
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public List<Double> getProfitMargin(){
        List<LearningResource> learningResources = getLearningResources();
        List<Double> profitMargins = learningResources.stream()
                .map(lr->((lr.getSellingPrice()-lr.getCostPrice())/lr.getSellingPrice()))
                .collect(toList());
        return profitMargins;
    }

    public List<LearningResource> sortLearningResourcesByProfitMargins(){
        List<LearningResource> learningResources = getLearningResources();
        learningResources.sort((lr1, lr2)->{
            Double profitMargin1 = (lr1.getSellingPrice()-lr1.getCostPrice())/lr1.getSellingPrice();
            Double profitMargin2 = (lr2.getSellingPrice()-lr2.getCostPrice())/lr2.getSellingPrice();
            return profitMargin2.compareTo(profitMargin1);
        });
        return learningResources;
    }
}
