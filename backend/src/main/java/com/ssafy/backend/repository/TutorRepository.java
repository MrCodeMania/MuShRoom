package com.ssafy.backend.repository;

import com.ssafy.backend.model.Tutor;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface TutorRepository extends MongoRepository<Tutor, ObjectId> {

  Optional<Tutor> findById(ObjectId id);
  List<Tutor> findAll();

}