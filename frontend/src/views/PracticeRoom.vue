<template>
  <div>
    <Metronome />
    <player
      url="http://i4a105.p.ssafy.io:8080/downloadFile/sample.mp3"
    ></player>
  </div>
</template>

<script>
import Player from "../components/practiceroom/Player.vue";
import Metronome from "@/components/practiceroom/Metronome.vue";

export default {
  name: "PracticeRoom",
  components: {
    Player,
    Metronome
  },
  data() {
    return {};
  },
};
</script>

<style>
</style>