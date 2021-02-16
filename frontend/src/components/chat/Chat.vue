<template>
    <v-container id="chat"
    :class="openChat ? 'chat' : 'hide'"
    style="z-index: 99">
        <v-card
        dark
        width="100%" height="100%" class="main-color">
            <!-- 채팅창 상단 -->
            <v-card-title>
                실시간 채팅
                <v-spacer></v-spacer>
                <v-btn 
                icon
                style="position: absolute; right: 5px; top: 5px;"
                @click="closeChat()"
                >
                    <v-icon>
                        mdi-close
                    </v-icon>
                </v-btn>
            </v-card-title>
            <v-divider></v-divider>
            <!-- 메세지 나오는 부분 -->
            <v-container
            id="scroll-target"
            class="middle-area px-0 mx-auto">
                <div v-for="(msg, idx) in msgList"
                :key="idx">
                    <Message :msg="msg" :id="id"/>
                </div>
            </v-container>
            <v-divider></v-divider>
            <!-- 채팅창 하단 -->
            <v-card-text
            class="bottom-area py-0">
                <v-row>
                    <!-- text 입력칸 -->
                    <v-col cols="10">
                        <v-textarea
                        class="chat-area"
                        v-model="sentence"
                        auto-grow
                        autofocus
                        @keyup.enter="sendMessage()"
                        rows="1"
                        :prefix="prefix">
                        </v-textarea>
                    </v-col>
                    <!-- 전송 버튼 -->
                    <v-col
                    class="d-flex align-center px-0"
                    >
                        <v-btn
                        icon
                        @click="sendMessage()"
                        >
                            <v-icon
                            small>
                                mdi-send
                            </v-icon>
                        </v-btn>
                    </v-col>
                </v-row>
            </v-card-text>
        </v-card>
    </v-container>
</template>

<script>
import Message from '@/components/chat/Message.vue';
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import Config from '@/store/config'

export default {
    components: {
        Message
    },
    props: [
        'openChat', // class 바인딩 바꿔줄 toggle 변수
        'nickName'  // nickName
    ],
    data() {
        return {
            msgList: [], // 서버에서 불러올 채팅 리스트
            sentence: '',   // 현재 입력 칸에 적힌 문장
            roomCode: this.$route.query.shareUrl,
            id: '',
        }
    },
    created() {
        // 소켓 연결
        this.connect();
    },
    computed: {
        length() {  // 전체 리스트 길이
            return this.msgList.length;
        },
        prefix() {  // 말머리
            return '[' + this.nickName + '] ';
        }
    },
    watch: {
        length: function() {
            var obj = document.getElementById("scroll-target");
            obj.scrollTop = obj.scrollHeight;
        }
    },
    methods: {
        closeChat(){    // PracticeRoom에서 openChat = false;
            this.$emit('toggleChat');
        },
        sendMessage: function() {   // 엔터 또는 전송버튼 누르면 서버로 전송
            if(this.sentence.length == 1){  // 빈칸일 때 보내지 않음
                this.sentence = '';
                return;
            }
            this.stompClient.send("/socket/chat/"+this.roomCode+"/receive", JSON.stringify({id: this.id+" "+this.nickName, message: this.sentence}));
            this.sentence = '';
        },
        receiveMessage(res) {
            const resBody = JSON.parse(res.body);
            // console.log("Receive message: ", resBody);
            const head = resBody["id"].split(" ");  // id과 닉네임 분리
            const now = new Date(Date.now());
            const time = now.getHours()+":"+now.getMinutes();
            this.msgList.push({id: head[0], nickName: head[1], time: time, text: resBody["message"]});
            if(!this.openChat)
                this.$emit('newChat');
        },
        connect() {
            this.id = Date.now();
            console.log(this.id);

            // 서버 정보 등록
            const serverURL = Config.ServerURL;
            let socket = new SockJS(serverURL);
            this.stompClient = Stomp.over(socket);
            // 서버 연결 시도
            console.log(`채팅 소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
            this.stompClient.connect(
                {},
                (frame) => {
                    // 소켓 연결 성공
                    this.connected = true;
                    console.log("채팅 소켓 연결 성공", frame);
                    // recieve 콜백 등록
                    this.stompClient.subscribe("/socket/chat/"+this.roomCode+"/send", this.receiveMessage);
                },
                (error) => {
                // 소켓 연결 실패
                console.log("채팅 소켓 연결 실패", error);
                this.connected = false;
                }
            );
        },
    }
}
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

.chat-area{
    max-height: 100px;
    overflow-y: hidden;
}

.bottom-area {
    height: 100px;
}
</style>