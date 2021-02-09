package ssafy.a105.mushroom.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ssafy.a105.mushroom.vo.DataDTO;

public interface MainRepository extends MongoRepository<DataDTO, ObjectId> {

}
