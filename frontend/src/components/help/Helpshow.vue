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
      <v-carousel height="500" hide-delimiters show-arrows>
        <v-carousel-item v-for="(slide, i) in describe" :key="i">
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
      require("@/assets/help/0.png"),
      require("@/assets/help/1.png"),
      require("@/assets/help/2.png"),
      require("@/assets/help/3.png"),
      require("@/assets/help/4.png"),
      require("@/assets/help/5.png"),
      require("@/assets/help/6.png"),
      require("@/assets/help/7.png"),
      require("@/assets/help/8.png"),
      require("@/assets/help/9.png"),
      require("@/assets/help/10.png"),
      require("@/assets/help/11.png"),
      require("@/assets/help/12.png"),
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
      ["플레이어", "버튼 설명"],
      ["채팅", "와! 채팅"],
      ["이름!", "이름 입력!"],
      ["채팅창", "채팅! 정말 대단해!"],
      ["공유", "여러 사람과 함께 즐겨요~"],
      ["공유 분기", "이건 무엇 저건 무엇"],
      ["도움말", "이 화면을 다시 볼 수 있어요."],
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