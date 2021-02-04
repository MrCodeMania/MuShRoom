  <template>
  <div>
    <v-sheet
      color="white"
      elevation="1"
      height="100"
      width="100%"
      max-width="400px"
    >
      <div class="align-self-center d-flex justify-space-around">
        <recordBtn class="mt-3" v-on:sendData="receiveData"></recordBtn>
        <div>
          <v-file-input
            v-model="files"
            hide-input
            hide-details
            accept="audio/*"
            prepend-icon="mdi-file"
            @change="selectFile"
          ></v-file-input>
        </div>
      </div>
    </v-sheet>
    <v-sheet
      color="white"
      elevation="1"
      height="100%"
      width="100%"
      max-width="400px"
    >
      <v-card
        class="overflow-y-auto"
        width="90%"
        height="100%"
        max-height="530px"
      >
        <div v-for="(item, index) in records" :key="index">
          <recordCard v-bind:fileData="records[index]" />
        </div>
      </v-card>
    </v-sheet>
  </div>
</template>

<script>
import recordBtn from "./record/recordBtn";
import recordCard from "./record/Audiocard";
import sendfile from "@/service/filecontrol";
import UploaderPropsMixin from "@/mixins/uploader-props";
export default {
  mixins: [UploaderPropsMixin],
  data: () => {
    return {
      scrollInvoked: 0,
      records: [],
      files: [],
      progress: 0,
    };
  },
  components: {
    recordCard,
    recordBtn,
  },
  methods: {
    addCard(data) {
      this.records.push(data);
    },
    receiveData(data) {
      this.addCard(data);
    },
    selectFile(file) {
      this.progress = 0;
      this.currentFile = file;
      this.upload();
    },
    upload() {
      const data = new FormData();
      data.append("file", this.files);

      sendfile
        .send(data)
        .then((result) => {
          const returnData = { url: result.data.fileDownloadUri };
          this.addCard(returnData);
        })
        .catch((err) => {
          console.log("업로드 실패 ㅠㅠ", err);
        });
    },
  },
};
</script>


<style scoped lang="scss">
</style>