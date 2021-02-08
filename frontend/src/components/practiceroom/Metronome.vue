<template>
  <v-card
    class="d-flex flex-column justify-space-between"
    id="metronome"
    color="amber lighten-1"
    elevation="0"
    width="100%"
    height="100%"
  >
  <v-card-title>
    <v-card
    dark
    elevation="0"
    width="50px"
    rounded="circle"
      class="pa-2 mx-3 d-inline-block text-center" :class="beatIndex? 'grey' : 'brown darken-1'"
    >{{ beatIndex? beatIndex : 1 }}</v-card>
    <p style="font-size: 2em; " class="pt-2">BPM: </p>
    <v-text-field
      v-model.number="bpm"
      class="mt-0 pl-2 pb-3 pt-0"
      hide-details
      single-line
      flat solo
      background-color="amber lighten-1"
      type="number"
      style="max-width: 100px; width: auto; font-size: 2em;"
    ></v-text-field>
    <p style="font-size: 2em;" class="pt-2">Beat: </p>
    <v-text-field
      :disabled="isPlaying()"
      v-model.number="beatsPerBar"
      class="mt-0 mx-auto pl-2 pb-3 pt-0"
      hide-details
      flat solo
      background-color="amber lighten-1"
      single-line
      type="number"
      style="max-width: 80px; width: auto; font-size: 2em;"
      hint="Beats/bar"
    ></v-text-field>
  </v-card-title>
  <v-card-text class="d-flex align-center
  pa-0">
  </v-card-text>
  <v-card-actions>
    <v-btn 
    fab
    small
    elevation="0"
    color="#00ff0000"
    v-if="isPlaying()" @click="onStop">
      <v-icon>mdi-stop</v-icon>
    </v-btn>
    <v-btn 
    fab
    small
    elevation="0"
    color="#00ff0000"
    v-else @click="onStart">
      <v-icon>mdi-play</v-icon>
    </v-btn>
    <v-spacer></v-spacer>
    <div>
      {{ volume | volume(mute) }}
      <v-btn 
      fab
      small
      elevation="0"
      color="#00ff0000"
      @click="onVolumeDown">
        <v-icon>mdi-volume-medium</v-icon>
      </v-btn>
      <v-btn
      fab
      small
      elevation="0"
      color="#00ff0000"
      @click="onVolumeUp">
        <v-icon>mdi-volume-high</v-icon>
      </v-btn>
      <v-btn 
      fab
      small
      elevation="0"
      color="#00ff0000"
      @click="onVolumeMute">
        <v-icon>mdi-volume-off</v-icon>
      </v-btn>
    </div>
  </v-card-actions>
  </v-card>
</template>

<script>
import Vue from 'vue'
import { Player, Sequence, Transport, start, Destination } from 'tone';

const accent = new Player('http://i4a105.p.ssafy.io:8080/downloadFile/Ping%20Hi.wav').toDestination();
const beat = new Player('http://i4a105.p.ssafy.io:8080/downloadFile/Ping%20Low.wav').toDestination();

Vue.filter('volume',( value, mute ) => {
  if (mute) {
    return '';
  }
  const prefix = value > 0? '+' : '';
  return `${prefix}${value}db`;
})

export default {
  name: "metronome",
  data: function() {
    return {
      bpmMin: 20,
      bpmMax: 300,
      bpm: 60,

      beatsPerBar: 4,
      beatIndex: 0,

      isStopped: true,
      
      volume: 0,
      mute: false,
    }
  },
  methods: {

    isPlaying() {
      if (this.isStopped) {
        return false;
      }
      return true;
    },

    onStart() {
      if (this.isPlaying()) {
        return;
      }
      if (this.isStopped) {
        start()
        const sequence = this.createNoteSequence();
        console.log(sequence)
        sequence.start(0);
      }
      this.isStopped = false;

      Transport.start();

    },

    onStop() {
      start()
      Transport.stop();
      Transport.cancel(0);
      Transport.seconds = 0;
      this.beatIndex = 0;
      this.isStopped = true;
    },

    onVolumeDown() {
      this.mute = false;
      this.volume = Math.round(Math.max(this.volume - 3, -24));
      Destination.volume.value = this.volume;
    },

    onVolumeUp() {
      this.mute = false;
      this.volume = Math.round(Math.min(this.volume + 3, 24));
      Destination.volume.value = this.volume;
    },

    onVolumeMute() {
      this.mute = !this.mute
      Destination.mute = this.mute;
    },

    createNoteSequence() {
      const accentNote = 'G2';
      const beatNote = 'C2';
      const notes = [accentNote, ...new Array(this.beatsPerBar -1).fill(beatNote)]
      return new Sequence((time, note) => {
        switch(note) {
          case accentNote:
            console.log('노트', Transport)
            this.beatIndex = 0;
            accent.start(time);
            break;
          case beatNote:
            if (this.beatIndex == 0) {
              this.beatIndex = 1;
            }
            this.beatIndex += 1;
            beat.start(time);
            break;
          default:
            break;
        }
      }, notes, '4n');
    },


  },

  watch: {
    bpm: function () {
      Transport.bpm.value = this.bpm
    },

    beatsPerBar() {
      Transport.timeSignature = this.beatsPerBar;
    }
  },

  mounted() {
    Transport.bpm.value = this.bpm
    start()
    
  },

}
</script>

<style>

#metronome {
  position: relative;
  padding: 10px;
}

.v-card__title {
    align-items: center;
    display: flex;
    flex-wrap: wrap;
    font-size: 1.25rem;
    font-weight: 500;
    letter-spacing: 0.0125em;
    line-height: 2rem;
    word-break: break-all;
    padding: 0px !important;
}

.v-card__actions {
  align-items: center;
  display: flex;
  padding: 0px !important;
}

.v-text-field > .v-input__control > .v-input__slot:before {
  border-style: none; 
}


</style>