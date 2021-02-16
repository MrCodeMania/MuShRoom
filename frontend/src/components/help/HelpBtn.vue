<template>
  <v-btn dark text @click="changeHelpState">
    <v-icon class="pr-2">mdi-help-circle-outline</v-icon> help
  </v-btn>
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