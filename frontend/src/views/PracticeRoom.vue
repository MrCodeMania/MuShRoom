<template>
  <div>
    <h1>PracticeRoom</h1>
    <player
      url="http://i4a105.p.ssafy.io:8080/downloadFile/sample.mp3"
    ></player>
  </div>
</template>

<script>
import Player from "../components/practiceroom/Player.vue";

export default {
  name: "PracticeRoom",
  components: {
    Player,
  },
  data() {
    return {};
  },
};
</script>

<style>
</style>