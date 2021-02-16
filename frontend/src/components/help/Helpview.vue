<template>
  <v-container>
    <v-sheet width="100%" height="420" color="1E1E1E" elevation="20" rounded>
      <v-row no-gutters
        ><v-col :cols="8">
          <v-sheet
            width="100%"
            height="410"
            color="303030"
            rounded
            class="d-flex align-center justify-center mt-1"
          >
            <div>
              <v-img
                max-width="100%"
                max-height="400"
                contain
                :src="src"
              ></v-img></div></v-sheet
        ></v-col>
        <v-divider vertical class="ml-1"></v-divider>
        <v-col>
          <div>
            <v-sheet
              rounded
              width="200"
              color="303030"
              class="d-flex align-center mt-4 ml-1 justify-space-around"
              ><div>
                {{ describe[0] }}
              </div>
            </v-sheet>
            <v-divider class="mt-4"></v-divider>
            <v-sheet width="200" height="300" color="303030" class="mt-1 ml-1"
              ><div class="d-flex align-center justify-center mt-10">
                {{ describe[1] }}
              </div></v-sheet
            >
          </div>
        </v-col>
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
  mounted() {
    console.log(this.describe);
    console.log(this.src);
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