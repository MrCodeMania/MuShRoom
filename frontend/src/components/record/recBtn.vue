<template>
  <div class="ar">
    <div class="ar-content component-color">
      <div class="ar-recorder mt-5">
        <icon-button
          style="background-color: #d7c9b2"
          class="ar-icon ar-icon__lg"
          :name="iconButtonType"
          :class="{
            'ar-icon--rec': isRecording,
            'ar-icon--pulse': isRecording,
          }"
          @click.native="startRecorder"
        />
        <icon-button
          class="ar-icon ar-icon__lg ar-recorder__stop"
          name="stop"
          @click.native="stopRecorder"
        />
      </div>
      <div class="ar-recorder__duration">{{ recordedTime }}</div>
    </div>
  </div>
</template>

<script>
import IconButton from "./icon-button";
import Recorder from "@/lib/recorder";
import UploaderPropsMixin from "@/mixins/uploader-props";
import { convertTimeMMSS } from "@/lib/utils";
export default {
  mixins: [UploaderPropsMixin],
  props: {
    attempts: { type: Number },
    time: { type: Number },
    bitRate: { type: Number, default: 192 },
    sampleRate: { type: Number, default: 44100 },

    micFailed: { type: Function },
    beforeRecording: { type: Function },
    pauseRecording: { type: Function },
    afterRecording: { type: Function },
    failedUpload: { type: Function },
    beforeUpload: { type: Function },
    successfulUpload: { type: Function },
    selectRecord: { type: Function },
  },
  data() {
    return {
      recorder: this._initRecorder(),
      recordList: [],
      selected: {},
    };
  },
  beforeDestroy() {
    this.stopRecorder();
  },
  components: {
    IconButton,
  },
  methods: {
    startRecorder() {
      if (this.attempts && this.recorder.records.length >= this.attempts) {
        return;
      }

      if (!this.isRecording || (this.isRecording && this.isPause)) {
        this.$store.commit("setRC", "startMetro");
      }
    },
    stopRecorder() {
      if (!this.isRecording) {
        return;
      }
      this.$store.commit("setRC", "stopMetro");
    },
    _initRecorder() {
      return new Recorder({
        beforeRecording: this.beforeRecording,
        afterRecording: this.afterRecording,
        pauseRecording: this.pauseRecording,
        micFailed: this.micFailed,
        bitRate: this.bitRate,
        sampleRate: this.sampleRate,
        format: this.format,
      });
    },
  },
  computed: {
    iconButtonType() {
      return this.isRecording && this.isPause
        ? "mic"
        : this.isRecording
        ? "pause"
        : "mic";
    },
    isPause() {
      return this.recorder.isPause;
    },
    isRecording() {
      return this.recorder.isRecording;
    },
    recordedTime() {
      if (this.time && this.recorder.duration >= this.time * 60) {
        this.stopRecorder();
      }
      return convertTimeMMSS(this.recorder.duration);
    },
    getRC() {
      return this.$store.getters.getRC;
    },
  },

  watch: {
    getRC(val) {
      if (val === "startRecord") {
        this.recorder.start();
      } else if (val === "stopRecord") {
        this.recorder.stop();
        this.recordList = this.recorder.recordList();
      }
    },
  },
};
</script>
<style lang="scss">
.ar {
  width: 100%;
  font-family: "Roboto", sans-serif;
  border-radius: 16px;
  background-color: #fafafa;
  box-shadow: 0 4px 18px 0 rgba(0, 0, 0, 0.17);
  position: relative;
  box-sizing: content-box;

  &-content {
    padding: 1px;
    background-color: white;
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  &-recorder {
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: center;

    &__duration {
      color: #aeaeae;
      font-size: 32px;
      font-weight: 500;
      margin-top: 20px;
      margin-bottom: 16px;
    }

    &__stop {
      fill: white !important;
      background-color: #ff6b64 !important;
      position: absolute;
      width: 38px;
      height: 38px;
      display: none;
    }
  }
}

@import "../../scss/icons";
</style>