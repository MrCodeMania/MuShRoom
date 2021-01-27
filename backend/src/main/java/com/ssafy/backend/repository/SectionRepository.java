package com.ssafy.backend.repository;

import com.ssafy.backend.model.Section;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SectionRepository extends MongoRepository<Section, ObjectId> {

}
