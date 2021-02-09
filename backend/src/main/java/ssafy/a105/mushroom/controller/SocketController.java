package ssafy.a105.mushroom.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ssafy.a105.mushroom.repository.MainRepository;
import ssafy.a105.mushroom.vo.DataDTO;
import ssafy.a105.mushroom.vo.Music;
import ssafy.a105.mushroom.vo.TestClass;

@Controller
public class SocketController {

  private final MainRepository mainRepository;

  @Autowired
  public SocketController(MainRepository mainRepository) {
    this.mainRepository = mainRepository;
  }

  @MessageMapping("/socket/music/receive/{id}") // /receive를 메시지를 받을 endpoint로 설정합니다.
  @SendTo("/socket/music/send/{id}") // /send로 메시지를 반환합니다.
  // SocketHandler는 1) /receive에서 메시지를 받고, /send로 메시지를 보내줍니다.
  // 정의한 SocketVO를 1) 인자값, 2) 반환값으로 사용합니다.
  public TestClass<Music> socketHandler(@DestinationVariable String id, TestClass<Music> obj) {
    // 생성자로 반환값을 생성합니다.
    DataDTO data = mainRepository.findById(id).orElseThrow(() -> {
      return new IllegalArgumentException("not exist");
    });

    // data를 service에 넘겨서 처리

    System.out.println(obj);

    return obj;
  }

}
