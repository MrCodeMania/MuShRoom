<template>
  <div>
    <span style="color: white">|</span>
    <v-btn dark text @click="changeHelpState">
      <v-icon class="pr-2">mdi-help-circle-outline</v-icon> help
    </v-btn>
  </div>
</template>

<script>
export default {
  data: () => ({}),
  methods: {
    changeHelpState() {
      this.$store.commit("helpShowChange");
    },
  },
};
</script>

<style>
</style>