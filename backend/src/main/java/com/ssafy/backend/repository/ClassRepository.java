package com.ssafy.backend.repository;

import com.ssafy.backend.model.ClassEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClassRepository extends MongoRepository<ClassEntity, String> {

}
