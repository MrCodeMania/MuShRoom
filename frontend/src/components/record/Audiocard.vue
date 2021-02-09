<template>
  <v-menu v-model="showMenu" absolute offset-y style="max-width: 600px">
    <template v-slot:activator="{ on, attrs }">
      <v-card
        elevation="0"
        outlined
        class="mx-auto mt-1"
        width="90%"
        height="100px"
        v-bind="attrs"
        v-on="on"
      >
        <v-card-title width="50%" max-width="60px"
          ><span class="title font-weight-light">{{
            fileData.fileName
          }}</span></v-card-title
        >
        <v-card-text><Waveform :url="fileData.downloadURL" height="64" /></v-card-text>
      </v-card>
    </template>
    <v-list>
      <v-btn class="mx-2" fab dark color="indigo" @click="addThis"
        ><v-icon dark> mdi-plus </v-icon></v-btn
      >
      <v-btn class="mx-2" fab dark color="error" @click="deleteThis"
        ><v-icon dark>mdi-delete</v-icon></v-btn
      >
    </v-list>
  </v-menu>
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
</style>