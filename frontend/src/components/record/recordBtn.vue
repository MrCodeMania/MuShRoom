<template>
  <v-container class="record" style="z-index: 100" v-show="showRecord">
    <div width="80px">
      <recBtn
        style="border: none"
        ref="recorder"
        :after-recording="setRecorded"
        :before-recording="startRecord"
        :bit-rate="192"
        :sampleRate="44100"
      />
      <div class="mx-auto">
        <v-card mode="in-out" width="100%" class="component-color">
          <v-sheet
            width="100%"
            height="100%"
            class="d-flex align-center mx-auto justify-center component-color"
          >
            <v-text-field
              background-color="#D7C9B2"
              v-model="inputFileName"
              :rules="filenameRules"
              label="파일이름"
              solo
              hide-details
            ></v-text-field>
            <v-btn icon large @click="upload" class="ml-2"
              ><v-icon large>mdi-plus-circle</v-icon></v-btn
            >
          </v-sheet>
        </v-card>
      </div>
    </div>
  </v-container>
</template>
<script>
import recBtn from "./recBtn.vue";
import sendfile from "@/service/filecontrol";
import { getYyyyMmDdMmSsToString } from "@/lib/timestamp";
export default {
  components: { recBtn },
  props: ["showRecord"],
  data: function () {
    return {
      inputFileName: "",
      //컴포넌트에서 녹화한 파일을 담는 변수
      // blob 형태 {size , type 두가지 정보} , duration 재생길이 , url => 로컬 다운로드 url
      file: {}, // 녹음 완료 후 파일 정보

      startTime: 4,
      filenameRules: [(value) => !!value || "Required."],
    };
  },
  methods: {
    expandInit() {
      this.inputFileName = "";
    },
    async upload() {
      if (this.file === "") return;
      if (this.file.blob === undefined) return;
      const sendFileData = {
        fileName: "",
        downloadURL: "",
      };
      if (this.inputFileName === "") return;
      const data = new FormData();
      sendFileData.fileName = this.inputFileName;
      var date = new Date();
      date = getYyyyMmDdMmSsToString(date);
      data.append(
        "file",
        this.file.blob,
        `${sendFileData.fileName}_${date.toString()}.mp3`
      );
      await sendfile
        .send(data)
        .then((result) => {
          sendFileData.downloadURL = result.data.fileDownloadUri;
        })
        .catch((err) => {
          console.debug("녹음 파일 업로드 실패", err);
        });
      this.$emit("sendData", sendFileData);
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

<style scoped>
.record {
  padding: 0;
  display: flex;
  position: fixed;
  right: 10px;
  width: 250px;
  height: 250px;
  top: 100px;

  border-radius: 2px;
}
</style>