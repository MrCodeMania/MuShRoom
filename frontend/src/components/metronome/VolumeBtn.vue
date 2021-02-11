<template>
  <div class="d-flex align-center">
    {{ volume | volume(mute) }}
    <v-btn
    icon
    large
    color="black"
    @click="volumeDown"
    >
    <v-icon large>mdi-volume-minus</v-icon>
    </v-btn>
    <v-slider
      class="pt-5 volume-slider"
      v-model="slideVolume"
      max="50"
      min="-50"
      style="width: 100px !important;"
      @change="changeBySlide(slideVolume)"
    ></v-slider>
    <v-btn
    icon
    large
    color="black"
    @click="volumeUp"
    >
    <v-icon large>mdi-volume-plus</v-icon>
    </v-btn>
    <v-btn
    icon
    large
    :color="mute ? 'black': '' "
    @click="volumeMute"
    >
    <v-icon large>mdi-volume-off</v-icon>
    </v-btn>
  </div>
</template>

<script>
import Vue from "vue";


Vue.filter("volume", (value, mute) => {
  if (mute) {
    return "mute";
  }
  return `${value+50}`;
});

export default {
  name: 'VolumeBtn',
  props: {
    mute: Boolean,
    volume: Number,
  },
  data() {
    return {
      slideVolume: this.volume,
    }
  },
  methods: {
    volumeDown() {
      this.$emit('volumeDown');
    },
    volumeUp() {
      this.$emit('volumeUp');
    },
    volumeMute() {
      this.$emit('volumeMute');
    },
    changeBySlide(volume) {
      this.$emit('changeBySlide', volume)
    }
  }
}
</script>

<style>

@media all and (max-width: 1100px){
  .volume-slider {
    display: none !important;
  }
}

</style>