<!--이거로 이름 변경예정-->
<template>
  <div>
    <div width="80px">
      <recBtn
        style="border: none"
        ref="recorder"
        :after-recording="setRecorded"
        :before-recording="startRecord"
        :bit-rate="192"
      />
      <v-sheet class="d-flex align-center" width="100%" height="100%">
        <v-text-field
          v-model="fileName"
          :rules="filenameRules"
          label="파일이름"
          solo
          hide-details
        ></v-text-field>
        <v-btn @click="upload">업로드</v-btn>
      </v-sheet>
    </div>
  </div>
</template>
<script>
import recBtn from "./recBtn.vue";
import UploaderPropsMixin from "@/mixins/uploader-props";
import sendfile from "@/service/filecontrol";

export default {
  mixins: [UploaderPropsMixin],
  components: { recBtn },
  data: function () {
    return {
      //컴포넌트에서 녹화한 파일을 담는 변수
      // blob 형태 {size , type 두가지 정보} , duration 재생길이 , url => 로컬 다운로드 url
      file: "",
      fileName: "",
      fineNum: 0,
      filenameRules: [(value) => !!value || "Required."],
      fileUploadCheck: false,
    };
  },
  methods: {
    upload() {
      if (!this.fileName) {
        return;
      }
      if (!this.file.url) {
        return;
      }
      const data = new FormData();
      data.append(
        "file",
        this.file.blob,
        `${this.$store.state.myName}${this.fileName} ${this.fineNum}.mp3`
      );
      this.fineNum++;
      sendfile
        .send(data)
        .then((result) => {
          this.file.url = result.data.fileDownloadUri;
          console.log(result.data);
        })
        .catch((err) => {
          console.log("녹음 파일 업로드 실패", err);
        });
      this.$emit("sendData", this.file);
    },

    hideStopBtn() {
      const $stopBtn = this.$refs.recorder.$el.querySelector(
        ".ar-recorder__stop"
      );
      $stopBtn.style.display = "none";
    },
    showStopBtn() {
      const $stopBtn = this.$refs.recorder.$el.querySelector(
        ".ar-recorder__stop"
      );
      $stopBtn.style.display = "block";
    },
    setRecentRecord() {
      const recorder = this.$refs.recorder;
      if (recorder) {
        const top = recorder.recordList.length - 1;
        recorder.selected = recorder.recordList[top];
        this.file = recorder.selected;
        this.file["fileName"] = `${this.fileName} ${this.fineNum}`;
        //파일 데이터를 전송해줍니다.
      }
    },
    // :after-recording
    setRecorded() {
      this.hideStopBtn();
      setTimeout(() => {
        this.setRecentRecord();
      }, 800);
    },

    //:before-recording=
    startRecord() {
      this.showStopBtn();
    },
  },
};
</script>

<style scoped  lang="scss">
::v-deep .ar-content {
  padding: 1px;
  background-color: white;
}

::v-deep .ar-player__play {
  fill: white !important;
  background-color: #171003 !important;

  &.ar-player__play--active {
    background-color: #171003 !important;
  }
}

::v-deep .ar-player__play {
  fill: white !important;
  background-color: #ff6b64 !important;
  cursor: inherit;

  &.ar-player__play--active {
    background-color: #ff6b64 !important;
  }
}

::v-deep .ar-icon {
  border: none;
  box-shadow: 0 2px 5px 1px rgba(158, 158, 158, 0.5);
}

::v-deep .ar-icon__lg {
  width: 38px;
  height: 38px;
}

::v-deep svg {
  vertical-align: baseline;
}

::v-deep div.ar {
  margin: auto;
  width: 100%;
  max-width: 510px;
  box-shadow: 0 0.75rem 1.5rem rgba(18, 38, 63, 0.03);
  background-color: #fff;
  border: 1px solid #eff2f7;
  border-radius: 0.375rem;
}

::v-deep .ar-player {
  display: none;
  width: 100%;
}

/* disalbed 처리 */
::v-deep .ar-player {
  opacity: 0.5;
  cursor: default;
  &.abled {
    opacity: 1;
    cursor: pointer;
  }
}

::v-deep .ar-player__time {
  width: 3.2rem;
  margin: 0 0.4rem;
}

::v-deep .ar-records {
  display: none;
}

::v-deep .ar-records__record {
  min-width: 250px;
}

::v-deep .ar-recorder__duration {
  font-size: 1.3rem;
  margin: 0.3rem 0 0 0;
}

::v-deep .ar-player-actions {
  width: 50px;
  justify-content: center;
}

::v-deep .ar-player > .ar-player-bar > .ar-player__progress {
  max-width: 110px;
}

/* 중지 버튼 레코딩 버튼과 겹치기 */

::v-deep .ar-recorder__stop {
  fill: white !important;
  background-color: #ff6b64 !important;
  top: 0;
  right: 0;
  width: 38px;
  height: 38px;
  display: none;
}
</style>