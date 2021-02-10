<template>
  <v-card class="musicBoard nav-color" elevation="0" width="100%" height="100%">
    <v-card
      id="musicListId"
      class="musicList overflow-y-auto nav-color"
      min-height="70vh"
      max-height="70vh"
      v-scroll.self="onScroll"
      elevation="0"
    >
        <Player
          class="ma-3 border smallcomponent-color"
          v-for="(item, idx) in music"
          :key="item.id"
          :n="idx"
          :page="page"
          :music="item"
          @deleteMusic="deleteMusic"
          ref="player"
        />
    </v-card>
    <v-divider dark></v-divider>
    <v-card
      class="buttonBar d-flex nav-color"
      elevation="0"
      width="100%"
    >
      <v-spacer></v-spacer>
      <v-card class="d-flex justify-end nav-color" elevation="0">
        <v-btn class="musicboard_btn pt-2" icon dark large @click="downloadButton">
          <v-icon dark large>mdi-download</v-icon>
        </v-btn>
        <v-btn class="musicboard_btn" icon dark large @click="musicPlayButton">
          <div v-if="!play">
            <v-icon dark large>mdi-play</v-icon>
          </div>
          <div v-else>
            <v-icon dark large>mdi-pause</v-icon>
          </div>
        </v-btn>
        <v-btn class="musicboard_btn mr-5" icon dark large @click="musicStopButton">
          <v-icon dark large>mdi-stop</v-icon>
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
      if (this.$refs.player) {
        this.$refs.player.forEach((el) => {
          el.removeFromTransport();
        });
      }
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
      this.$store.commit('deleteMusic', {
            page, idx
          });
    },
  },
};
</script>

<style>

.musicboard_btn {
  margin: 10px 5px 0px;
}



</style>