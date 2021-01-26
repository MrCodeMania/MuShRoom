<template>
  <v-app>
    <Nav/>
    <router-view/>
  </v-app>
</template>

<script lang="ts">
import Vue from 'vue';
import Nav from './components/common/Nav2.vue';

export default Vue.extend({
  name: 'App',

  components: {
    Nav
  },

  data: () => ({
    //
  }),
});
</script>
