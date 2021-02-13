<template>
  <div class="home_background">
    <Header/>
    <div class="home">
      <v-card 
      color="#00ff0000"
      elevation="0"
      class="d-flex align-center justify-center"
      height="100%">
        <v-avatar
        rounded="circle"
        color="rgba( 255, 255, 255, 0.1 )"
        size="300">
          <v-btn
        icon
        color="#00ff0000"
        width="200" height="200" 
        x-large
        @click="openModal">
          <v-img
          width="200"
          height="auto"
          :src="src">
          </v-img>
          <v-card
          color="#00ff0000"
          elevation="0">
            <v-card-title>
              start
            </v-card-title>
          </v-card>
        </v-btn>
        </v-avatar>
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
      src: require("@/assets/mushroom_v2.png"),
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
        
        axios.delete("/data/" + this.address.toString().split('/')[3].split('=')[1]).then(res => {
            console.log(res);
        });
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