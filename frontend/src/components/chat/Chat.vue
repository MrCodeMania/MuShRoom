<template>
  <v-container
    id="chat"
    :class="openChat ? 'chat' : 'hide'"
    style="z-index: 99"
  >
    <v-card dark width="100%" height="100%" class="main-color-light">
      <!-- 채팅창 상단 -->
      <v-card-title class="pr-2">
        Chat
        <v-spacer></v-spacer>
        <v-btn
          icon
          plain
          style="position: absolute; right: 5px; top: 5px"
          @click="closeChat()"
        >
          <v-icon> mdi-close </v-icon>
        </v-btn>
      </v-card-title>
      <v-divider
        style="background-color: rgba(255, 255, 255, 0.733)"
      ></v-divider>
      <!-- 메세지 나오는 부분 -->
      <v-container id="scroll-target" class="middle-area px-0 mx-auto">
        <div v-for="(msg, idx) in msgList" :key="idx">
          <Message :msg="msg" :idx="idx" :msgList="msgList" :id="id" />
        </div>
      </v-container>
      <v-divider
        style="background-color: rgba(255, 255, 255, 0.733)"
      ></v-divider>
      <!-- 채팅창 하단 -->
      <v-card-text class="bottom-area py-0">
        <v-row>
          <!-- text 입력칸 -->
          <v-col cols="10">
            <v-textarea
              style="font-size: 14px"
              class="chat-area"
              v-model="sentence"
              @keyup.enter="sendMessage()"
              autofocus
              auto-grow
              rows="1"
            >
            </v-textarea>
          </v-col>
          <!-- 전송 버튼 -->
          <v-col class="d-flex align-center px-0">
            <v-btn icon plain @click="sendMessage()">
              <v-icon small> mdi-send </v-icon>
            </v-btn>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import Message from "@/components/chat/Message.vue";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import Config from "@/store/config";

export default {
  components: {
    Message,
  },
  props: [
    "openChat", // class 바인딩 바꿔줄 toggle 변수
    "nickName", // nickName
  ],
  data() {
    return {
      msgList: [], // 서버에서 불러올 채팅 리스트
      sentence: "", // 현재 입력 칸에 적힌 문장
      roomCode: this.$route.query.shareUrl, // 방 id
      id: "", // 사용자 아이디 (일회용 - 창 접속 시간)
    };
  },
  created() {
    // 소켓 연결
    this.connect();
  },
  computed: {
    length() {
      // 전체 리스트 길이
      return this.msgList.length;
    },
  },
  watch: {
    length: function () {
      var obj = document.getElementById("scroll-target");
      obj.scrollTop = obj.scrollHeight;
    },
  },
  methods: {
    closeChat() {
      // PracticeRoom에서 openChat = false;
      this.$emit("toggleChat");
    },
    sendMessage: function () {
      // 엔터 또는 전송버튼 누르면 전송
      if (this.sentence.length == 1) {
        // 빈칸일 때 보내지 않음
        this.sentence = "";
        return;
      }
      this.stompClient.send(
        "/socket/chat/" + this.roomCode + "/receive",
        JSON.stringify({
          id: this.id + " " + this.nickName,
          message: this.sentence,
        })
      );
      this.sentence = "";
    },
    receiveMessage(res) {
      // 받은 메세지
      const resBody = JSON.parse(res.body);
      const head = resBody["id"].split(" "); // id과 닉네임 분리
      const now = new Date(Date.now()); // 메세지 받은 시간
      const time = now.getHours() + ":" + now.getMinutes();
      this.msgList.push({
        id: head[0],
        nickName: head[1],
        time: time,
        text: resBody["message"],
      });

      //  창이 닫혀있는데 새 메세지가 오면 PracticeRoom에서 newChat++;
      if (!this.openChat) this.$emit("newChat");
    },
    connect() {
      // 소켓 연결
      this.id = Date.now(); // 처음 연결된 시점 : 아이디

      // 서버 정보 등록
      const serverURL = Config.ServerURL;
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      this.stompClient.debug = () => {};

      // 서버 연결 시도
      this.stompClient.connect(
        {},
        () => {
          // 소켓 연결 성공
          this.connected = true;
          // console.log("채팅 소켓 연결 성공", frame);
          // recieve 콜백 등록
          this.stompClient.subscribe(
            "/socket/chat/" + this.roomCode + "/send",
            this.receiveMessage
          );
        },
        (error) => {
          // 소켓 연결 실패
          console.log("채팅 소켓 연결 실패", error);
          this.connected = false;
        }
      );
    },
  },
};
</script>

<style>
.hide {
  display: none;
}

.chat {
  padding: 0;
  display: flex;
  position: fixed;
  right: 16px;
  width: 300px;
  height: 500px;
  bottom: 20px;
  border: 1px solid white;
  border-radius: 3px;
}

.middle-area {
  height: 65%;
  width: 90%;
  overflow-y: auto;
}

.chat-area {
  max-height: 100px;
  overflow-y: hidden;
}

.bottom-area {
  height: 100px;
}
</style>