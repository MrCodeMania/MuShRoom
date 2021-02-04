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
      <ul class="pl-1 pr-3">
        <Player
          v-for="(item, n) in music"
          v-bind:key="n"
          v-bind:title="item.title"
          v-bind:url="item.url"
          v-bind:options="item.options"
          @deleteMusic="deleteMusic(n)"
        />
      </ul>
    </v-card>
    <v-divider></v-divider>
    <v-card class="buttonBar text-end" elevation="0">
      <!-- <v-btn height="50px" text @click="addMusicList">Test </v-btn> -->
      <v-btn height="50px" text @click="downloadButton">
        <v-icon dark large>mdi-download-circle</v-icon>
      </v-btn>
      <v-btn height="50px" text @click="musicPlayButton">
        <div v-if="!play">
          <v-icon dark large>mdi-arrow-right-drop-circle</v-icon>
        </div>
        <div v-else>
          <v-icon dark large>mdi-pause-circle</v-icon>
        </div>
      </v-btn>
      <v-btn height="50px" text @click="musicStopButton">
        <v-icon dark large>mdi-stop-circle</v-icon>
      </v-btn>
    </v-card>
  </v-card>
</template>

<script>
import Player from "./practiceroom/Player";

export default {
  props: ["url"],
  components: {
    Player,
  },
  data() {
    return {
      times: 0, // id 증진 넘버
      music: [],
      musicURL: "",
      play: false,
      scrollInvoked: 0,
    };
  },
  methods: {
    addMusicList() {
      this.music.push({
        id: this.times++,
        title: "되네이게",
        url: this.musicURL,
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
    onScroll() {
      this.scrollInvoked++;
    },
    deleteMusic(n) {
      console.log(n);
      this.music.splice(n, 1);
    },
  },
  computed: {
    getURL() {
      return this.$store.getters.getURL;
    },
  },
  watch: {
    getURL(val) {
      if (val === "") return;
      console.log("watched", val);
      this.musicURL = val;
      this.addMusicList();
      this.$store.commit("pushURL", "");
    },
  },
};
</script>

<style>
</style>