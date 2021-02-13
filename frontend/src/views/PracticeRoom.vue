<template>
  <v-main class="main-color">
    <Header />

    <!-- 부모 row -->
    <v-row no-gutters>
      <!-- 왼쪽 컴포넌트들 -->
      <v-col cols="8" class="flex-grow-0 flex-shrink-0 pa-4">
        <v-row no-gutters style="height: 80vh">
          <v-card elevation="0" height="100%" width="100%" color="#00ff0000">
            <!-- 뮤직 보드 상단 페이징 탭 -->
            <v-tabs
              dark
              hide-slider
              background-color="#00ff0000"
              v-model="page"
            >
              <v-tab
                class="rounded-t pa-0 divider"
                :class="page == i - 1 ? 'nav-color' : 'others'"
                v-for="i in range"
                :key="i"
              >
                <v-card height="48px" elevation="0" color="#00ff0000">
                  <!-- 페이지 삭제 버튼 -->
                  <v-btn
                    class="tab_btn"
                    width="2"
                    height="2"
                    dark
                    icon
                    :disabled="length == 1"
                    @click="removePage()"
                  >
                    <v-icon v-if="length != 1" x-small> mdi-close </v-icon>
                  </v-btn>
                  <!-- 페이지 이름 -->
                  <v-text-field
                    v-model="pageNames[i - 1]"
                    background-color="#00ff0000"
                    flat
                    class="tab_textfield"
                    solo
                  >
                  </v-text-field>
                </v-card>
              </v-tab>
              <!-- 페이지 추가 버튼 -->
              <v-btn
                class="d-line-block ml-1 mt-1"
                icon
                v-if="length < 5"
                @click="addPage()"
              >
                <v-icon> mdi-plus </v-icon>
              </v-btn>
            </v-tabs>
            <v-tabs-items v-model="page" class="rounded-tr nav-color">
              <v-tab-item
                v-for="i in range"
                :key="i"
                :transition="false"
                :reverse-transition="false"
              >
                <!-- 뮤직 보드 -->
                <v-card elevation="0" width="100%" height="100%">
                  <MusicBoard :page="page" />
                </v-card>
              </v-tab-item>
            </v-tabs-items>
          </v-card>
        </v-row>
      </v-col>

      <!-- 오른쪽 컴포넌트들 -->
      <v-col cols="4" class="flex-grow-0 flex-shrink-0 pa-4">
        <!-- 매트로놈 -->
        <v-row no-gutters style="height: 18vh">
          <v-card elevation="0" width="100%" height="100%">
            <MetronomeV2 />
          </v-card>
        </v-row>

        <!-- 파일 목록 -->
        <v-row v-if="status === 'Master'" no-gutters style="height: 68vh">
          <v-card
            elevation="0"
            width="100%"
            height="100%"
          >
            <Record :page="page" />
          </v-card>
        </v-row>
      </v-col>
    </v-row>
  </v-main>
</template>

<script>
import Header from "@/components/common/Header.vue";
import MetronomeV2 from "@/components/MetronomeBody.vue";
import MusicBoard from "@/components/MusicBoard.vue";
import Record from "@/components/record.vue";
import axios from "@/service/axios.service.js";
import Stomp from 'webstomp-client';
import SockJS from 'sockjs-client';
//import * as Tone from "tone";

