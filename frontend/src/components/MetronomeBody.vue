<template>
  <v-card class="metronome-main nav-color" height="100%" width="100%" elevation="0">
    <v-card class="d-flex pa-2 metronome-screen" color="brown darken-4">
      <BeatIndicator :bpm="bpm" :beatIndex="beatIndex" :isPlaying="isPlaying()"/>
      <v-card class="d-flex ml-2 justify-space-around" height="100%" width="100%" color="#868488">
        <div class="d-flex-column pl-4">
          <p class="metronome-now">{{ bpm }} <span class="metronome-label"> BPM </span> </p>
          <v-btn plain class="mt-n3 pl-2" 
            icon color="black" @click="decrease('bpm')" 
            @mousedown="decreaseStart('bpm')" 
            @mouseleave="decreaseEnd()"
            @mouseup="decreaseEnd()"><v-icon>mdi-minus</v-icon>
          </v-btn>
          <v-btn plain class="mt-n3 pr-2" 
            icon color="black" @click="increase('bpm')" 
            @mousedown="increaseStart('bpm')" 
            @mouseleave="increaseEnd()"
            @mouseup="increaseEnd()"><v-icon>mdi-plus</v-icon>
          </v-btn>
        </div>
        <div class="d-flex-column pr-4">
          <p class="metronome-now">{{ beatsPerBar }} <span class="metronome-label"> Beat </span> </p>
          <v-btn plain class="mt-n3 ml-n2 pl-2" 
            icon color="white" @click="decrease('beat')" 
            @mousedown="decreaseStart('beat')" 
            @mouseleave="decreaseEnd()" @mouseup="decreaseEnd()" 
            :disabled="isPlaying()"><v-icon>mdi-minus</v-icon>
          </v-btn>
          <v-btn plain class="mt-n3 pr-2" 
            icon color="white" @click="increase('beat')" 
            @mousedown="increaseStart('beat')" 
            @mouseleave="increaseEnd()" @mouseup="increaseEnd()"
            :disabled="isPlaying()"><v-icon>mdi-plus</v-icon>
          </v-btn>
        </div>
      </v-card>
    </v-card>
    <v-card class="d-flex align-center metronome-btn component-color2">
      <PlayControlBtn :isPlaying="isPlaying()" @start="onStart" @stop="onStop"/>
      <v-spacer></v-spacer>
      <!-- <VolumeBtn :mute="mute" :volume="volume" @volumeDown="onVolumeDown" @volumeUp="onVolumeUp" @volumeMute="onVolumeMute" @changeBySlide="onChangeBySlide"/> -->
      <div class="d-flex align-center">

        <v-btn
          icon
          large
          color="white"
          :disabled="isMute()"
          @click="onVolumeDown"
          class="volume-btn"
        >
        <v-icon >mdi-volume-minus</v-icon>
        </v-btn>
        <v-btn
          icon
          large
          color="white"
          :disabled="isMaxVolume()"
          @click="onVolumeUp"
          class="volume-btn"
        >
        <v-icon >mdi-volume-plus</v-icon>
        </v-btn>
        <v-btn
          icon
          color="white"
          @click="onVolumeMute"
          class="volume-slider"
        >
        <v-icon v-if="volume == -50 || mute">mdi-volume-mute</v-icon>
        <v-icon v-else-if="volume > -50 && volume <= 0">mdi-volume-medium</v-icon>
        <v-icon v-else>mdi-volume-high</v-icon>
        </v-btn>
        <v-slider
            color="brown darken-4"
            class="pt-5 volume-slider general-slider"
            track-color="grey"
            v-model="volume"
            max="50"
            min="-50"
            style="width: 150px !important;"
            @change="onChangeBySlide"
        ></v-slider>
        <p class="volume-number">{{ volume | volume(mute) }}</p>
      </div>
    </v-card>
  </v-card>
</template>

<script>
import Vue from "vue";
import BeatIndicator from "@/components/metronome/BeatIndicator.vue";
import PlayControlBtn from "@/components/metronome/PlayControlBtn.vue";

import { mapState } from "vuex";
import { Player, Sequence, Transport, start, Destination } from "tone";

const accent = new Player(
  "https://musicshareroom.tk/api/downloadFile/Ping%20Hi.wav"
).toDestination();
const beat = new Player(
  "https://musicshareroom.tk/api/downloadFile/Ping%20Low.wav"
).toDestination();



Vue.filter("volume", (value, mute) => {
  if (mute) {
    return "0";
  }
  return `${value+50}`;
});



export default {
  name: "metronome",
  components:{
    BeatIndicator,
    PlayControlBtn,
  },
  data: function () {
    return {
      bpm: 60,
      hideSlider: true,
      beatsPerBar: 4,
      beatIndex: 0,

      interval: false,
      clickTime: 0,

      isStopped: true,

      volume: 0,
      saveVolume: 0,
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

    isMute() {
      if (this.mute) {
        return true;
      }
      return false;
    },

    isMaxVolume() {
      if (this.volume == 50) {
        return true;
      }
      return false;
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
      this.volume = Math.round(Math.max(this.volume - 5, -50));
      Destination.volume.value = this.volume/2;
      if (this.volume == -50) {
        this.mute = !this.mute
        Destination.mute = this.mute;
      }
    },

    onVolumeUp() {
      this.mute = false;
      this.volume = Math.round(Math.min(this.volume + 5, 50));
      Destination.volume.value = this.volume/2;
    },

    onVolumeMute() {
      if(!this.mute) {
        this.saveVolume = this.volume;
        this.volume = -50;
      } else {
        this.volume = this.saveVolume;
      }
      this.mute = !this.mute;
      Destination.mute = this.mute;

    },

    onChangeBySlide() {
      if (this.mute) {
        this.onVolumeMute()
      }
      Destination.volume.value = this.volume/2;
      if (this.volume == -50) {
        Destination.mute = !this.mute;
      }
    },

    decrease(point) {
      if (point == 'bpm'){
        this.bpm--;
      } else {
        this.beatsPerBar--;
      }
    },

    increase(point) {
      if (point == 'bpm'){
        this.bpm++;
      } else {
        this.beatsPerBar++;
      }
    },

    decreaseStart(point) {
      if (!this.interval) {
        this.interval = setInterval(() => this.decrease(point), 100);
      }
    },
    decreaseEnd() {
      clearInterval(this.interval)
      this.interval = false
    },

    increaseStart(point) {
      if (!this.interval) {
        this.interval = setInterval(() => this.increase(point), 100);
      }
    },
    increaseEnd() {
      clearInterval(this.interval)
      this.interval = false
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


  },

  watch: {
    bpm: function () {
      if (this.bpm > 300) {
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
}
</script>

<style>

/* .metronome-main {
    border : 10px solid black !important;
} */

.metronome-screen {
    border-radius: 5px 5px 0px 0px ;
    height: 70%;
}

.metronome-label {
  margin-left: -6px;
  font-weight: 400;
  font-size: 16px !important;
}

.metronome-now {
  margin-bottom: 0px !important;
  font-weight: 600;
  font-size: 2rem;
}

.metronome-btn {
    border-radius: 0px 0px 3px 3px!important;
    height: 30%
}


@media all and (max-width: 1100px){
  .volume-slider {
    display: none !important;
  }
}

@media all and (min-width: 1100px){
  .volume-btn {
    display: none !important;
  }
}

.showVolumeBtn {
  display: none !important;
}

.volume-number {
  font-size: 25px;
  font-weight: 600;
  padding-top: 12px;
  width: 60px;
}


</style>