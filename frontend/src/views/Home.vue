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
    <MainModal :showModal="showModal" @close="closeModal"/>
    <Footer/>
  </div>
</template>

<script>
import Header from '@/components/common/Header.vue';
import Footer from '@/components/common/Footer.vue';

import MainModal from '@/components/MainModal.vue';

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
      showModal: false
    }
  },
  methods: {
    openModal() {
      this.showModal = true;
    },
    closeModal(close) {
      this.showModal = close;
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