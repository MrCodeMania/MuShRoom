<template>
  <v-card class="musicBoard nav-color" elevation="0" width="100%" height="100%">
    <v-card
      id="musicListId"
      class="musicList overflow-y-auto nav-color"
      min-height="73vh"
      max-height="73vh"
      v-scroll.self="onScroll"
      elevation="0"
    >
        <Player
          class="ma-8 border smallcomponent-color"
          v-for="(item, idx) in music"
          :key="idx"
          :n="idx"
          :page="page"
          :music="item"
          @deleteMusic="deleteMusic"
          @updateMusicOption="updateMusicOption"
          ref="player"
        />
    </v-card>
    <v-divider dark></v-divider>
    <v-card
      class="buttonBar d-flex nav-color"
      elevation="0"
      width="100%"
      height="8vh"
    >
      <v-spacer></v-spacer>
      <v-card class="d-flex justify-end nav-color" elevation="0">
        <v-btn class="musicboard_btn pt-2" icon dark large @click="downloadButton">
          <v-icon dark size="30px">mdi-download</v-icon>
        </v-btn>
        <v-btn class="musicboard_btn mx-n2" icon dark large @click="musicPlayButton">
          <div v-if="!play">
            <v-icon dark size="30px">mdi-play</v-icon>
          </div>
          <div v-else>
            <v-icon dark size="30px">mdi-pause</v-icon>
          </div>
        </v-btn>
        <v-btn class="musicboard_btn mr-5" icon dark large @click="musicStopButton">
          <v-icon dark size="30px">mdi-stop</v-icon>
        </v-btn>
      </v-card>
    </v-card>
  </v-card>
</template>

<script>
import Player from "./practiceroom/Player";
import * as Tone from "tone";
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import Config from '@/store/config'

export default {
  props: ["page"],
  components: {
    Player,
  },
  data() {
    return {
      play: false,
      scrollInvoked: 0,
      code : "",
      status
    };
  },
  created(){
    this.status = this.$store.state.status;
    
    this.code = document.location.href.split('=')[1];

    this.connect();
  },
  computed: {
    getURL() {  
      return this.$store.getters.getURL;  
    },
    music: function() {
      return this.$store.getters.getBoard(this.page);
    },
    length: function() {
      return this.$store.state.pageLen;
    }
  },
  watch: {
    page: function() {
      if (this.$refs.player) {
        this.$refs.player.forEach((el) => {
          el.removeFromTransport();
        });
      }
    }
  },
  methods: {
    send(type, msg) {
      console.log(msg);
      if(type == "music")
          this.musicStompClient.send("/socket/music/" + this.code + "/" + this.page + "/receive", JSON.stringify(msg),{});        
    },
    connect() {
      const serverURL = Config.ServerURL;

      let musicSocket = new SockJS(serverURL);
      this.musicStompClient = Stomp.over(musicSocket);
      this.musicStompClient.connect(
        {},
        frame => {
          // 소켓 연결 성공
          this.connected = true;
          console.log('뮤직보드 소켓 연결 성공', frame);

          for(let i = 0; i < 5; i++)
          {
            console.log(i);
            this.musicStompClient.subscribe("/socket/music/" + this.code + "/" + i + "/send", res => {
              const resBody = JSON.parse(res.body);
              
              if(resBody["type"] == "delete")
                  this.$store.commit('deleteMusic', {page : i, idx: resBody["index"]});
              if(resBody["type"] == "update")
                  this.$store.commit('updateMusic', {page : i, music: {
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
                    }
                    }});

            });
          }
        },
        error => {
          // 소켓 연결 실패
          console.log('뮤직보드 소켓 연결 실패', error);
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
      console.log("stop");
      Tone.Transport.stop();
      this.play = false;
    },
    onScroll() {
      this.scrollInvoked++;
    },
    deleteMusic(id) {
     this.send("music", {type: "delete", index: id, obj: {url : this.music[id].url, fileName : this.music[id].fileName}});
    },
    updateMusicOption(id) {
      console.log(this.music[id]);
      console.log(id);
      this.send("music", {type: "update", index: id, obj: {url : this.music[id].url, fileName : this.music[id].fileName,
           volume: this.music[id].volume.value, distortion: this.music[id].distortion.value, gain: this.music[id].gain.value, reverb: this.music[id].reverb.value}});
    }
  },
};
</script>

<style>

.musicboard_btn {
  margin: 10px 5px 0px;
}



</style>