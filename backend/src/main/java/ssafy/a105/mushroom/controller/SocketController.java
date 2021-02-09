package ssafy.a105.mushroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ssafy.a105.mushroom.service.MainService;
import ssafy.a105.mushroom.vo.Music;
import ssafy.a105.mushroom.vo.DataClass;
import ssafy.a105.mushroom.vo.Record;

@Controller
public class SocketController {

  private final MainService mainService;

  @Autowired
  public SocketController(MainService mainService) {
    this.mainService = mainService;
  }

  @MessageMapping("/socket/music/{id}/receive")
  @SendTo("/socket/music/{id}/send")
  public DataClass<Music> musicSocketHandler(@DestinationVariable String id, DataClass<Music> obj) {
    String type = obj.getType();

    if (type.equals("add")) {
      mainService.insertMusic(id, obj.getIndex(), obj.getObj());
    } else if (type.equals("delete")) {
      mainService.deleteMusic(id, obj.getIndex());
    } else if (type.equals("update")) {
      mainService.updateMusic(id, obj.getIndex(), obj.getObj());
    }

    return obj;
  }

  @MessageMapping("/socket/record/{id}/receive")
  @SendTo("/socket/record/{id}/send")
  public DataClass<Record> recordSocketHandler(@DestinationVariable String id,
      DataClass<Record> obj) {
    String type = obj.getType();

    if (type.equals("add")) {
      mainService.insertRecord(id, obj.getIndex(), obj.getObj());
    } else if (type.equals("delete")) {
      mainService.deleteRecord(id, obj.getIndex());
    } else if (type.equals("update")) {
      mainService.updateRecord(id, obj.getIndex(), obj.getObj());
    }

    return obj;
  }

}
