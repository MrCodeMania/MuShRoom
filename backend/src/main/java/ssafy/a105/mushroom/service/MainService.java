package ssafy.a105.mushroom.service;

import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssafy.a105.mushroom.repository.MainRepository;
import ssafy.a105.mushroom.vo.DataDTO;
import ssafy.a105.mushroom.vo.MultiId;

@Service
public class MainService {

  @Autowired
  private final MainRepository mainRepository;

  public MainService(MainRepository mainRepository) {
    this.mainRepository = mainRepository;
  }

  public DataDTO makeData() {
    String masterId = "";
    while (true) {
      masterId = makeRandomString();
      if (!mainRepository.existsByIdMasterIdOrIdSlaveId(masterId, masterId)) {
        break;
      }
    }

    String slaveId = "";
    while (true) {
      slaveId = makeRandomString();
      if (!mainRepository.existsByIdMasterIdOrIdSlaveId(slaveId, slaveId)) {
        break;
      }
    }

    DataDTO data = new DataDTO(new MultiId(masterId, slaveId));
    mainRepository.save(data);

    return data;
  }

  public List<DataDTO> getAllData() {
    return mainRepository.findAll();
  }

  public DataDTO getOneData(String id) {
    return mainRepository.findByIdMasterIdOrIdSlaveId(id, id)
        .orElseThrow(() -> new IllegalArgumentException("doc not exist"));
  }

  public String makeRandomString() {
    int leftLimit = 48; // 0
    int rightLimit = 122; // z
    int targetStringLength = 15;

    return new Random().ints(leftLimit, rightLimit + 1)
        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
        .limit(targetStringLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
  }
}
