<template>
  <v-card elevation="0" width="100%" height="98%">
    <v-card elevation="0">
      <div
        class="py-3 d-flex justify-space-around nav-color"
        style="border-radius: 0px"
      >
        <v-btn
          text
          :class="expand ? 'select' : 'not-select'"
          @click="showRecord = !showRecord"
          ><v-icon>mdi-microphone-outline</v-icon>
        </v-btn>
        <v-btn
          text
          :class="expand2 ? 'not-select' : 'not-select'"
          @click="file_upload_open"
          ><v-icon>mdi-file-upload-outline</v-icon>
        </v-btn>
      </div>

      <recordBtn
        :showRecord="showRecord"
        @sendData="receiveData"
        ref="recBtn"
      />
      <UploadBtn @sendData="receiveData" ref="fileupload" />
    </v-card>
    <v-divider style="background-color: rgba(255, 255, 255, 0.733)"></v-divider>
    <v-card
      class="overflow-y-auto nav-color"
      style="height: inherit !important; border-radius: 0px 0px 3px 3px"
      v-scroll.self="onScroll"
    >
      <recordCard
        v-for="(item, index) in records"
        :key="item.id"
        v-on:delRecord="delRecord"
        v-on:addRecord="addRecord"
        v-bind:fileData="records[index]"
      />
    </v-card>
  </v-card>
</template> 

<script>
import recordBtn from "./record/recordBtn";
import UploadBtn from "./record/fileupload";
import recordCard from "./record/Audiocard";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import Config from "@/store/config";

export default {
  props: ["page"],
  data: () => {
    return {
      expand: false,
      expand2: false, //
      showRecord: false,
      scrollInvoked: 0,
    };
  },
  computed: {
    records: function () {
      return this.$store.getters.getRecords;
    },
  },
  created() {
    this.idx = this.records.length;

    this.code = document.location.href.split("=")[1];

    this.connect();
  },
  components: {
    recordCard,
    recordBtn,
    UploadBtn,
  },
  methods: {
    send(type, msg) {
      if (type == "record")
        this.recordStompClient.send(
          "/socket/record/" + this.code + "/receive",
          JSON.stringify(msg),
          {}
        );
      else if (type == "music")
        this.musicStompClient.send(
          "/socket/music/" + this.code + "/" + this.page + "/receive",
          JSON.stringify(msg),
          {}
        );
    },
    connect() {
      const serverURL = Config.ServerURL;

      let recordSocket = new SockJS(serverURL);
      this.recordStompClient = Stomp.over(recordSocket);
      this.recordStompClient.connect(
        {},
        (frame) => {
          // 소켓 연결 성공
          this.connected = true;
          console.log("레코드 소켓 연결 성공", frame);

          this.recordStompClient.subscribe(
            "/socket/record/" + this.code + "/send",
            (res) => {
              this.$toasts.success("record toast");
              const resBody = JSON.parse(res.body);

              console.log(resBody);

              if (resBody["type"] == "add")
                this.$store.commit("updateRecord", {
                  fileName: resBody["obj"]["fileName"],
                  downloadURL: resBody["obj"]["url"],
                  id: resBody["index"],
                });
              if (resBody["type"] == "delete")
                this.$store.commit("deleteRecord", resBody.index);
            }
          );
        },
        (error) => {
          // 소켓 연결 실패
          console.log("소켓 연결 실패", error);
          this.connected = false;
        }
      );

      let musicSocket = new SockJS(serverURL);
      this.musicStompClient = Stomp.over(musicSocket);
      this.musicStompClient.connect(
        {},
        (frame) => {
          // // 소켓 연결 성공
          this.connected = true;
          console.log("레코드 소켓 연결 성공", frame);
          this.musicStompClient.subscribe(
            "/socket/music/" + this.code + "/" + this.page + "/send",
            (res) => {
              const resBody = JSON.parse(res.body);
              console.log(resBody);
              if (resBody["type"] == "add") {
                this.$toasts.success("music toast");
                this.$store.commit("addMusic", {
                  page: this.page,
                  record: {
                    fileName: resBody["obj"]["fileName"],
                    downloadURL: resBody["obj"]["url"],
                    id: resBody["index"],
                  },
                });
              }
            }
          );
        },
        (error) => {
          // 소켓 연결 실패
          console.log("소켓 연결 실패", error);
          this.connected = false;
        }
      );
    },

    rec_show() {
      this.$refs.recBtn.show();
    },
    file_upload_open() {
      this.$refs.fileupload.inputClick();
    },

    addCard(data) {
      this.send("record", {
        type: "add",
        index: this.records.length,
        obj: { url: data["downloadURL"], fileName: data["fileName"] },
      });
    },
    receiveData(data) {
      data["id"] = this.idx;
      this.idx += 1;
      this.addCard(data);
      this.expand2 = false;
      this.expand = false;
    },
    delRecord(id) {
      let len = this.records.length;
      for (var i = 0; i < len; i++) {
        if (this.records[i].id === id) {
          this.send("record", {
            type: "delete",
            index: i,
          });
          break;
        }
      }
    },
    addRecord(id) {
      let len = this.records.length;
      for (var i = 0; i < len; i++) {
        if (this.records[i].id === id) {
          this.send("music", {
            type: "add",
            index: this.$store.getters.getBoard(this.page).length,
            obj: {
              url: this.records[i]["downloadURL"],
              fileName: this.records[i]["fileName"],
              distortion: 0,
              gain: 0,
              volume: 0,
              reverb: 0,
            },
          });

          break;
        }
      }
    },
    onScroll() {
      this.scrollInvoked++;
    },
  },
};
</script>


<style>
.select {
  color: red !important;
  font-size: 1.75em !important;
}

.not-select {
  color: white !important;
}
</style>
