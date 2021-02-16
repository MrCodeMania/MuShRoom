<template>
  <v-app-bar color="black" tile flat class="pt-2 pb-1" rounded height="auto">
    <v-btn text max-width="180" height="50" @click="toMain">
      <v-img max-width="180" height="50" :src="logo"> </v-img>
    </v-btn>
    <v-spacer></v-spacer>
    <v-btn dark text @click="toggleChat()" :color="openChat ? 'grey' : 'white'">
      <div style="position: relative" class="pr-2">
        <v-card
          v-if="newChat > 0"
          elevation="0"
          :width="newChat < 10 ? 11 : 20"
          height="11"
          rounded="circle"
          color="red"
          style="position: absolute; z-index: 2; margin-top: 2px"
          class="ml-4"
        >
          <div
            style="
              font-size: 8px !important;
              position: absolute;
              bottom: 5px;
              height: 8px;
            "
            :class="newChat < 10 ? 'one' : 'two'"
          >
            {{ newChat }}
          </div>
        </v-card>
        <v-icon dark> mdi-chat </v-icon>
      </div>
      Chat
    </v-btn>
    <span style="color: white">|</span>
    <Share />
  </v-app-bar>
</template>

<script>
import Share from "../practiceroom/Share.vue";
export default {
  components: { Share },
  props: ["openChat", "hasNickName", "newChat"],
  data() {
    return {
      logo: require("@/assets/tmpLogo.png"),
    };
  },
  methods: {
    toMain() {
      this.$router.push({ name: "Home" });
    },
    toggleChat: function () {
      // 닉네임이 없으면
      if (!this.hasNickName) {
        // 닉네임 세팅 모달 열기
        this.$emit("openModal");
      }
      this.$emit("toggleChat");
    },
  },
};
</script>

<style>
.one {
  left: 1.5px;
}

.two {
  left: 3px;
}
</style>