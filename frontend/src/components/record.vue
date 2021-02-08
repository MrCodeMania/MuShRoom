  <template>
  <div>
    <v-sheet color="white" height="50" width="100%" rounded>
      <div class="mt-3 mb-3 d-flex justify-space-around">
        <v-btn @click="expandChange(1)">record </v-btn>
        <v-btn @click="expandChange(2)">upload </v-btn>
      </div>
    </v-sheet>
    <v-expand-transition>
      <v-card
        v-show="expand"
        mode="in-out"
        height="130"
        width="100%"
        class="mx-auto"
        ><recordBtn @sendData="receiveData"
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
    <v-sheet color="white" height="100%" width="100%" class="mt-3">
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
export default {
  props: ['page'],
  data: () => {
    return {
      showExpand: false,
      expand: false,
      expand2: false, // expand data
      files: [],
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
  },
  components: {
    recordCard,
    recordBtn,
    uploadBtn,
  },
  methods: {
    expandChange(data) {
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
    }
  },
};
</script>


<style scoped lang="scss">
</style>
