package com.ssafy.backend.repository;

import com.ssafy.backend.model.UserDto;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserDto, ObjectId> {

}
