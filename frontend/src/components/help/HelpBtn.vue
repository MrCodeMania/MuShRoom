<template>
  <v-btn color="primary" dark="dark" @click="changeHelpState"> help </v-btn>
</template>

<script>
export default {
  data: () => ({}),
  methods: {
    changeHelpState() {
      this.$store.commit("helpShowChange");
      console.log(this.$store.getters.getHelp);
    },
  },
};
</script>

<style>
</style>