export default {
  components: {
    Header,
    // Metronome,
    MetronomeV2,
    MusicBoard,
    Record,
  },
  created() {
    // Status를 vuex에 저장
    
    this.init();
    this.load();

  },
  data() {
    return {
      page: 0, //  현재 페이지,
      status,
      pageNames: ["", "", "", "", ""], // 페이지 이름,
    };
  },
  computed: {
    length: function () {
      // 전체 페이지 수
      return this.$store.getters.getPageLength;
    },
    range() {
      let pages = [];
      for (let i = 1; i <= this.length; i++) pages.push(i);
        return pages;
    },
  },
  methods: {
    init() {

      this.code = this.$route.query.shareUrl;
      
      // store에 있는 거 다 지워야함
      this.$store.commit("setData");
    },
    load() {
      axios.get("/data/" + this.code).then((res) => {
      this.$store.commit("pushShareUrl", [
        res.data.id.masterId,
        res.data.id.slaveId,
      ]);

      if (this.code === res.data.id.masterId)
        this.$store.commit("pushStatus", "Master");
      else this.$store.commit("pushStatus", "Slave");

      this.status = this.$store.state.status;

      // 받아온 res에서 뮤직보드, 레코드보드 불러오기 해야함
      // 뮤직 보드 불러오기
      console.log(res.data);
      
      for(let i = 0; i < res.data.musicPageList.length; i++){
        if(i > 0)
          this.$store.commit('addPage', i)
          
        for(let j = 0; j < res.data.musicPageList[i].musicList.length; j++){
          // 일단 한 번 넣고 수정한다
          
          this.$store.commit('addMusic', {page : i, record : { fileName: res.data.musicPageList[i].musicList[j].fileName, downloadURL: res.data.musicPageList[i].musicList[j].url, id:j}});

          // 업데이트
          this.$store.commit('updateMusic', {page : i, music : {
            id:j,
            url: res.data.musicPageList[i].musicList[j].url,
            fileName: res.data.musicPageList[i].musicList[j].fileName,
            timestamp: res.data.musicPageList[i].musicList[j].timestamp == null ? 0 : res.data.musicPageList[i].musicList[j].timestamp,
            distortion: {
              object: null,
              value: res.data.musicPageList[i].musicList[j].distortion
            },
            volume: {
              object: null,
              value: res.data.musicPageList[i].musicList[j].volume
            },
            gain: {
              object: null,
              value: res.data.musicPageList[i].musicList[j].gain
            },
            reverb: {
              object: null,
              value: 0
              // value: res.data.musicPageList[i].musicList[j].reverb
            }
          }});
        }
      }
      console.log("뮤직보드 불러오기 완료!");

      //레코드 보드 불러오기
      for(let i = 0; i < res.data.recordList.length; i++)
        this.$store.commit('updateRecord', {fileName: res.data.recordList[i].fileName, downloadURL: res.data.recordList[i].url, id: i})
      
      console.log("레코드 불러오기 완료!");

      this.connect();

      });
    },
    send(msg) {
      if(msg == "addPage")
        this.musicPageStompClient.send("/socket/music-page/" + this.code + "/receive", JSON.stringify({type:"add", index: this.page, obj: {pageName:"", musicList:[]}}), {});
      else if(msg == "deletePage")
        this.musicPageStompClient.send("/socket/music-page/" + this.code + "/receive", JSON.stringify({type:"delete", index: this.page, obj: {pageName:"", musicList:[]}}), {});
      
    },
    connect() {
      const serverURL = "http://i4a105.p.ssafy.io:8080/";
      
      let musicPageSocket = new SockJS(serverURL);
      this.musicPageStompClient = Stomp.over(musicPageSocket);
      this.musicPageStompClient.connect(
        {},
        frame => {
          // 소켓 연결 성공
          this.connected = true;
          console.log('연습실 소켓 연결 성공', frame);

          this.musicPageStompClient.subscribe("/socket/music-page/" + this.code + "/send", res => {
            const resBody = JSON.parse(res.body);
            
            console.log(resBody);

              if(resBody["type"] == "add")
                this.$store.commit("addPage", this.page + 1);
              else if(resBody["type"] == "delete")
                this.$store.commit("removePage", this.page);
          });
        },
        error => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error);
          this.connected = false;
        }
      );
    },

    //  페이지 왼쪽 이동
    moveLeft() {
      this.page = this.page == 0 ? 0 : this.page - 1;
    },
    //  페이지 오른쪽 이동
    moveRight() {
      this.page = this.page == 4 ? 4 : this.page + 1;
    },
    //  페이지 추가
    addPage() {
      this.moveRight();
      this.send("addPage");
      
    },
    // 페이지 삭제 추가해야함
    removePage() {
      this.moveLeft();
      this.send("deletePage");
    },
  },
};
</script>

<style>
.tab_textfield input::placeholder {
  color: white !important;
}

.tab_textfield input {
  width: 55px;
  height: 5px;
  color: white !important;
}

.tab_textfield .v-input__slot {
  min-height: 30px;
}

.tab_btn {
  margin-top: 10px;
  margin-left: 59px;
}

.divider {
  border-right: 1px solid black;
  /* border-bottom: 1px solid white; */
}

.others {
  background-color: #3c4d5d;
}
</style>
