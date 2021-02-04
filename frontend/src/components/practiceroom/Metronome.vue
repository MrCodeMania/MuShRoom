<template>
  <v-card
    class="d-flex flex-column justify-space-between"
    id="metronome"
    color="amber"
    width="100%"
    height="100%"
  >
  <v-card-title>
    <p style="font-size: 3em;" class="pt-2">{{ bpm }}</p>
    <v-spacer></v-spacer>
    <div
      class="pa-3 ml-9 rounded-circle d-inline-block" :class="beatIndex? 'grey' : 'brown lighten-1'"
    >{{ beatIndex? beatIndex : 1 }}/4</div>
    <v-text-field
      :disabled="isPlaying()"
      v-model.number="beatsPerBar"
      class="mt-0 ml-3 pt-0"
      hide-details
      single-line
      type="number"
      style="width: 10px"
    ></v-text-field>/4
  </v-card-title>
  <v-card-text>
    <v-slider
      v-model.number="bpm"
      class="align-center"
      :max="bpmMax"
      :min="bpmMin"
      hide-details
      style="width: 300px;"
    >
      <template v-slot:append>
        <v-text-field
          v-model.number="bpm"
          class="mt-0 pt-0"
          hide-details
          single-line
          type="number"
          style="width: 45px"
        ></v-text-field>
      </template> 
    </v-slider>
  </v-card-text>
  <v-card-actions>
    <v-btn small>
      <v-icon  v-if="isPlaying()" @click="onStop">mdi-stop</v-icon>
      <v-icon  v-else @click="onStart">mdi-play</v-icon>
    </v-btn>
    <v-spacer></v-spacer>
    <div>
      {{ volume | volume(mute) }}
      <v-btn small @click="onVolumeDown">
        <v-icon>mdi-volume-medium</v-icon>
      </v-btn>
      <v-btn small @click="onVolumeUp">
        <v-icon>mdi-volume-high</v-icon>
      </v-btn>
      <v-btn small @click="onVolumeMute">
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
  name: "metronome1",
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
      console.log('시퀀스', Transport)
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
    console.log('마운티드', Transport)
    start()
    
  },

}
</script>

<style>

#metronome {
  position: relative;
  padding: 20px;
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

</style>