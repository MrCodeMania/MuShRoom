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
        @click="createRoom">
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
    <Footer/>
  </div>
</template>

<script>
import Header from '@/components/common/Header.vue';
import Footer from '@/components/common/Footer.vue';
import axios from '@/service/axios.service.js';
import Config from '@/store/config'

export default {
  name: 'home',
  components: {
    Header,
    Footer,
    // MainModal
  },
  data: function() {
    return {
      src: require("@/assets/mushroom_v2.png"),
      showModal: false,
      address: ""
    }
  },
  methods: {
    createRoom() {
      axios.post("/data").then(res => {
        console.log([res.data.id.masterId, res.data.id.slaveId]);
        
        this.$store.commit("pushShareUrl", [res.data.id.masterId, res.data.id.slaveId]);
        // 서버 URL 바꿔야 한다
        this.address = Config.HostURL + "/practiceroom?shareUrl=" + res.data.id.masterId;
        this.$router.push({ name: 'PracticeRoom', query: {shareUrl: this.address.split('=')[1]}});
      });     
    },
  }
}
</script>

<style>

.home {
  width:100%;
  height: 72.8vh;
}

</style>