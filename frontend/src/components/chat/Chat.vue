<template>
    <v-container id="chat" 
    :class="openChat ? 'chat' : 'hide'">
        <v-card
        dark
        width="100%" height="100%" class="nav-color">
            <!-- 채팅창 상단 -->
            <v-card-title>
                실시간 채팅
                <v-spacer></v-spacer>
                <v-btn 
                icon
                @click="closeChat()"
                >
                    <v-icon
                    small>
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
                    <Message :msg="msg"/>
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
                        rows="1">
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

export default {
    components: {
        Message
    },
    props: ['openChat'],    // class 바인딩 바꿔줄 toggle 변수
    data() {
        return {
            msgList: [{id: 1, text: '야'}], // 서버에서 불러올 채팅 리스트
            sentence: '',   // 현재 입력 칸에 적힌 문장
        }
    },
    computed: {
        length() {  // 전체 리스트 길이
            return this.msgList.length;
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
            this.$emit('closeChat');
        },
        sendMessage: function() {   // 엔터 또는 전송버튼 누르면 list로 push (-> 서버로 전송하게 변경해야 함)
            if(this.sentence.length == 1){
                this.sentence = '';
                return;
            }
            this.msgList.push({id: 0, text: this.sentence});
            this.sentence = '';
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
    border-radius: 5px;
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