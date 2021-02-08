package ssafy.a105.mushroom.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ssafy.a105.mushroom.vo.SocketVO;

@Controller
public class SocketController {

    @MessageMapping("/socket/receive/{aid}/{bid}") // /receive를 메시지를 받을 endpoint로 설정합니다.
    @SendTo("/socket/send/{aid}/{bid}") // /send로 메시지를 반환합니다.
    // SocketHandler는 1) /receive에서 메시지를 받고, /send로 메시지를 보내줍니다.
    // 정의한 SocketVO를 1) 인자값, 2) 반환값으로 사용합니다.
    public SocketVO socketHandler(@DestinationVariable int aid, @DestinationVariable int bid, SocketVO socketVO) {
        // 생성자로 반환값을 생성합니다.
        System.out.println(aid + " " + bid);
        return new SocketVO(socketVO.getUserName(),  socketVO.getContent());
    }

    @MessageMapping("/socket/receive2/{aid}/{bid}")
    @SendTo("/socket/send2/{aid}/{bid}")
    public SocketVO socketHandler2(@DestinationVariable int aid, @DestinationVariable int bid, SocketVO socketVO) {
        // 생성자로 반환값을 생성합니다.
        System.out.println(aid + bid);
        return new SocketVO(socketVO.getUserName(),  socketVO.getContent());
    }

}
