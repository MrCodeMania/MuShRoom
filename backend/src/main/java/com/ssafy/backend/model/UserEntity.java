package com.ssafy.backend.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ssafy.backend.other.CustomObjectIdSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@AllArgsConstructor
@Builder
public class UserEntity {

  @JsonSerialize(using = CustomObjectIdSerializer.class)
  @MongoId
  private ObjectId id;

  // some fields
  private String data;
}
