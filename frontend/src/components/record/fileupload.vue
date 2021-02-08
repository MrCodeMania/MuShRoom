<template>
  <div class="d-flex align-center" width="100%">
    <v-file-input
      v-model="files"
      color="deep-purple accent-4"
      class="mt-5"
      multiple
      accept="audio/*"
      outlined
      hide-details
      :show-size="1000"
    >
    </v-file-input
    ><v-btn class="mt-5" @click="upload">추가하기</v-btn>
  </div>
</template>
<script>
import sendfile from "@/service/filecontrol";
import { getYyyyMmDdMmSsToString } from "@/lib/timestamp";
export default {
  data: () => ({
    files: undefined,
  }),
  methods: {
    upload() {
      if (this.files === null) {
        return;
      }
      for (let i = 0; i < this.files.length; i++) {
        const data = new FormData();
        var date = new Date();
        date = getYyyyMmDdMmSsToString(date);
        const file = { fileName: "", downloadURL: "" };
        data.append("file", this.files[i]);
        sendfile
          .send(data)
          .then((result) => {
            file["fileName"] = result.data.fileName;
            file["downloadURL"] = result.data.fileDownloadUri;
            this.$emit("sendData", file);
          })
          .catch((err) => {
            console.log("업로드 실패 ㅠㅠ", err);
          });
      }
    },
  },
};
</script>
<style>
</style>