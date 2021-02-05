<template>
  <div>
    <div class="d-flex align-center">
      <div class="mb-3 border d-flex" style="flex: 1">
        <v-btn
          v-if="this.state == 'stopped'"
          class="ml-3 mr-3"
          :disabled="player == null"
          v-on:click="start()"
        >
          <v-icon dense>mdi-play</v-icon>
        </v-btn>
        <v-btn
          v-else
          class="ml-3 mr-3"
          :disabled="player == null"
          v-on:click="stop()"
        >
          <v-icon dense>mdi-pause</v-icon>
        </v-btn>
      </div>

      <div style="flex: 5">
        <Waveform :url="url" height="64"></Waveform>
      </div>

      <div class="ml-3" style="flex: 1">
        <!-- dropdown button -->
        <v-btn v-on:click="toggleDropdown()">
          <v-icon v-if="isShow == 0">mdi-chevron-down</v-icon>
          <v-icon v-else>mdi-chevron-up</v-icon>
        </v-btn>
      </div>

      <div style="flex: 1">
        <v-icon class="ml-2" dense @click="sendDelete(n)">mdi-delete</v-icon>
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
                  id="myRange"
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
                label="Offset (단위: note)"
                v-model="offset"
              ></v-text-field>
            </td>
          </tr>
          <tr>
            <td>StartAtTime</td>
            <td>
              <v-text-field
                type="number"
                label="Offset (단위: second)"
                v-model="offsetSecond"
              ></v-text-field>
            </td>
          </tr>
          <!-- temp end -->
        </tbody>
      </v-simple-table>
    </div>
  </div>
</template>

<script>
import * as Tone from "tone";
import Waveform from "./Waveform.vue";

export default {
  name: "Player",
  props: {
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
      offset: 0,
      offsetSecond: 0,
      delay: 0,
    };
  },
  created() {
    const player = new Tone.Player(this.url, () => {
      this.player = player;
      //player.start();
      //player.sync().start(0);
      this.player.onstop = () => {
        console.log("event");
        this.state = "stopped";
        Tone.Transport.stop();
      };
    }).toDestination();
  },
  methods: {
    start() {
      // //this.player.unsync();
      // this.player.sync().start(0);
      // Tone.start();
      // Tone.Transport.start();
      Tone.Transport.cancel(); // clean objects

      Tone.start(); // bug fix

      console.log(this.player.sampleTime);
      this.player.sync().start(0);
      var now = Tone.now();

      // now: Transport 생성 후 현재 시간
      // offset: 시작할 오프셋 위치. 초 단위
      // 박자로 시간과 오프셋을 맞추고싶다면: time or offset + Tone.Time(박자).toSeconds();
      if (this.offsetSecond) {
        Tone.Transport.start(
          now + Tone.Time(this.delay).toSeconds(),
          this.offsetSecond
        );
      } else {
        Tone.Transport.start(
          now + Tone.Time(this.delay).toSeconds(),
          Tone.Time(this.offset).toSeconds()
        );
      }

      this.state = "started"; // delay를 줄 경우, player.state로 즉시 받아오면 stopped가 넘어옴
      this.isExist = false;
    },
    pause() {
      Tone.Transport.pause();
      this.state = this.player.state;
    },
    stop() {
      //this.player.stop();
      //this.player.start();
      Tone.Transport.stop();
      Tone.Transport.cancel(); // clean objects
      this.state = this.player.state;
      this.isExist = false;
    },
    changeDistortion(value) {
      this.distortion.object.distortion = value;
    },
    changeVolume(value) {
      //this.volume.object.volume.value = value;
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
      //Tone.Transport.start(); // start
    },
    sendDelete(n) {
      this.$emit("deleteMusic", n);
    },
  },
};
</script>

<style>
</style>