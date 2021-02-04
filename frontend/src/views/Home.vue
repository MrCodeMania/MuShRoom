<template>
  <div>
    <Header/>
    <div class="home" >
      <v-btn width="200" height="200" :to="{ name: 'PracticeRoom' }">
        to practiceroom
      </v-btn>

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
      img: require("@/assets/homebg.jpg")
    }
  }
}
</script>

<style>

.home {
  width:100vw;
  height: 100vh;
}

</style>