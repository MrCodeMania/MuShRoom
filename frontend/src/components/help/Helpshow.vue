<template>
  <v-overlay
    :absolute="true"
    :value="this.$store.getters.getHelp"
    opacity="0.8"
    z-index="4"
  >
    <div class="align-center d-flex">
      <v-img
        v-if="show"
        transition="v-slide-y-transition"
        dark
        max-width="400"
        max-height="400"
        contain
        :lazy-src="imgsrc[nowPage]"
        :src="imgsrc[nowPage]"
      ></v-img>

      <div class="ml-4">
        <v-card
          elevation="20"
          class="mx-auto"
          min-width="300px"
          max-width="500px"
          color="white"
          outlined
        >
          <div class="d-flex justify-space-between mt-2 ml-2 mr-2 align-center">
            <div class="black--text" style="font-weight: bold; font-size: 20px">
              {{ describe[nowPage][0] }}
            </div>

            <v-btn
              color="indigo"
              outlined
              fab
              small
              @click="changeHelpState"
              text
            >
              <v-icon color="red">mdi-close-thick</v-icon>
            </v-btn>
          </div>
          <v-list-item three-line>
            <v-list-item-content>
              <v-list-item-subtitle class="black--text">
                {{ describe[nowPage][1] }}
              </v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
          <div class="d-flex justify-end mb-3">
            <div class="btn-group">
              <v-btn
                :disabled="nowPage === 0"
                small
                color="indigo  lighten-1"
                @click="prev"
                >이전</v-btn
              >
              <v-btn
                v-if="nowPage !== maxPage"
                :disabled="nowPage === maxPage"
                small
                color="indigo darken-4"
                @click="next"
                >다음</v-btn
              >
              <v-btn
                v-if="nowPage === maxPage"
                small
                color="indigo darken-4"
                @click="changeHelpState"
                >종료</v-btn
              >
            </div>
          </div>
        </v-card>
      </div>
    </div>
  </v-overlay>
</template>

<script>
export default {
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
      console.log("이전");
      this.nowPage -= 1;
      this.show = false;
      setTimeout(() => {
        this.show = true;
      }, 1);

      //this.$forceUpdate();
    },
    next() {
      this.nowPage += 1;
      console.log("다음");
      this.show = false;
      setTimeout(() => {
        this.show = true;
      }, 1);
      // this.$forceUpdate();
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
      console.log("watched:", val);
      this.nowPage = 0;
    },
  },
};
</script>

<style>
#con {
  position: absolute;
}
</style>