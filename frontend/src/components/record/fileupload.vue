<template>
  <div class="mx-auto align-center d-flex justify-center">
    <v-sheet width="80%" class="d-flex align-center">
      <v-file-input
        v-model="files"
        color="deep-purple accent-4"
        class="mt-5"
        multiple
        accept="audio/*"
        outlined
        hide-details
        full-width
        :show-size="1000"
        flat
      >
      </v-file-input
      ><v-btn width="10%" class="mt-5 ml-2" @click="upload"
        ><v-icon>mdi-plus-circle</v-icon></v-btn
      >
    </v-sheet>
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
    async upload() {
      if (this.files === undefined) {
        return;
      }
      for (let i = 0; i < this.files.length; i++) {
        const data = new FormData();
        var date = new Date();
        date = getYyyyMmDdMmSsToString(date);
        const file = { fileName: "", downloadURL: "" };
        data.append("file", this.files[i]);
        await sendfile
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