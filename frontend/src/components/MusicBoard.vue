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
          v-for="(item, idx) in music"
          :key="item.id"
          :n="idx"
          :page="page"
          :music="item"
          @deleteMusic="deleteMusic"
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
  props: ["page"],
  components: {
    Player,
  },
  data() {
    return {
      play: false,
      scrollInvoked: 0,
    };
  },
  created(){
    this.status = this.$store.state.status;
  },
  computed: {
    getURL() {
      return this.$store.getters.getURL;
    },
    music: function() {
      return this.$store.getters.getBoard(this.page);
    },
    length: function() {
      return this.$store.getters.getPageLength;
    }
  },
  watch: {
    page: function() {
      this.$refs.player.forEach((el) => {
        el.removeFromTransport();
      });
      console.log(this.music);
    }
  },
  methods: {
    downloadButton() {
      console.log("download");

      // let a = new MusicDummy();
      // MusicDummies.push(a);
      // console.log(MusicDummies);
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
      let page = this.page, idx = id;
      console.log(idx);
      this.$store.commit('deleteMusic', {
            page, idx
          });
        // this.music.splice(i, 1).list;
    },
    addPage() {
      this.$store.commit('addPage', this.page);
      this.$emit("add");
    },
    removePage() {
      this.$store.commit('removePage', this.page);
      this.$emit("remove");
    },
  },
};
</script>

<style>
</style>