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
      >
        <v-icon color="red">mdi-close-thick</v-icon>
      </v-btn>
      <v-carousel height="500" hide-delimiters show-arrows-on-hover>
        <v-carousel-item class="mt-3" v-for="(slide, i) in describe" :key="i">
          <Helpview :describe="slide" :src="imgsrc[i]" />
          <div class="d-flex justify-center">{{ i + 1 }}/{{ maxPage + 1 }}</div>
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
    show: true,
    nowPage: 0,
    maxPage: 2,
    box: "",
    share: "",
    imgsrc: [
      require("@/assets/help/help1.png"),
      require("@/assets/help/help1-1.png"),
      require("@/assets/help/help1-2.png"),
      require("@/assets/help/help2.png"),
      require("@/assets/help/help3.png"),
      require("@/assets/help/help4.png"),
    ],
    describe: [
      ["플레이어", "이것은 플레이어 입니다"],
      ["New Page", "새로운 페이지 추가 가능합니다"],
      ["재생", "한번에 재생이 가능해요"],
      ["메트로놈", "메트로놈이에요"],
      [
        "작업보드",
        "녹음을하거나 파일업로드를 하고, 플레이어에 추가가 가능해요",
      ],
      ["채팅", "와! 채팅 대박!"],
    ],
  }),
  methods: {
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