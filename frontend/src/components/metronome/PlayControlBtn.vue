<template>
  <div>
    <v-btn
      icon
      large
      color="white"
      v-if="isPlaying"
      @click="stop"
    >
      <v-icon large>mdi-stop</v-icon>
    </v-btn>
    <v-btn
      icon
      large
      color="white"
      v-else
      @click="start"
    >
      <v-icon large>mdi-play</v-icon>
    </v-btn>
  </div>
</template>

<script>

export default {
  name: 'MetronomeBtn',
  props: {
    isPlaying: Boolean,
  },
  methods: {
    start() {
      this.$emit('start');
    },
    stop() {
      this.$emit('stop');
    },
  }
}
</script>

<style>

</style>