<template>
  <div class="musicBoard">
    <div id="musicListId" class="musicList">
      <ul>
        <!-- <li is="musicComp" v-for="item in music" 
                    v-bind:key="item.id"                
                    v-bind:title="item.title" 
                    v-bind:url="item.url" 
                    v-bind:options="item.options">
            </li> -->
        <!-- <Player
          url="http://i4a105.p.ssafy.io:8080/downloadFile/sample.mp3"
        ></Player> -->
        <!-- sample -->
        <Player
          url="http://i4a105.p.ssafy.io:8080/downloadFile/guitar-group.mp3"
        ></Player>
        <Player
          url="http://i4a105.p.ssafy.io:8080/downloadFile/drums-group.mp3"
        ></Player>
        <Player
          url="http://i4a105.p.ssafy.io:8080/downloadFile/bass-group.mp3"
        ></Player>
      </ul>
    </div>
    <div class="buttonBar">
      <v-btn @click="addMusicList">Test </v-btn>
      <v-btn @click="downloadButton">
        <v-icon dark large>mdi-download-circle</v-icon>
      </v-btn>
      <v-btn @click="musicPlayButton">
        <div v-if="!play">
          <v-icon dark large>mdi-arrow-right-drop-circle</v-icon>
        </div>
        <div v-else>
          <v-icon dark large>mdi-pause-circle</v-icon>
        </div>
      </v-btn>
      <v-btn @click="musicStopButton">
        <v-icon dark large>mdi-stop-circle</v-icon>
      </v-btn>
    </div>
  </div>
</template>

<script>
//import musicComp from "./musicComp";
import Player from "./practiceroom/Player.vue";

export default {
  components: {
    //musicComp: musicComp,
    Player,
  },
  data() {
    return {
      times: 6, // id 증진 넘버
      music: [
        {
          id: 1,
          title: "02_05 보컬",
          url: "url",
          options: "blahblah",
        },
        {
          id: 2,
          title: "02_04 드럼 비트",
          url: "url",
          options: "blahblah",
        },
        {
          id: 3,
          title: "02_03 베이스 비트",
          url: "url",
          options: "blahblah",
        },
        {
          id: 4,
          title: "02_02 작업물",
          url: "url",
          options: "blahblah",
        },
        {
          id: 5,
          title: "02_01 포크 송",
          url: "url",
          options: "blahblah",
        },
      ],
      play: false,
    };
  },
  methods: {
    addMusicList() {
      this.music.push({
        id: this.times++,
        title: "되네이게",
        url: "url",
        options: "blahblah",
      });
      console.log(this.times);
    },
    downloadButton() {
      console.log("download");

      // let a = new MusicDummy();
      // MusicDummies.push(a);
      // console.log(MusicDummies);
    },
    musicPlayButton() {
      console.log("play");
      this.play = !this.play;
    },
    musicStopButton() {
      console.log("stop");
      this.play = false;
    },
  },
};
</script>

<style>
.musicBoard {
  /* 포지션은 바꿔야함 */
  display: flex;
  flex-direction: column;
  position: fixed;
  left: 15%;
  min-width: 50%;
  max-width: 50%;
  min-height: 50%;
  max-height: 50%;
  border: 2px solid black;
}
.buttonBar {
  position: absolute;
  top: 90%;
  left: 70%;
  display: flex;
}
.musicList {
  min-width: 100%;
  max-width: 100%;
  min-height: 450px;
  max-height: 450px;
  border-bottom: 2px solid black;
  overflow: auto;
}
</style>