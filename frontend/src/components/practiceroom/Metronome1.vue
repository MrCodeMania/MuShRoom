<template>
  <v-container>
    <v-card
    flat
    color="transparent"
    max-width="300"
    >
      <v-card-text>
        <v-row>
          <v-col class="pr-4">
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
                  style="width: 50px"
                ></v-text-field>
              </template>
            </v-slider>
          </v-col>
        </v-row>
        <v-row>
          <v-text-field
            :disabled="isPlaying()"
            v-model.number="beatsPerBar"
            class="mt-0 pt-0"
            hide-details
            single-line
            type="number"
            style="width: 50px"
          ></v-text-field>
        </v-row>
      </v-card-text>
    </v-card>
    <v-btn height="200" width="200" @click="onStart">start</v-btn>
    <v-btn height="200" width="200" @click="onStop">stop</v-btn>
    <v-btn height="200" width="200" @click="onVolumeUp">up</v-btn>
    <v-btn height="200" width="200" @click="onVolumeDown">down</v-btn>
    <v-btn height="200" width="200" @click="onVolumeMute">mute</v-btn>
    <p>{{ volume | volume(mute) }}</p>
    <div
      class="pa-7 rounded-circle d-inline-block" :class="beatIndex? 'secondary' : 'primary'"
    ></div>{{ beatIndex? beatIndex : 1 }}/4
  </v-container>
</template>

<script>
import Vue from 'vue'
import { Synth, Player, Sequence, Transport, start, Destination } from 'tone';

const synth = new Synth().toDestination();
const accent = new Player('http://i4a105.p.ssafy.io:8080/downloadFile/Ping%20Hi.wav').toDestination();
const beat = new Player('http://i4a105.p.ssafy.io:8080/downloadFile/Ping%20Low.wav').toDestination();

Vue.filter('volume',( value, mute ) => {
  if (mute) {
    return '';
  }
  const prefix = value > 0? '+' : '';
  return `${prefix}${value}db`;
})

console.log(synth, accent, beat)

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
      const accentNote = 'G2';
      const beatNote = 'C2';
      const notes = [accentNote, ...new Array(this.beatsPerBar -1).fill(beatNote)]
      return new Sequence((time, note) => {
        switch(note) {
          case accentNote:
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

</style>