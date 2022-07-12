package com.tgt.rysetii.learningresourcesapishreesha.repository;

import com.tgt.rysetii.learningresourcesapishreesha.entity.LearningResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningResourceRepository extends JpaRepository<LearningResource, Integer> {
}
