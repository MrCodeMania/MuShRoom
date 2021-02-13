<template>
  <v-card class="musicBoard component-color" elevation="0" width="100%" height="100%">
    <v-card elevation="0">
      <div class="py-3 d-flex justify-space-around nav-color">
        <v-btn text style="font-size: 1.5em;" :class="expand ? 'select' : 'not-select'" @click="expandChange(1)">record </v-btn>
        <v-btn text style="font-size: 1.5em;" :class="expand2 ? 'not-select' : 'not-select'" @click="expandChange(2)">upload </v-btn>
      </div>
      <v-expand-transition>
        <v-card
          style="position: absolute; z-index:99;"
          v-show="expand"
          mode="in-out"
          height="auto"
          width="100%"
          class="mx-auto component-color"
          ><recordBtn @sendData="receiveData" ref="recBtn"
        /></v-card>
      </v-expand-transition>
      <v-expand-transition>
        <v-card
          v-show="expand2"
          mode="out-in"
          height="0"
          width="100%"
          class="mx-auto"
          ><uploadBtn @sendData="receiveData" ref="fileupload"
        /></v-card>
      </v-expand-transition>
    </v-card>
    <v-divider light></v-divider>
    <v-card height="60vh" class="overflow-y-auto nav-color" style="border-radius: 0px 0px 3px 3px;" v-scroll.self="onScroll">
        <recordCard
          v-for="(item, index) in records" :key="item.id"
          v-on:delRecord="delRecord"
          v-on:addRecord="addRecord"
          v-bind:fileData="records[index]"
        />
    </v-card>
  </v-card>
</template> 

<script>
import recordBtn from "./record/recordBtn";
import uploadBtn from "./record/fileupload";
import recordCard from "./record/Audiocard";
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
  props: ["page"],
  data: () => {
    return {
      expand: false,
      expand2: false, // expand data
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
    
    this.code = document.location.href.split('=')[1];

    this.connect();
  },
  components: {
    recordCard,
    recordBtn,
    uploadBtn,
  },
  methods: {
    send(type, msg) {
      if(type == "record")
        this.recordStompClient.send("/socket/record/" + this.code + "/receive", JSON.stringify(msg), {});      
      else if(type == "music")
        this.musicStompClient.send("/socket/music/" + this.code + "/" + this.page + "/receive", JSON.stringify(msg),{});        

    },
     connect() {
      const serverURL = "http://i4a105.p.ssafy.io:8080/";
      
      let recordSocket = new SockJS(serverURL);
      this.recordStompClient = Stomp.over(recordSocket);
      this.recordStompClient.connect(
        {},
        frame => {
          // 소켓 연결 성공
          this.connected = true;
          console.log('레코드 소켓 연결 성공', frame);

          this.recordStompClient.subscribe("/socket/record/" + this.code + "/send", res => {
            const resBody = JSON.parse(res.body);
            
            console.log(resBody);

              if(resBody["type"] == "add")
                this.$store.commit('updateRecord', {fileName : resBody["obj"]["fileName"], downloadURL : resBody["obj"]["url"], id: resBody["index"]});
              if(resBody["type"] == "delete")
                this.$store.commit('deleteRecord', resBody["id"]);
          });
        },
        error => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error);
          this.connected = false;
        }
      );

      let musicSocket = new SockJS(serverURL);
      this.musicStompClient = Stomp.over(musicSocket);
      this.musicStompClient.connect(
        {},
        frame => {
          // 소켓 연결 성공
          this.connected = true;
          console.log('레코드 소켓 연결 성공', frame);

          this.musicStompClient.subscribe("/socket/music/" + this.code + "/" + this.page + "/send", res => {
            const resBody = JSON.parse(res.body);
            
            console.log(resBody);

            if(resBody["type"] == "add")
              this.$store.commit('addMusic', {page : this.page, record : {fileName : resBody["obj"]["fileName"], downloadURL : resBody["obj"]["url"], id: resBody["index"]}});
          });
        },
        error => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error);
          this.connected = false;
        }
      );
    },

    
    rec_expand_close() {
      this.$refs.recBtn.expandInit();
    },
    file_upload_open() {
      this.$refs.fileupload.inputClick();
    },
    expandChange(data) {
      if (data === 1) this.rec_expand_close();
      if (data === 2) this.file_upload_open();

      if (data == 1) {
        this.expand = !this.expand;
        this.expand2 = false;
      }
      if (data == 2) {
        this.expand = false;
        this.expand2 = true;
      }
    },

    addCard(data) {
      this.send("record", {type:"add", index: this.idx - 1, obj: {url : data["downloadURL"], fileName : data["fileName"]}});
    },
    receiveData(data) {
      data["id"] = this.idx;
      this.idx += 1;
      this.addCard(data);
      this.expand2 = false;
    },
    delRecord(id) {
      let len = this.records.length;
      for (var i = 0; i < len; i++) {
        if (this.records[i].id === id) {
          this.send("record", {type: "delete", index: i, obj: {url : this.records[i]["downloadURL"], fileName : this.records[i]["fileName"]}});
          break;
        }
      }
      
    },
    addRecord(id) {
      let len = this.records.length;
      for (var i = 0; i < len; i++) {
        if (this.records[i].id === id) {
          this.send("music", {type: "add", index: i, obj: {url : this.records[i]["downloadURL"], fileName : this.records[i]["fileName"]}});
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
  color: green !important;
  font-size: 1.75em !important;
}

.not-select {
  color: gray !important;
}
</style>
