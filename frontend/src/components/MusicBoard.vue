<template>
  <v-card
    class="musicBoard main-color-light"
    elevation="0"
    width="100%"
    height="100%"
  >
    <v-card
      id="musicListId"
      class="musicList overflow-y-auto main-color-light"
      style="height: inherit !important; border-radius: 0px"
      v-scroll.self="onScroll"
    >
      <div v-if="music.length==0" 
      style="height: 100%;"
      class="d-flex justify-center align-center">
        <div>
            <v-img
            width="300px !important"
            style="object-fit: cover"
            src="@/assets/grey.png">
            </v-img>
            <div class="ml-10" style="color: #4a4a4a;">
              어떤 음악을 만들지 기대돼요
            </div>
        </div>
      </div>
      <Player
        class="mx-8 mt-4 smallcomponent-color"
        v-for="(item, idx) in music"
        :key="item.id"
        :n="idx"
        :music="item"
        @deleteMusic="deleteMusic"
        @updateMusicOption="updateMusicOption"
        ref="player"
      />
    </v-card>
    <v-divider style="background-color: rgba(255, 255, 255, 0.733)"></v-divider>
    <v-card class="buttonBar d-flex main-color-light">
      <v-spacer></v-spacer>
      <v-card
        style="border-radius: 0px"
        class="d-flex align-center justify-end main-color-light"
        elevation="0"
      >
        <v-btn
          class="musicboard_btn pt-1"
          icon
          dark
          large
          @click="downloadButton"
        >
          <v-icon dark size="26px">mdi-download</v-icon>
        </v-btn>
        <v-btn
          class="musicboard_btn mx-n2"
          icon
          dark
          large
          @click="musicPlayButton"
        >
          <div v-if="!play">
            <v-icon dark size="30px">mdi-play</v-icon>
          </div>
          <div v-else>
            <v-icon dark size="30px">mdi-pause</v-icon>
          </div>
        </v-btn>
        <v-btn
          class="musicboard_btn mr-5"
          icon
          dark
          large
          @click="musicStopButton"
        >
          <v-icon dark size="30px">mdi-stop</v-icon>
        </v-btn>
      </v-card>
    </v-card>
  </v-card>
</template>

<script>
import Player from "./practiceroom/Player";
import * as Tone from "tone";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import Config from "@/store/config";
import options from "@/store/option";

export default {
  components: {
    Player,
  },
  data() {
    return {
      play: false,
      scrollInvoked: 0,
      code: "",
      status,
    };
  },
  created() {
    this.status = this.$store.state.status;

    this.code = document.location.href.split("=")[1];

    this.connect();
  },
  computed: {
    getURL() {
      return this.$store.getters.getURL;
    },
    music: function () {
      return this.$store.getters.getBoard;
    },
  },
  methods: {
    send(type, msg) {
      if (type == "music")
        this.musicStompClient.send(
          "/socket/music/" + this.code + "/0/receive",
          JSON.stringify(msg),
          {}
        );
    },
    connect() {
      const serverURL = Config.ServerURL;

      let musicSocket = new SockJS(serverURL);
      this.musicStompClient = Stomp.over(musicSocket);
      this.musicStompClient.connect(
        {},
        (frame) => {
          // 소켓 연결 성공
          this.connected = true;
          console.log("뮤직보드 소켓 연결 성공", frame);

          this.musicStompClient.subscribe(
            "/socket/music/" + this.code + "/0/send",
            (res) => {
              const resBody = JSON.parse(res.body);

              if (resBody["type"] == "delete") {
                this.$toast(
                  `[${resBody["obj"]["fileName"]}]이(가) 칠판에서 제거되었습니다.`,
                  options
                );
                this.$store.commit("deleteMusic", {
                  idx: resBody["index"],
                });
              }
              if (resBody["type"] == "update") {
                this.$toast(
                  `[${resBody["obj"]["fileName"]}]이(가) 칠판에서 수정되었습니다.`,
                  options
                );
                this.$store.commit("updateMusic", {
                  music: {
                    id: resBody["index"],
                    url: resBody["obj"]["url"],
                    fileName: resBody["obj"]["fileName"],
                    timestamp: resBody["obj"]["timestamp"],
                    distortion: {
                      object: null,
                      value: resBody["obj"]["distortion"],
                    },
                    volume: {
                      object: null,
                      value: resBody["obj"]["volume"],
                    },
                    gain: {
                      object: null,
                      value: resBody["obj"]["gain"],
                    },
                    reverb: {
                      object: null,
                      value: resBody["obj"]["reverb"],
                    },
                  },
                });
              }
            }
          );
        },
        (error) => {
          // 소켓 연결 실패
          console.log("뮤직보드 소켓 연결 실패", error);
          this.connected = false;
        }
      );
    },
    downloadButton() {
      console.log("download");
    },
    musicPlayButton() {
      if (this.play) {
        Tone.Transport.pause();
      } else {
        if (this.$refs.player) {
          this.$refs.player.forEach((el) => {
            el.addToTransport();
          });
          Tone.Transport.start();
        } else {
          // error
          console.log("player 가 존재하지 않습니다.");
        }
      }

      this.play = !this.play;
    },
    musicStopButton() {
      Tone.Transport.stop();
      this.play = false;
    },
    onScroll() {
      this.scrollInvoked++;
    },
    deleteMusic(id) {
      this.send("music", {
        type: "delete",
        index: id,
        obj: { url: this.music[id].url, fileName: this.music[id].fileName },
      });
    },
    updateMusicOption(id) {
      this.send("music", {
        type: "update",
        index: id,
        obj: {
          url: this.music[id].url,
          fileName: this.music[id].fileName,
          volume: this.music[id].volume.value,
          distortion: this.music[id].distortion.value,
          gain: this.music[id].gain.value,
          reverb: this.music[id].reverb.value,
        },
      });
    },
  },
};
</script>

<style>
</style>