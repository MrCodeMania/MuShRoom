<template>
  <v-card class="musicBoard component-color" elevation="0" width="100%" height="100%">
    <v-card
      id="musicListId"
      class="musicList overflow-y-auto component-color"
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
    <v-divider></v-divider>
    <v-card
      class="buttonBar d-flex justify-space-between component-color"
      elevation="0"
      width="100%"
    >
      <v-card class="d-flex justify-start component-color" elevation="0">
        <!-- 페이지 생성, 삭제 -->
        <v-btn v-if="status === 'Master'" class="musicboard_btn ml-5" icon color="black" large :disabled="length == 5" @click="addPage">
          <v-icon large> mdi-card-plus </v-icon>
        </v-btn>
        <v-btn
          v-if="status === 'Master'"
          icon
          class="musicboard_btn"
          color="black"
          large
          :disabled="length == 1"
          @click="removePage"
        >
          <v-icon large> mdi-card-minus </v-icon>
        </v-btn>
      </v-card>
      <!-- <v-btn height="50px" text @click="addMusicList">Test </v-btn> -->
      <v-card class="d-flex justify-end component-color" elevation="0">
        <v-btn class="musicboard_btn pt-2" icon color="black" large @click="downloadButton">
          <v-icon dark large>mdi-download</v-icon>
        </v-btn>
        <v-btn class="musicboard_btn" icon color="black" large @click="musicPlayButton">
          <div v-if="!play">
            <v-icon dark large>mdi-play</v-icon>
          </div>
          <div v-else>
            <v-icon dark large>mdi-pause</v-icon>
          </div>
        </v-btn>
        <v-btn class="musicboard_btn mr-5" icon color="black" large @click="musicStopButton">
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

.musicboard_btn {
  margin: 10px 5px 0px;
}



</style>