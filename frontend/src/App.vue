<template>
  <v-app>
    <Nav/>
    <v-main>
      <router-view/>
    </v-main>
    <Footer/>
  </v-app>
</template>

<script lang="ts">
import Vue from 'vue';
import Nav from './components/common/Nav.vue';
import Footer from './components/common/Footer.vue';

export default Vue.extend({
  name: 'App',

  components: {
    Nav,
    Footer
  },

  data: () => ({
    //
  }),
});
</script>
