package com.ssafy.backend.repository;

import com.ssafy.backend.model.ClassEntity;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ClassRepository extends MongoRepository<ClassEntity, ObjectId> {
  public List<ClassEntity> findByTitleContaining(String title);

  public List<ClassEntity> findTop10ByOrderByLikeDesc();

  public List<ClassEntity> findTop10ByOrderByStudentCountDesc();
}
