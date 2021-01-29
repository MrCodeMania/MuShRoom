package com.ssafy.backend.repository;

import com.ssafy.backend.model.Lecture;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LectureRepository extends MongoRepository<Lecture, ObjectId> {

}
