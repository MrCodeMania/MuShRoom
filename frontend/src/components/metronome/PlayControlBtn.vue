<template>
  <div>
    <v-btn
      icon
      color="white"
      v-if="isPlaying"
      @click="stop"
      :disabled="recordStartState === 'startMetro' || recordStartState === 'startRecord' ? true : false"
    >
      <v-icon size="30px">mdi-stop</v-icon>
    </v-btn>
    <v-btn
      icon
      color="white"
      v-else
      @click="start"
    >
      <v-icon size="30px">mdi-play</v-icon>
    </v-btn>
  </div>
</template>

<script>
import { mapState } from 'vuex'

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
  },
  computed: {
    ...mapState(['recordStartState'])
  }
}
</script>

<style>

</style>