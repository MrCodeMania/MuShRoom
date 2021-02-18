<template>
  <v-container>
    <v-sheet height="420" color="black" elevation="20" rounded>
      <v-row no-gutters class="d-flex flex-column justify-center">
        <v-sheet
          height="340"
          color="black"
          rounded
          class="d-flex align-center justify-center"
        >
          <div>
            <v-img
              max-height="320"
              max-width="420"
              contain
              :src="src"
            ></v-img></div
        ></v-sheet>
        <v-divider class="mt-4"></v-divider>
        <div class="d-flex justify-center align-center flex-column mt-6">
          <v-sheet width="100%" color="black"
            ><div
              class="d-flex align-center justify-center"
              v-html="describe[0]"
            ></div
          ></v-sheet>
        </div>
      </v-row>
    </v-sheet>
  </v-container>
</template>

<script>
export default {
  props: {
    describe: Array,
    src: String,
  },
  methods: {
    changeHelpState() {
      this.$store.commit("helpShowChange");
    },
  },
};
</script>

<style>
</style>