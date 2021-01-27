package com.ssafy.backend.repository;

import com.ssafy.backend.model.ClassQuestion;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClassQuestionRepository extends MongoRepository<ClassQuestion, ObjectId> {

}
