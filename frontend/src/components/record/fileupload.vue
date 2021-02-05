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
import UploaderPropsMixin from "@/mixins/uploader-props";
export default {
  mixins: [UploaderPropsMixin],
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
        data.append("file", this.files[i]);
        sendfile
          .send(data)
          .then((result) => {
            console.log(result.data);
          })
          .catch((err) => {
            console.log("업로드 실패 ㅠㅠ", err);
          });
        this.$emit("sendData", this.files[i]);
      }
    },
  },
};
</script>
<style>
</style>