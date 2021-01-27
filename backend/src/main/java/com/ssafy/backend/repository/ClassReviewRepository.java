package com.ssafy.backend.repository;

import com.ssafy.backend.model.ClassReview;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClassReviewRepository extends MongoRepository<ClassReview, ObjectId> {

}
