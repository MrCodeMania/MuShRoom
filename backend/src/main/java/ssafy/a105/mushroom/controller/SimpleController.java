package ssafy.a105.mushroom.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.a105.mushroom.repository.MainRepository;
import ssafy.a105.mushroom.vo.DataDTO;

@CrossOrigin(origins = "*")
@RestController
public class SimpleController {

  @Autowired
  private MainRepository mainRepository;

  @PostMapping("/data")
  public ResponseEntity<DataDTO> insertData() {
    DataDTO data = new DataDTO();
    mainRepository.save(data);
    return new ResponseEntity<>(data, HttpStatus.OK);
  }

  @GetMapping("/data/{id}")
  public ResponseEntity<DataDTO> getOneData(@PathVariable ObjectId id) {
    DataDTO data = mainRepository.findById(id).orElseThrow(() ->
        new IllegalArgumentException("not exist")
    );

    return new ResponseEntity<>(data, HttpStatus.OK);
  }
}
