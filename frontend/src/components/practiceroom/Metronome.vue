<template>
  <v-card
    class="d-flex flex-column justify-space-between"
    id="metronome"
    color="amber lighten-1"
    elevation="0"
    width="100%"
    height="100%"
  >
    <v-card-title class="d-flex justify-space-between">
      <v-fade-transition>
        <v-avatar
          :color="beatIndex ? 'grey' : 'brown darken-1'"
          :style="isPlaying() ? { animationDuration: animationDuration } : ''"
          class="mb-1 v-avatar--metronome pa-3 mx-3"
          size="50"
          >{{ beatIndex + 1 }}</v-avatar
        >
      </v-fade-transition>
      <div class="d-flex">
        <div class="d-flex">
          <p style="font-size: 2em" class="pt-2">BPM:</p>
          <v-text-field
            v-model.number="bpm"
            class="mt-0 pl-1 pb-2"
            hide-details
            single-line
            flat
            solo
            type="number"
            background-color="amber lighten-1"
            style="max-width: 100px; width: auto; font-size: 2em"
          ></v-text-field>
        </div>
        <div class="d-flex">
          <p style="font-size: 2em" class="pt-2">Beat:</p>
          <v-text-field
            :disabled="isPlaying()"
            v-model.number="beatsPerBar"
            class="mt-0 pl-1 pb-2"
            hide-details
            flat
            solo
            type="number"
            background-color="amber lighten-1"
            single-line
            style="max-width: 80px; width: auto; font-size: 2em"
            hint="Beats/bar"
          ></v-text-field>
        </div>
      </div>
    </v-card-title>
    <v-card-text class="d-flex align-center pa-0"> </v-card-text>
    <v-card-actions>
      <v-btn
        fab
        small
        elevation="0"
        color="amber lighten-1"
        v-if="isPlaying()"
        @click="onStop"
      >
        <v-icon>mdi-stop</v-icon>
      </v-btn>
      <v-btn
        fab
        small
        elevation="0"
        color="amber lighten-1"
        v-else
        @click="onStart"
      >
        <v-icon>mdi-play</v-icon>
      </v-btn>
      <v-spacer></v-spacer>
      <div>
        {{ volume | volume(mute) }}
        <v-btn
          fab
          small
          elevation="0"
          color="amber lighten-1"
          @click="onVolumeDown"
        >
          <v-icon>mdi-volume-medium</v-icon>
        </v-btn>
        <v-btn
          fab
          small
          elevation="0"
          color="amber lighten-1"
          @click="onVolumeUp"
        >
          <v-icon>mdi-volume-high</v-icon>
        </v-btn>
        <v-btn
          fab
          small
          elevation="0"
          color="amber lighten-1"
          @click="onVolumeMute"
        >
          <v-icon>mdi-volume-off</v-icon>
        </v-btn>
      </div>
    </v-card-actions>
  </v-card>
</template>

<script>
import Vue from "vue";
import { mapState } from "vuex";
import { Player, Sequence, Transport, start, Destination } from "tone";

const accent = new Player(
  "http://i4a105.p.ssafy.io:8080/downloadFile/Ping%20Hi.wav"
).toDestination();
const beat = new Player(
  "http://i4a105.p.ssafy.io:8080/downloadFile/Ping%20Low.wav"
).toDestination();

Vue.filter("volume", (value, mute) => {
  if (mute) {
    return "mute";
  }
  return `${value+24}db`;
});

export default {
  name: "metronome",
  data: function () {
    return {
      bpm: 60,
      hideSlider: true,
      beatsPerBar: 4,
      beatIndex: 0,

      isStopped: true,

      volume: 0,
      mute: false,
    };
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
        start();
        const sequence = this.createNoteSequence();
        console.log(sequence);
        sequence.start(0);
      }
      this.isStopped = false;

      Transport.start();
    },

    onStop() {
      Transport.stop();
      Transport.cancel(0);
      Transport.seconds = 0;
      this.beatIndex = 0;
      this.isStopped = true;
    },
    onRecordStop() {
      if (this.beatIndex == this.beatsPerBar - 1) {
        console.log("마지막 박자에 멈춰", this.beatIndex);
        this.$store.commit("setRC", "stopRecord");
        console.log("끝났어", this.recordStartState);
        Transport.stop();
        Transport.cancel(0);
        Transport.seconds = 0;
        this.beatIndex = 0;
        this.isStopped = true;
        return;
      }
      console.log("마지막 박자까지 가", this.beatIndex);
      setTimeout(() => {
        this.onRecordStop();
      }, (1000 * 60) / (this.bpm + 20));
    },

    onVolumeDown() {
      this.mute = false;
      this.volume = Math.round(Math.max(this.volume - 3, -24));
      Destination.volume.value = this.volume;
      if (this.volume == -24) {
        Destination.mute = !this.mute;
      }
    },

    onVolumeUp() {
      this.mute = false;
      this.volume = Math.round(Math.min(this.volume + 3, 24));
      Destination.volume.value = this.volume;
    },

    onVolumeMute() {
      this.mute = !this.mute;
      Destination.mute = this.mute;
    },

    createNoteSequence() {
      var flag = 0
      const accentNote = "G2";
      const beatNote = "C2";
      const notes = [
        accentNote,
        ...new Array(this.beatsPerBar - 1).fill(beatNote),
      ];
      return new Sequence(
        (time, note) => {
          switch (note) {
            case accentNote:
              this.beatIndex = 0;
              if (flag) {
                console.log("왔고", this.recordStartState);
                this.$store.commit("setRC", "startRecord");
                console.log("변했어", this.recordStartState);
                flag = 0
              }
              accent.start(time);
              break;
            case beatNote:
              this.beatIndex += 1;
              if (
                this.beatIndex == this.beatsPerBar - 1 &&
                this.recordStartState === "startMetro"
              ) {
                flag = 1
              }
              beat.start(time);
              break;
            default:
              break;
          }
        },
        notes,
        "4n"
      );
    },

    countDowntimer() {
      if (this.startTime > 0) {
        setTimeout(() => {
          this.startTime -= 1;
          console.log(this.startTime);
          this.countDowntimer();
        }, 1000);
      } else {
        this.startTime = 4;
        // console.log(this.startTime)
      }
    },
  },

  watch: {
    bpm: function () {
      if (this.bpm > 300) {
        Vue.set(this.$data, "bpm", 300);
        this.bpm = 300;
      } else if (this.bpm < 20) {
        this.bpm = 20;
      }
      Transport.bpm.value = this.bpm;
    },

    beatsPerBar() {
      if (this.beatsPerBar > 16) {
        this.beatsPerBar = 16;
      } else if (this.beatsPerBar < 1) {
        this.beatsPerBar = 1;
      }
      Transport.timeSignature = this.beatsPerBar;
    },
    getRC(val) {
      if (val === "startMetro") {
        this.onStart();
        console.log("METRO watched", val);
      } else if (val === "stopMetro") {
        console.log("METRO watched", val);
        this.onRecordStop();
      }
    },
  },

  computed: {
    animationDuration() {
      return `${30 / this.bpm}s`;
    },
    getRC() {
      return this.$store.getters.getRC;
    },
    ...mapState(["recordStartState"]),
  },

  mounted() {
    Transport.bpm.value = this.bpm;
    start();
  },
};
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