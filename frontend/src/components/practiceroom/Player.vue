<template>
  <div id="player">
    <div class="d-flex file-title">
      <p class="file-name pb-2 medium">{{ music.fileName }}</p>
      <v-spacer></v-spacer>
      <v-btn icon dark plain @click="sendDelete()" v-if="status === 'Master'">
        <v-icon>mdi-close</v-icon>
      </v-btn>
    </div>
    <div class="d-flex align-center border">
      <div>
        <v-btn
          v-if="this.state == 'paused' || this.state == 'stopped'"
          icon
          dark
          plain
          class="mt-4"
          :disabled="player == null"
          v-on:click="start()"
        >
          <v-icon>mdi-play</v-icon>
        </v-btn>
        <v-btn
          v-else
          icon
          plain
          class="mt-4"
          dark
          :disabled="player == null"
          v-on:click="pause()"
        >
          <v-icon>mdi-pause</v-icon>
        </v-btn>
        <v-btn icon plain dark class="mt-4" v-on:click="stop()">
          <v-icon>mdi-stop</v-icon>
        </v-btn>
      </div>

      <div class="" style="flex: 10">
        <Waveform
          :class="status === 'Master' ? '' : 'mr-4'"
          :url="music.url"
          height="64"
          mouse="true"
          timeline="true"
          :idx="n"
          full="false"
          color="#00A2FF"
          @setTime="setTime"
          ref="waveform"
        ></Waveform>
      </div>

      <!-- 이 부분부터 ReadOnly -->
      <div class="ml-3 mr-3" v-if="status === 'Master'">
        <!-- dropdown button -->
        <v-btn icon dark plain class="mt-4" v-on:click="toggleDropdown()">
          <v-icon v-if="isShow == 0">mdi-chevron-down</v-icon>
          <v-icon v-else>mdi-chevron-up</v-icon>
        </v-btn>
      </div>
    </div>

    <div>
      <v-sheet
        style="margin: 0px 72px 0px 84px; background-color: #00ff0000"
        height="auto"
        :hidden="isShow == 0"
      >
        <v-card style="background-color: #00ff0000; border-radius: 0px">
          <div class="px-3 pt-3 d-flex justify-space-around">
            <div>
              <p>Volume</p>
              <v-slider
                color="#ffffffbb"
                class="ml-2"
                track-color="grey darken-2"
                v-model="music.volume.value"
                min="-30"
                max="20"
                step="0.01"
                style="width: 100px !important"
                @change="changeVolume(music.volume.value)"
              ></v-slider>
            </div>
            <div>
              <p>Distortion</p>
              <v-slider
                color="#ffffffbb"
                class="ml-2"
                track-color="grey darken-2"
                v-model="music.distortion.value"
                min="0"
                max="5"
                step="0.01"
                style="width: 100px !important"
                @change="changeDistortion(music.distortion.value)"
              ></v-slider>
            </div>
            <div>
              <p>Gain</p>
              <v-slider
                color="#ffffffbb"
                class="ml-2"
                track-color="grey darken-2"
                v-model="music.gain.value"
                min="0"
                max="10"
                step="0.01"
                style="width: 100px !important"
                @change="changeGain(music.gain.value)"
              ></v-slider>
            </div>
            <div>
              <p>Reverb</p>
              <v-slider
                color="#ffffffbb"
                class="ml-2 mb-n2"
                track-color="grey darken-2"
                v-model="music.reverb.value"
                min="0"
                max="1"
                step="0.01"
                style="width: 100px !important"
                @change="changeReverb(music.reverb.value)"
              ></v-slider>
            </div>
          </div>

          <v-divider
            style="background-color: rgba(255, 255, 255, 0.733)"
          ></v-divider>

          <div class="pa-3 d-flex justify-space-around">
            <div class="d-flex align-center">
              <p class="align-self-center pt-3">Loop</p>
              <v-checkbox
                id="loop"
                name="loop"
                dark
                class="ml-1 mt-2 pt-6"
                v-model="music.loop.loop"
                v-on:change="toggleLoop($event)"
              ></v-checkbox>
            </div>
            <div class="d-flex align-center">
              <p class="align-self-center pt-3">Loop Start:</p>
              <v-text-field
                class="ml-2"
                type="number"
                dark
                style="width: 80px !important"
                label="Start Time"
                min="0"
                v-model.number="music.loop.loopStart"
                v-on:change="setLoopTime()"
              ></v-text-field>
            </div>
            <div class="d-flex align-center">
              <p class="align-self-center pt-3">Loop End:</p>
              <v-text-field
                class="ml-2"
                type="number"
                dark
                style="width: 80px !important"
                label="End Time"
                min="0"
                v-model.number="music.loop.loopEnd"
                v-on:change="setLoopTime()"
              ></v-text-field>
            </div>
          </div>

          <v-divider
            style="background-color: rgba(255, 255, 255, 0.733)"
          ></v-divider>

          <div class="pa-3 d-flex justify-space-around">
            <div class="d-flex">
              <p class="align-self-center pt-3">Delay:</p>
              <v-text-field
                class="ml-2"
                type="number"
                dark
                style="width: 150px !important"
                label="Delay Time"
                min="0"
                v-model="music.delay.delay"
                @change="updateMusicOption()"
              ></v-text-field>
            </div>
            <div class="d-flex">
              <p class="align-self-center pt-3">StartAt:</p>
              <v-text-field
                class="ml-2"
                dark
                type="number"
                style="width: 150px !important"
                label="Start Time"
                min="0"
                v-model="music.delay.offset"
                @change="updateMusicOption()"
              ></v-text-field>
            </div>
          </div>
        </v-card>
      </v-sheet>
    </div>
    <v-divider class="mt-3"></v-divider>
  </div>
