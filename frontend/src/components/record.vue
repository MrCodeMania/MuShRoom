  <template>
  <div>
    <v-sheet color="white" height="50" width="100%" rounded>
      <div class="mt-5 mb-3 d-flex justify-space-around ">
        <v-btn text style="font-size: 1.5em;" :class="expand ? 'select' : 'not_select'" @click="expandChange(1)">record </v-btn>
        <v-btn text style="font-size: 1.5em;" :class="expand2 ? 'select' : 'not_select'" @click="expandChange(2)">upload </v-btn>
      </div>
    </v-sheet>
    <v-expand-transition>
      <v-card
        v-show="expand"
        mode="in-out"
        height="130"
        width="100%"
        class="mx-auto"
        ><recordBtn @sendData="receiveData" ref="recBtn"
      /></v-card>
    </v-expand-transition>
    <v-expand-transition>
      <v-card
        v-show="expand2"
        mode="out-in"
        height="100"
        width="100%"
        class="mx-auto"
        ><uploadBtn @sendData="receiveData"
      /></v-card>
    </v-expand-transition>
    <v-sheet color="white" height="100%" width="100%" class="mt-3 component-color">
      <v-card
        elevation="0"
        class="overflow-y-auto"
        width="100%"
        height="100%"
        max-height="50vh"
      >
        <div v-for="(item, index) in records" :key="item.id">
          <recordCard
            v-on:delRecord="delRecord"
            v-on:addRecord="addRecord"
            v-bind:fileData="records[index]"
          />
        </div>
      </v-card>
    </v-sheet>
  </div>
</template> 

<script>
import recordBtn from "./record/recordBtn";
import uploadBtn from "./record/fileupload";
import recordCard from "./record/Audiocard";
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
  props: ['page'],
  data: () => {
    return {
      expand: false,
      expand2: false, // expand data
      idx: 0,
    };
  },
  computed: {
    records: function() {
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
    send(msg) {
      if (this.stompClient && this.stompClient.connected) {
        this.stompClient.send("/socket/record/"+this.code+"/receive", JSON.stringify(msg));        
      }
    },
     connect() {
      const serverURL = "http://i4a105.p.ssafy.io:8080/";
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect(
        {},
        frame => {
          // 소켓 연결 성공
          this.connected = true;
          console.log('소켓 연결 성공', frame);

          this.stompClient.subscribe("/socket/record/"+this.code+"/send", res => {
            console.log('구독으로 받은 메시지 입니다.');
            console.log(JSON.parse(res.body));
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
    expandChange(data) {
      if (data === 1) this.rec_expand_close();
      if (this.showExpand === true) {
        if (this.expand == true && data === 1) {
          this.expand = false;
          this.showExpand = false;
        } else if (this.expand === false && data === 1) {
          this.expand = true;
          this.expand2 = false;
          this.showExpand = true;
        } else if (this.expand2 === true && data === 2) {
          this.expand2 = false;
          this.showExpand = false;
        } else if (this.expand2 === false && data == 2) {
          this.expand2 = true;
          this.expand = false;
          this.showExpand = true;
        }
      } else {
        if (data === 1) {
          this.showExpand = true;
          this.expand = true;
        } else {
          this.showExpand = true;
          this.expand2 = true;
        }
      }
    },
    addCard(data) {
      this.$store.commit('updateRecord', data);
      this.send({type:"add", index: this.idx - 1, obj: {url : data["downloadURL"], fileName : data["fileName"]}});
    },
    receiveData(data) {
      data["id"] = this.idx;
      this.idx += 1;
      console.log(data);
      this.addCard(data);
    },
    delRecord(id) {
      var idx = 0, len = this.records.length;
      for (var i = 0; i < len; i++) {
        if (this.records[i].id === id) {
          idx = i;
          break;
        }
      }
      this.$store.commit('deleteRecord', idx);
      this.send({type: "delete", index: idx});
    },
    addRecord(id) {
      var record = {}, len = this.records.length, page = this.page;
      for (var i = 0; i < len; i++) {
        if (this.records[i].id === id) {
          record = this.records[i];
          break;
        }
      }
      this.$store.commit('addMusic', {
          page, record
        });

      // Page별 MusicList 소켓 완료되면 test
      // this.send({type:"add", index: this.idx - 1, obj: {url : data["downloadURL"], fileName : data["fileName"]}}, data);
    }
    
  },
};
</script>


<style>

.select {
  color: green !important;
  font-size: 1.75em !important;
}

.not_select {
  color: gray !important;
}


</style>
