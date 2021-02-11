<template>
  <v-fade-transition>
    <v-avatar
      :color="beatIndex ? 'grey' : 'brown darken-1'"
      :style="isPlaying ? { animationDuration: animationDuration } : ''"
      class="v-avatar--metronome"
      size="50"
      rounded="circle"
      >{{ beatIndex + 1 }}</v-avatar
    >
  </v-fade-transition>
</template>

<script>
export default {
  name: 'BeatIndicator',
  props:{
    isPlaying: Boolean,
    beatIndex: Number,
    bpm: Number,
  },
  computed: {
    animationDuration() {
      return `${30 / this.bpm}s`;
    }
  }
}
</script>

<style>

@keyframes metronome-example {
  from {
    transform: scale(0.9);
  }

  to {
    transform: scale(1);
  }
}

.v-avatar--metronome {

  animation-name: metronome-example;
  animation-iteration-count: infinite;
  animation-direction: alternate;
}

</style>