</template>

<script>
import * as Tone from "tone";
import Waveform from "./Waveform.vue";

export default {
  name: "Player",
  props: {
    music: Object,
    n: Number,
  },
  components: {
    Waveform,
  },
  data() {
    return {
      player: null,
      state: "stopped",
      isShow: 0,
      isExist: false,
      currentTime: 0,
    };
  },
  created() {
    this.constructor();
  },
  watch: {
    music: function () {
      if (this.player) this.player.dispose();
      this.constructor();
    },
  },
  methods: {
    constructor() {
      const player = new Tone.Player(this.music.url, () => {
        this.player = player;
        this.player.onstop = () => {
          if (this.state == "stopped") {
            Tone.Transport.stop();
          } else if (this.state == "paused") {
            Tone.Transport.stop();
          } else {
            // 재생 시간이 최대 시간 이후일 때 (정지)
            if (
              player.buffer.duration + player.context.lookAhead <
              Tone.Transport.seconds
            ) {
              this.stop();
            }
          }
        };
        player.volume.value = this.music.volume.value;
        player.loop = this.music.loop.loop;
        player.loopStart = this.music.loop.loopStart;
        player.loopEnd = this.music.loop.loopEnd;
        const gain = new Tone.Gain(0).toDestination();
        this.music.gain.object = gain;
        this.player.connect(gain);

        const distortion = new Tone.Distortion(0).toDestination();
        distortion.distortion = this.music.distortion.value;
        this.music.distortion.object = distortion;
        this.player.connect(distortion);

        const reverb = new Tone.Reverb(1.5).toDestination();
        reverb.wet.value = this.music.reverb.value;
        this.music.reverb.object = reverb;
        this.player.connect(reverb);
      }).toDestination();

      this.status = this.$store.state.status;
      Tone.start(); // ...start()를 실행하기 위한 사전 작업
    },
    start() {
      this.state = "started"; // delay를 줄 경우, player.state로 즉시 받아오면 stopped가 넘어옴
      this.player.unsync();
      Tone.start(); // ...start()를 실행하기 위한 사전 작업
      Tone.Transport.stop();
      Tone.Transport.cancel(); // clean objects

      this.player.sync().start(0);

      // now: Transport 생성 후 현재 시간
      // offset: 시작할 오프셋 위치. 초 단위
      // 박자로 시간과 오프셋을 맞추고싶다면: time or offset + Tone.Time(박자).toSeconds();
      var now = Tone.now();
      Tone.Transport.start(
        now + this.music.delay.delay,
        this.music.delay.offset + this.currentTime
      );
      document.getElementById(``);

      setTimeout(() => {
        this.moveProgressBar();
      }, this.music.delay.delay * 1000);
    },
    pause() {
      this.currentTime = Tone.Transport.seconds;
      this.state = "paused";
      this.player.unsync();
      Tone.Transport.stop();
    },
    stop() {
      this.$refs.waveform.setTime(0);
      this.currentTime = 0;
      this.state = "stopped";
      this.player.unsync();
      Tone.Transport.stop();
    },
    changeDistortion(value) {
      this.music.distortion.object.distortion = value;
      this.updateMusicOption();
    },
    changeVolume(value) {
      this.player.volume.value = value;
      this.updateMusicOption();
    },
    changeGain(value) {
      this.music.gain.object.gain.value = value;
      this.updateMusicOption();
    },
    changeReverb(value) {
      this.music.reverb.object.wet.value = value;
      this.updateMusicOption();
    },
    updateMusicOption() {
      this.$emit("updateMusicOption", this.n);
    },
    updateAll() {
      this.music.distortion.object.distortion = this.music.distortion.value;
      this.player.volume.value = this.music.volume.value;
      this.music.gain.object.gain.value = this.music.gain.value;
      this.music.reverb.object.wet.value = this.music.reverb.value;
    },
    toggleDropdown() {
      this.isShow ^= 1;
    },
    toggleLoop(e) {
      this.player.loop = e;
      this.music.loop.loop = e;
      this.updateMusicOption();
    },
    setLoopTime() {
      this.player.loopStart = this.music.loop.loopStart;
      this.player.loopEnd = this.music.loop.loopEnd;
      this.updateMusicOption();
    },
    addToTransport() {
      this.player.unsync();
      this.player.sync().start(0);
    },
    moveProgressBar() {
      let interval = setInterval(() => {
        if (Tone.Transport.seconds > 0) {
          if (this.player.loop) {
            if (this.music.loop.loopEnd > this.music.loop.loopStart) {
              this.$refs.waveform.setTime(
                (Tone.Transport.seconds %
                  (this.music.loop.loopStart - this.music.loop.loopEnd)) +
                  this.music.loop.loopStart
              );
            } else {
              this.$refs.waveform.setTime(
                Tone.Transport.seconds + this.music.loop.loopStart
              );
            }
          } else this.$refs.waveform.setTime(Tone.Transport.seconds);
        }
        if (this.player.state != "started") clearInterval(interval);
      }, 50);
    },
    removeFromTransport() {
      this.currentTime = 0;
      this.player.unsync();
    },
    sendDelete() {
      this.player.unsync();
      this.player.dispose();
      this.$emit("deleteMusic", this.n);
    },
    setTime(sec) {
      this.player.unsync();

      this.currentTime = sec;
      if (this.state == "started") {
        this.start();
      }
    },
  },
};
</script>

<style>
.file-title {
  padding: 5px 5px 0px 15px;
  margin: 0px !important;
}

.file-name {
  font-size: 1.5em;
  color: balck;
  margin: 0px !important;
}

#player p {
  margin: 0px !important;
}
</style>
