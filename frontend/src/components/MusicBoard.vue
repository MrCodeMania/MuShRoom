<template>
  <v-card class="musicBoard" elevation="0" width="100%" height="100%">
    <v-card
      id="musicListId"
      class="musicList overflow-y-auto"
      min-height="70vh"
      max-height="70vh"
      v-scroll.self="onScroll"
      elevation="0"
    >
      <ul class="px-auto pt-2">
        <Player
          v-for="item in music"
          v-bind:key="item.id"
          v-bind:title="item.title"
          v-bind:url="item.url"
          v-bind:options="item.options"
          @deleteMusic="deleteMusic(item.id)"
          ref="player"
        />
      </ul>
    </v-card>
    <v-divider></v-divider>
    <v-card
      class="buttonBar d-flex justify-space-between"
      elevation="0"
      width="100%"
    >
      <v-card class="d-flex justify-start" elevation="0">
        <!-- 페이지 생성, 삭제 -->
        <v-btn v-if="status === 'Master'" fab text height="50px" :disabled="length == 5" @click="addPage">
          <v-icon large> mdi-card-plus </v-icon>
        </v-btn>
        <v-btn
          v-if="status === 'Master'"
          fab
          text
          height="50px"
          :disabled="length == 1"
          @click="removePage"
        >
          <v-icon large> mdi-card-minus </v-icon>
        </v-btn>
      </v-card>
      <!-- <v-btn height="50px" text @click="addMusicList">Test </v-btn> -->
      <v-card class="d-flex justify-end" elevation="0">
        <v-btn fab height="50px" text @click="downloadButton">
          <v-icon dark large>mdi-download-circle</v-icon>
        </v-btn>
        <v-btn fab height="50px" text @click="musicPlayButton">
          <div v-if="!play">
            <v-icon dark large>mdi-arrow-right-drop-circle</v-icon>
          </div>
          <div v-else>
            <v-icon dark large>mdi-pause-circle</v-icon>
          </div>
        </v-btn>
        <v-btn fab height="50px" text @click="musicStopButton">
          <v-icon dark large>mdi-stop-circle</v-icon>
        </v-btn>
      </v-card>
    </v-card>
  </v-card>
</template>

<script>
import Player from "./practiceroom/Player";
import * as Tone from "tone";

export default {
  props: ["pageData", "length", "page"],
  components: {
    Player,
  },
  data() {
    return {
      times: 0, // id 증진 넘버
      musicURL: "",
      music: this.pageData,
      play: false,
      scrollInvoked: 0,
    };
  },
  created(){
      this.status = this.$store.state.status;
  },
  methods: {
    addMusicList(tempTitle) {
      this.music.push({
        id: this.times++,
        title: tempTitle,
        url: this.musicURL,
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
      console.log(this.$refs);
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
    deleteMusic(n) {
      console.log(n);
      for (let i = 0; i < this.music.length; i++)
        if (this.music[i].id == n) this.music.splice(i, 1);
    },
    addPage() {
      console.log("add");
      this.$emit("add", this.music);
    },
    removePage() {
      this.$emit("remove");
    },
  },
  computed: {
    getURL() {
      return this.$store.getters.getURL;
    },
  },
  watch: {
    getURL(val) {
      if (val[0] === "") return;
      console.log("watched", val);
      this.musicURL = val[0];
      this.addMusicList(val[1]);
      this.$store.commit("pushURL", "", "");
    },
    pageData() {
      this.music = this.pageData;
    },
    page() {
      this.times = this.music.length;
    },
  },
};
</script>

<style>
</style>