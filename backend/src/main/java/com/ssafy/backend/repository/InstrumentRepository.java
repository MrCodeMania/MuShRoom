package com.ssafy.backend.repository;

import com.ssafy.backend.model.InstrumentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InstrumentRepository extends MongoRepository<InstrumentEntity, String> {

}
