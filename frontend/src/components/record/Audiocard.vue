<template>
  <div>
    <v-card
      elevation="0"
      outlined
      class="mx-auto mt-1"
      width="90%"
      height="100px"
    >
      <v-card-title width="50%" max-width="60px"
        ><span class="title font-weight-medium ml-2">{{
          fileData.fileName
        }}</span>
        <v-spacer></v-spacer>
        <v-btn icon dark color="indigo" @click="addThis"
          ><v-icon dark> mdi-plus </v-icon></v-btn
        >
        <v-btn class="mr-2 ml-1" icon dark color="error" @click="deleteThis"
          ><v-icon dark>mdi-delete</v-icon></v-btn
        >
      </v-card-title>

      <v-card-text class="none"
        ><Waveform :url="fileData.downloadURL" height="64"
      /></v-card-text>
    </v-card>
  </div>
</template>
<script>
import Waveform from "../practiceroom/Waveform";
export default {
  props: ["fileData"],
  components: {
    Waveform,
  },
  data: () => ({
    showMenu: false,
  }),
  methods: {
    deleteThis() {
      this.$emit("delRecord", this.fileData.id);
    },
    addThis() {
      this.$emit("addRecord", this.fileData.id);
    },
  },
};
</script>

<style scoped >
.none {
  pointer-events: none;
}
</style>