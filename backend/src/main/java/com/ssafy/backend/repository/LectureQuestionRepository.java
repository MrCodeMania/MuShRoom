package com.ssafy.backend.repository;

import com.ssafy.backend.model.LectureQuestion;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LectureQuestionRepository extends MongoRepository<LectureQuestion, ObjectId> {

}
