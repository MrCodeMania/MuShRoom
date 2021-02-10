<template>
  <div>
    <div class="d-flex file-title">
      <p class="file-name">{{title}}</p>
      <v-spacer></v-spacer>
      <v-btn icon color="gray" @click="sendDelete(n)" v-if="status === 'Master'">
        <v-icon>mdi-close</v-icon>
      </v-btn>
    </div>
    <div class="d-flex align-center" style="background-color: black;">
      <div class="ml-5 mr-3" style="background-color: red;">
        <v-btn
          v-if="this.state == 'paused' || this.state == 'stopped'"
          icon color="black"
          :disabled="player == null"
          v-on:click="start()"
        >
          <v-icon>mdi-play</v-icon>
        </v-btn>
        <v-btn
          v-else
          icon color="black"
          :disabled="player == null"
          v-on:click="pause()"
        >
          <v-icon>mdi-pause</v-icon>
        </v-btn>
        <v-btn
          icon color="black"
          v-on:click="stop()"
        >
          <v-icon>mdi-stop</v-icon>
        </v-btn>
      </div>
        
      <div style="flex: 10; background-color: blue;">
        <Waveform :url="url" height="64"></Waveform>
      </div>

      <!-- 이 부분부터 ReadOnly -->
      <div class="ml-3 mr-3" style="background-color: green;" v-if="status === 'Master'">
        <!-- dropdown button -->
        <v-btn icon color="black" v-on:click="toggleDropdown()">
          <v-icon v-if="isShow == 0">mdi-chevron-down</v-icon>
          <v-icon v-else>mdi-chevron-up</v-icon>
        </v-btn>
      </div>

    </div>

    <div class="ml-5">
      <v-simple-table :hidden="isShow == 0">
        <tbody>
          <tr>
            <td>Volume</td>
            <td>
              <input
                type="range"
                min="-30"
                max="20"
                step="0.01"
                v-model="volume.value"
                class="slider"
                id="myRange"
                v-on:input="changeVolume(volume.value)"
              />
            </td>
          </tr>
          <tr>
            <td>Distortion</td>
            <td>
              <div v-if="distortion.object == null">
                <button v-on:click="addDistortion()">add dist</button>
              </div>
              <div v-else>
                <input
                  type="range"
                  min="0"
                  max="5"
                  step="0.01"
                  v-model="distortion.value"
                  class="slider"
                  id="distortion"
                  v-on:input="changeDistortion(distortion.value)"
                />
                <button v-on:click="delDistortion()">del</button>
              </div>
            </td>
          </tr>
          <tr>
            <td>Gain</td>
            <td>
              <div v-if="gain.object == null">
                <button v-on:click="addGain()">add gain</button>
              </div>
              <div v-else>
                <input
                  type="range"
                  min="0"
                  max="10"
                  step="0.01"
                  v-model="gain.value"
                  class="slider"
                  id="myRange"
                  v-on:input="changeGain(gain.value)"
                />
                <button v-on:click="delGain()">del</button>
              </div>
            </td>
          </tr>
          <tr>
            <td>Loop</td>
            <td>
              <!-- loop check box -->
              <v-checkbox v-on:change="toggleLoop($event)"></v-checkbox>
            </td>
          </tr>
          <tr>
            <td>LoopStart</td>
            <td>
              <v-text-field
                type="number"
                label="Start Time"
                v-model="loopStart"
                v-on:change="setLoopTime()"
              ></v-text-field>
            </td>
          </tr>
          <tr>
            <td>LoopEnd</td>
            <td>
              <v-text-field
                type="number"
                label="End Time"
                v-model="loopEnd"
                v-on:change="setLoopTime()"
              ></v-text-field>
            </td>
          </tr>
          <!-- temp start -->
          <tr>
            <td>Delay</td>
            <td>
              <v-text-field
                type="number"
                label="Delay Time (단위: note)"
                v-model="delay"
              ></v-text-field>
            </td>
          </tr>
          <tr>
            <td>StartAt</td>
            <td>
              <v-text-field
                type="number"
                label="Start Time (단위: note)"
                v-model="offset"
              ></v-text-field>
            </td>
          </tr>
          <!-- temp end -->
        </tbody>
      </v-simple-table>
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
    title: String,
    url: String,
    n: Number,
  },
  components: {
    Waveform,
  },
  data() {
    return {
      distortion: {
        object: null,
        value: 0,
      },
      volume: {
        object: null,
        value: -5,
      },
      gain: {
        object: null,
        value: 0,
      },
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
    const player = new Tone.Player(this.url, () => {
      this.player = player;
      this.player.onstop = () => {
        console.log(this.state);
        if (this.state == "stopped") {
          Tone.Transport.stop();
        } else if (this.state == "paused") {
          Tone.Transport.stop();
        } else {
          // 기본적으로 종료되면 started로 넘어옴
          this.stop();
        }
      };
    }).toDestination();
    
    this.status = this.$store.state.status;
  },
  methods: {
    start() {
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

      this.state = "started"; // delay를 줄 경우, player.state로 즉시 받아오면 stopped가 넘어옴
      this.isExist = false;
    },
    pause() {
      this.currentTime = Tone.Transport.seconds;
      this.state = "paused";
      Tone.Transport.stop();
      Tone.Transport.cancel();
    },
    stop() {
      this.currentTime = 0;
      this.state = "stopped";
      Tone.Transport.stop();
      Tone.Transport.cancel(); // clean objects
      this.isExist = false;
    },
    changeDistortion(value) {
      this.distortion.object.distortion = value;
    },
    changeVolume(value) {
      this.player.volume.value = value;
      console.log(this.player.volume.value);
    },
    changeGain(value) {
      this.gain.object.gain.value = value;
    },
    addGain() {
      const gain = new Tone.Gain(0).toDestination();
      this.gain.object = gain;
      this.player.connect(gain);
    },
    addDistortion() {
      const distortion = new Tone.Distortion(0).toDestination();
      this.distortion.object = distortion;
      this.player.connect(distortion);
    },
    delGain() {
      this.player.disconnect(this.gain.object);
      this.gain.object = null;
    },
    delDistortion() {
      this.player.disconnect(this.distortion.object);
      this.distortion.object = null;
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
      if (this.isExist) return;

      Tone.start();
      this.player.sync().start();
      this.isExist = true;
      // Tone.Transport.start(); // play
    },
    sendDelete(n) {
      this.$emit("deleteMusic", n);
    },
    setTime(sec) {
      console.log("Player set time: ", sec);
    },
  },
};
</script>

<style>

.file-title {
  padding: 5px 5px 0px 15px;
  margin: 0px !important;
}

.file-name{
  font-size: 1.5em;
  color: balck;
  margin: 0px !important;
}

</style>