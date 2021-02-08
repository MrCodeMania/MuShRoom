<template>
  <div>
    <Header/>
    <div class="home">
      <v-card 
      elevation="0"
      class="d-flex align-center justify-center"
      height="100%">
      <v-btn
      fab
      text
      width="200" height="200" 
      x-large
      :to="{ name: 'PracticeRoom' }">
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
    <Footer/>
  </div>
</template>

<script>
import Header from '@/components/common/Header.vue';
import Footer from '@/components/common/Footer.vue';

export default {
  name: 'home',
  components: {
    Header,
    Footer
  },
  data: function() {
    return {
      src: require("@/assets/mushroom.png")
    }
  }
}
</script>

<style>

.home {
  width:100vw;
  height: 72.8vh;
}

</style>