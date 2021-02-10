<template>
  <div>
    <Header/>
    <div class="home">
      <v-card 
      elevation="0"
      class="d-flex align-center justify-center"
      height="100%">
      <v-btn
      icon
      color="white"
      width="200" height="200" 
      x-large
      @click="openModal">
      <v-img
      width="200"
      height="auto"
      :src="src">
      </v-img>
      <v-card
      elevation="0">
        start
      </v-card>
      </v-btn>
      </v-card>
    </div>
    <MainModal :showModal="showModal" :address="address" @close="closeModal"/>
    <Footer/>
  </div>
</template>

<script>
import Header from '@/components/common/Header.vue';
import Footer from '@/components/common/Footer.vue';
import MainModal from '@/components/MainModal.vue';
import axios from '@/service/axios.service.js';

export default {
  name: 'home',
  components: {
    Header,
    Footer,
    MainModal
  },
  data: function() {
    return {
      src: require("@/assets/mushroom.png"),
      showModal: false,
      address: ""
    }
  },
  methods: {
    openModal() {
      this.showModal = true;
      axios.post("/data").then(res => {
        console.log([res.data.id.masterId, res.data.id.slaveId]);
        
        this.$store.commit("pushShareUrl", [res.data.id.masterId, res.data.id.slaveId]);
        // 서버 URL 바꿔야 한다
        this.address = "http://i4a105.p.ssafy.io:8080/practiceroom?shareUrl=" + res.data.id.masterId;
      });
      
    },
    closeModal(close, move) {
      this.showModal = close;
      if(!move) {
        console.log(this.address.toString().split('/')[3].split('=')[1]);
        // delete api 생기면 추가할 것
        // axios.delete("/data/" + this.address.toString().split('/')[3].split('=')[1]).then(res => {
        //     console.log(res);
        // });
      }
    },
  }
}
</script>

<style>

.home {
  width:100vw;
  height: 72.8vh;
}

</style>