<template>
  <div>
<<<<<<< HEAD
    <h1>
        PracticeRoom
    </h1>
    <Metronome />
=======
    <h1>PracticeRoom</h1>
    <player
      url="http://i4a105.p.ssafy.io:8080/downloadFile/sample.mp3"
    ></player>
>>>>>>> c29841de7d945e728b5fd260ce0fffb657440fa8
  </div>
</template>

<script>
<<<<<<< HEAD
import Metronome from '@/components/practiceroom/Metronome.vue';


export default {
  name: 'practiceroom',
  components: {
    Metronome,
  }
}
=======
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
>>>>>>> c29841de7d945e728b5fd260ce0fffb657440fa8
</script>

<style>
</style>