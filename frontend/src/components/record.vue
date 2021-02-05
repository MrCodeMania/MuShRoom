  <template>
  <div>
    <v-sheet color="white" height="100" width="100%"
    rounded>
      <div class="align-self-center d-flex justify-space-around align-center">
        <recordBtn class="mt-3" v-on:sendData="receiveData"></recordBtn>
        <div>
          <v-btn
            width="50px"
            height="50px"
            class="text-none mb-5"
            rounded
            depressed
            :loading="isSelecting"
            @click="onButtonClick"
            ><v-icon size="50px">mdi-file </v-icon></v-btn
          >
          <input
            ref="uploader"
            class="d-none"
            type="file"
            accept="audio/*"
            @change="selectFile"
          />
        </div>
      </div>
    </v-sheet>
    <v-sheet color="white" height="100%" width="100%">
      <v-card
        elevation="0"
        class="overflow-y-auto"
        width="100%"
        height="100%"
        max-height="50vh"
      >
        <div v-for="(item, index) in records" :key="item.id">
          <recordCard
            v-on:delList="delRecord"
            v-bind:fileData="records[index]"
          />
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
      isSelecting: false,
      records: [],
      files: [],
      idx: 0,
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
      data["id"] = this.idx;
      this.idx += 1;
      this.addCard(data);
    },
    selectFile(e) {
      this.files = e.target.files[0];
      this.upload();
    },
    upload() {
      if (this.files === null) {
        return;
      }
      const data = new FormData();
      data.append("file", this.files);
      sendfile
        .send(data)
        .then((result) => {
          const returnData = { url: result.data.fileDownloadUri };
          returnData["id"] = this.idx;
          returnData["fileName"] = result.data.fileName;
          this.idx += 1;
          this.addCard(returnData);
        })
        .catch((err) => {
          console.log("업로드 실패 ㅠㅠ", err);
        });
    },
    onButtonClick() {
      this.isSelecting = true;
      window.addEventListener(
        "focus",
        () => {
          this.isSelecting = false;
        },
        { once: true }
      );

      this.$refs.uploader.click();
    },
    delRecord(data) {
      var idx = 0;
      for (var i = 0; i < this.records.length; i++) {
        if (this.records[i].id === data) {
          idx = i;
          break;
        }
      }
      this.records.splice(idx, 1);
    },
  },
};
</script>


<style scoped lang="scss">
</style>
