<template>
  <div class="ar">
    <div class="ar-content">
      <div class="ar-recorder mt-3">
        <icon-button
          class="ar-icon ar-icon__lg"
          :name="iconButtonType"
          :class="{
            'ar-icon--rec': isRecording,
            'ar-icon--pulse': isRecording,
          }"
          @click.native="startRecorder"
        />
        <icon-button
          class="ar-icon ar-icon__sm ar-recorder__stop"
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
      this.$store.commit("setRC", "startMetro");
      if (!this.isRecording || (this.isRecording && this.isPause)) {
        console.log("메트로놈으로 시작신호 보냄");
      }
    },
    stopRecorder() {
      if (!this.isRecording) {
        return;
      }
      this.$store.commit("setRC", "stopMetro");
      this.recorder.stop();
      this.recordList = this.recorder.recordList();
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
        console.log("RECBTN watch", val);
        this.recorder.start();
      }
    },
  },
};
</script>
<style lang="scss">
.ar {
  width: 420px;
  font-family: "Roboto", sans-serif;
  border-radius: 16px;
  background-color: #fafafa;
  box-shadow: 0 4px 18px 0 rgba(0, 0, 0, 0.17);
  position: relative;
  box-sizing: content-box;

  &-content {
    padding: 16px;
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  &-records {
    height: 138px;
    padding-top: 1px;
    overflow-y: auto;
    margin-bottom: 20px;

    &__record {
      width: 320px;
      height: 45px;
      padding: 0 10px;
      margin: 0 auto;
      line-height: 45px;
      display: flex;
      justify-content: space-between;
      border-bottom: 1px solid #e8e8e8;
      position: relative;

      &--selected {
        border: 1px solid #e8e8e8;
        border-radius: 24px;
        background-color: #ffffff;
        margin-top: -1px;
        padding: 0 34px;
      }
    }
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
      position: absolute;
      top: 10px;
      right: -52px;
    }

    &__time-limit {
      position: absolute;
      color: #aeaeae;
      font-size: 12px;
      top: 128px;
    }

    &__records-limit {
      position: absolute;
      color: #aeaeae;
      font-size: 13px;
      top: 78px;
    }
  }

  &-spinner {
    display: flex;
    height: 30px;
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    margin: auto;
    width: 144px;
    z-index: 10;

    &__dot {
      display: block;
      margin: 0 8px;
      border-radius: 50%;
      width: 30px;
      height: 30px;
      background: #05cbcd;
      animation-name: blink;
      animation-duration: 1.4s;
      animation-iteration-count: infinite;
      animation-fill-mode: both;

      &:nth-child(2) {
        animation-delay: 0.2s;
      }

      &:nth-child(3) {
        animation-delay: 0.4s;
      }

      @keyframes blink {
        0% {
          opacity: 0.2;
        }
        20% {
          opacity: 1;
        }
        100% {
          opacity: 0.2;
        }
      }
    }
  }

  &__text {
    color: rgba(84, 84, 84, 0.5);
    font-size: 16px;
  }

  &__blur {
    filter: blur(2px);
    opacity: 0.7;
  }

  &__overlay {
    position: absolute;
    width: 100%;
    height: 100%;
    z-index: 10;
  }

  &__upload-status {
    text-align: center;
    font-size: 10px;
    padding: 2px;
    letter-spacing: 1px;
    position: absolute;
    bottom: 0;

    &--success {
      color: green;
    }

    &--fail {
      color: red;
    }
  }

  &__rm {
    cursor: pointer;
    position: absolute;
    width: 6px;
    height: 6px;
    padding: 6px;
    line-height: 6px;
    margin: auto;
    left: 10px;
    bottom: 0;
    top: 0;
    color: rgb(244, 120, 90);
  }

  &__downloader,
  &__uploader {
    position: absolute;
    top: 0;
    bottom: 0;
    margin: auto;
  }

  &__downloader {
    right: 115px;
  }

  &__uploader {
    right: 85px;
  }
}

@import "../../scss/icons";
</style>