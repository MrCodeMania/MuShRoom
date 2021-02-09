<template>
  <div>
    <div class="d-flex align-center">
      <div class="border d-flex align-center" style="flex: 1">
        <div class="d-flex">
          <v-btn
            v-if="this.state == 'paused' || this.state == 'stopped'"
            class="ml-3 mr-3 pa-1"
            style="min-width: 5px"
            :disabled="player == null"
            v-on:click="start()"
          >
            <v-icon dense>mdi-play</v-icon>
          </v-btn>
          <v-btn
            v-else
            class="ml-3 mr-3 pa-1"
            style="min-width: 5px"
            :disabled="player == null"
            v-on:click="pause()"
          >
            <v-icon dense>mdi-pause</v-icon>
          </v-btn>
          <v-btn
            class="ml-0 mr-3 pa-1"
            style="min-width: 5px"
            v-on:click="stop()"
          >
            <v-icon dense>mdi-stop</v-icon>
          </v-btn>
        </div>
      </div>

      <div style="flex: 5">
        <Waveform :url="url" height="64" @setTime="setTime"></Waveform>
      </div>

      <!-- 이 부분부터 ReadOnly -->

      <div class="ml-3" style="flex: 1" v-if="status === 'Master'">
        <!-- dropdown button -->
        <v-btn v-on:click="toggleDropdown()">
          <v-icon v-if="isShow == 0">mdi-chevron-down</v-icon>
          <v-icon v-else>mdi-chevron-up</v-icon>
        </v-btn>
      </div>

      <div style="flex: 1">
        <v-icon
          class="ml-2"
          dense
          @click="sendDelete(n)"
          v-if="status === 'Master'"
          >mdi-delete</v-icon
        >
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
      delay: 0,
      offset: 0,
      currentTime: 0,
      createdAt: 0,
    };
  },
  created() {
    const player = new Tone.Player(this.url, () => {
      this.createdAt = player.now();
      this.player = player;
      console.log(player.toSeconds());
      this.player.onstop = () => {
        console.log("player state: ", this.player.state);
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
      this.gain.object = gain;
      this.player.connect(gain);

      const distortion = new Tone.Distortion(0).toDestination();
      this.distortion.object = distortion;
      this.player.connect(distortion);
    }).toDestination();

    this.status = this.$store.state.status;
    Tone.start(); // ...start()를 실행하기 위한 사전 작업
  },
  methods: {
    start() {
      this.state = "started"; // delay를 줄 경우, player.state로 즉시 받아오면 stopped가 넘어옴
      this.player.unsync();
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
      this.distortion.object.distortion = value;
    },
    changeVolume(value) {
      this.player.volume.value = value;
      console.log(this.player.volume.value);
    },
    changeGain(value) {
      this.gain.object.gain.value = value;
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
    sendDelete(n) {
      this.player.unsync();
      this.player.dispose();
      this.$emit("deleteMusic", n);
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
</style>