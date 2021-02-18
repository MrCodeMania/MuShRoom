<template>
  <v-overlay
    :absolute="true"
    :value="this.$store.getters.getHelp"
    opacity="0.1"
    color="white"
    z-index="4"
  >
    <v-sheet color="#00000a" width="700" height="500" rounded>
      <v-btn
        id="exit"
        color="#white"
        @click="changeHelpState"
        icon
        plain
        v-if="firstIn"
      >
        <v-icon size="26px">mdi-close</v-icon>
      </v-btn>
      <v-carousel
        height="500"
        hide-delimiter-background
        show-arrows
        @change="test"
      >
        <template v-slot:prev="{ on, attrs }">
          <v-btn
            style="background-color: rgb(30, 30, 30)"
            icon
            plain
            v-bind="attrs"
            v-on="on"
            ><v-icon size="26px">mdi-arrow-left-bold-outline</v-icon></v-btn
          >
        </template>
        <template v-slot:next="{ on, attrs }">
          <v-btn
            style="background-color: rgb(30, 30, 30)"
            icon
            plain
            v-bind="attrs"
            v-on="on"
            ><v-icon size="26px">mdi-arrow-right-bold-outline</v-icon></v-btn
          >
        </template>
        <v-carousel-item v-for="(slide, i) in describe" :key="i">
          <Helpview :describe="slide" :src="imgsrc[i]" />
        </v-carousel-item>
      </v-carousel>
    </v-sheet>
  </v-overlay>
</template>

<script>
import Helpview from "./Helpview.vue";
export default {
  components: {
    Helpview,
  },

  data: () => ({
    firstIn: false,
    show: true,
    nowPage: 0,
    maxPage: 2,
    box: "",
    share: "",
    imgsrc: [
      require("@/assets/help/1.gif"),
      require("@/assets/help/chat.gif"),
      require("@/assets/help/fileupload.gif"),
      require("@/assets/help/recordClick.gif"),
      require("@/assets/help/recordCross.gif"),
      require("@/assets/help/recordResult.gif"),
      require("@/assets/help/recordtoboard.gif"),
    ],
    describe: [
      [`내용1`],
      [`내용2`],
      [`내용3`],
      [`내용4`],
      [`내용5`],
      [`내용6`],
      [`내용7`],
    ],
    visit: [],
  }),
  methods: {
    test(e) {
      this.visit[e - 1] = 1;
      if (this.allVisitCheck() === true) {
        this.$cookies.set("visit", "visited");
        this.firstIn = true;
      }
    },
    allVisitCheck() {
      for (var i = 0; i < this.maxPage; i++) {
        if (this.visit[i] === 0) return false;
      }
      return true;
    },
    prev() {
      this.nowPage -= 1;
      this.show = false;
      setTimeout(() => {
        this.show = true;
      }, 1);
    },
    next() {
      this.nowPage += 1;
      this.show = false;
      setTimeout(() => {
        this.show = true;
      }, 1);
    },
    changeHelpState() {
      this.$store.commit("helpShowChange");
    },
  },
  mounted() {
    this.maxPage = this.describe.length - 1;
    this.visit = Array.from({ length: this.maxPage + 1 }, () => 0);
    if (this.$cookies.isKey("visit")) {
      this.firstIn = true;
    }
  },
  computed: {
    showState() {
      return this.$store.getters.getHelp;
    },
  },
  watch: {
    showState(val) {
      if (val === false) return;
      this.nowPage = 0;
    },
  },
};
</script>

<style>
#exit {
  position: absolute;
  z-index: 30;
  top: 10px;
  right: 10px;
}
</style>