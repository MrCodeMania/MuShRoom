<template>
  <div>
    <div class="d-flex file-title">
      <p class="file-name">{{ music.fileName }}</p>
      <v-spacer></v-spacer>
      <v-btn
        icon
        color="gray"
        @click="sendDelete()"
        v-if="status === 'Master'"
      >
        <v-icon>mdi-close</v-icon>
      </v-btn>
    </div>
    <div class="d-flex align-center mx-3 border">
      <div>
        <v-btn
          v-if="this.state == 'paused' || this.state == 'stopped'"
          icon
          color="black"
          :disabled="player == null"
          v-on:click="start()"
        >
          <v-icon>mdi-play</v-icon>
        </v-btn>
        <v-btn
          v-else
          icon
          color="black"
          :disabled="player == null"
          v-on:click="pause()"
        >
          <v-icon>mdi-pause</v-icon>
        </v-btn>
        <v-btn icon color="black" v-on:click="stop()">
          <v-icon>mdi-stop</v-icon>
        </v-btn>
      </div>

      <div class="" style="flex: 10; background-color: blue">
        <Waveform
          :url="music.url"
          height="64"
          mouse="true"
          timeline="true"
          :idx="n"
          @setTime="setTime"
        ></Waveform>
      </div>

      <!-- 이 부분부터 ReadOnly -->
      <div class="ml-3 mr-3" v-if="status === 'Master'">
        <!-- dropdown button -->
        <v-btn icon color="black" v-on:click="toggleDropdown()">
          <v-icon v-if="isShow == 0">mdi-chevron-down</v-icon>
          <v-icon v-else>mdi-chevron-up</v-icon>
        </v-btn>
      </div>
    </div>

    <div>
      <v-sheet
        style="margin: 0px 75.5px 0px 87px"
        height="auto"
        :hidden="isShow == 0"
      >
        <v-card>
          <div class="pa-4 d-flex justify-space-around">
            <div>
              <label for="volume">Volume:</label>
              <input
                type="range"
                min="-30"
                max="20"
                step="0.01"
                v-model="music.volume"
                class="slider ml-2"
                name="volume"
                id="volume"
                v-on:input="changeVolume(music.volume.value)"
              />
            </div>
            <div>
              <label for="distortion">Distortion:</label>
              <input
                type="range"
                min="0"
                max="5"
                step="0.01"
                v-model="music.distortion.value"
                class="slider ml-2"
                name="distortion"
                id="distortion"
                v-on:input="changeDistortion(music.distortion.value)"
              />
            </div>
            <div>
              <label for="gain">Gain:</label>
              <input
                type="range"
                min="0"
                max="10"
                step="0.01"
                v-model="music.gain.value"
                class="slider ml-2"
                name="gain"
                id="gain"
                v-on:input="changeGain(music.gain.value)"
              />
            </div>
          </div>

          <v-divider></v-divider>

          <div class="pa-4 d-flex justify-space-around">
            <div class="d-flex align-center">
              <label for="loop">Loop</label>
              <v-checkbox
                id="loop"
                name="loop"
                class="ml-2"
                v-on:change="toggleLoop($event)"
              ></v-checkbox>
            </div>
            <div class="d-flex align-center">
              <p class="align-self-center pt-3">LoopStart:</p>
              <v-text-field
                class="ml-2"
                type="number"
                label="Start Time"
                v-model="loopStart"
                v-on:change="setLoopTime()"
              ></v-text-field>
            </div>
            <div class="d-flex align-center">
              <p class="align-self-center pt-3">LoopEnd:</p>
              <v-text-field
                class="ml-2"
                type="number"
                label="End Time"
                v-model="loopEnd"
                v-on:change="setLoopTime()"
              ></v-text-field>
            </div>
          </div>

          <v-divider></v-divider>

          <div class="pa-4 d-flex justify-space-around">
            <div class="d-flex">
              <p class="align-self-center pt-3">Delay:</p>
              <v-text-field
                class="ml-2"
                type="number"
                label="Delay Time (단위: note)"
                v-model="delay"
              ></v-text-field>
            </div>
            <div class="d-flex">
              <p class="align-self-center pt-3">StartAt:</p>
              <v-text-field
                class="ml-2"
                type="number"
                label="Start Time (단위: note)"
                v-model="offset"
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
    page: Number,
    music: Object,
    n: Number
  },
  components: {
    Waveform,
  },
  data() {
    return {
      player: null,
      state: "stopped",
      isShow: 0,
      loopStart: 0.0,
      loopEnd: 0.0,
      isExist: false,
      delay: 0,
      offset: 0,
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
          console.log(this.state);
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
              console.log("over!!");
              this.stop();
            }
          }
        };

        const gain = new Tone.Gain(0).toDestination();
        this.music.gain.object = gain;
        this.player.connect(gain);

        const distortion = new Tone.Distortion(0).toDestination();
        this.music.distortion.object = distortion;
        this.player.connect(distortion);
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
        now + Tone.Time(this.delay).toSeconds(),
        Tone.Time(this.offset).toSeconds() + this.currentTime
      );
    },
    pause() {
      this.currentTime = Tone.Transport.seconds;
      this.state = "paused";
      this.player.unsync();
      Tone.Transport.stop();
    },
    stop() {
      this.currentTime = 0;
      this.state = "stopped";
      this.player.unsync();
      Tone.Transport.stop();
    },
    changeDistortion(value) {
      this.music.distortion.object.distortion = value;
    },
    changeVolume(value) {
      this.player.volume.value = value;
      console.log(this.player.volume.value);
    },
    changeGain(value) {
      this.music.gain.object.gain.value = value;
    },
    toggleDropdown() {
      this.isShow ^= 1;
    },
    toggleLoop(e) {
      this.player.loop = e;
    },
    setLoopTime() {
      this.player.loopStart = this.loopStart;
      this.player.loopEnd = this.loopEnd;
    },
    addToTransport() {
      this.player.unsync();
      this.player.sync().start(0);
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
      console.log("Player set time: ", sec);
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
</style>
