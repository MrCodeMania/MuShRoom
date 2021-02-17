<template>
  <v-overlay
    :absolute="true"
    :value="this.$store.getters.getHelp"
    opacity="0.1"
    color="white"
    z-index="4"
  >
    <v-sheet color="1E1E1E" width="700" height="500" rounded>
      <v-btn
        id="exit"
        color="indigo"
        outlined
        fab
        small
        @click="changeHelpState"
        text
        v-if="firstIn"
      >
        <v-icon color="white">mdi-close-thick</v-icon>
      </v-btn>
      <v-carousel
        height="500"
        hide-delimiter-background
        show-arrows
        @change="test"
      >
        <template v-slot:prev="{ on, attrs }">
          <v-btn icon v-bind="attrs" v-on="on"
            ><v-icon>mdi-arrow-left-bold-outline</v-icon></v-btn
          >
        </template>
        <template v-slot:next="{ on, attrs }">
          <v-btn icon v-bind="attrs" v-on="on"
            ><v-icon>mdi-arrow-right-bold-outline</v-icon></v-btn
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
      ["메트로놈", `BPM과 Beat를 변경하여 원하는 박자의 ~`],
      ["음악 추가", `녹음을 하거나컴퓨터에서 음악을추가할 수 있습니다`],
      [
        "녹음",
        `메트로놈 1사이클 후 녹음을 시작합니다. 파일 이름을 입력하면 서버에 업로드 됩니다.`,
      ],
      [
        "레코드뷰",
        `추가 한 음악 파일을 보관합니다.
        플레이어에 추가하거나 뷰에서 제거가 가능합니다.`,
      ],
      ["플레이어", "플레이어다."],
      ["플레이어", "와! 다양한 효과 설명 설명 설명 설명~."],
      ["플레이어", "와! 다양한 효과 설명 설명 설명 설명~."],
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