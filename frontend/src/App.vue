<template>
  <v-app>
    <Nav/>
    <v-main>
      <HelloWorld/>
    </v-main>
  </v-app>
</template>

<script lang="ts">
import Vue from 'vue';
import HelloWorld from './components/HelloWorld.vue';
import Nav from './components/common/Nav2.vue';

export default Vue.extend({
  name: 'App',

  components: {
    HelloWorld,
    Nav
  },

  data: () => ({
    //
  }),
});
</script>
