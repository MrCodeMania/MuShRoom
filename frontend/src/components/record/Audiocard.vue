<template>
  <v-menu v-model="showMenu" absolute offset-y style="max-width: 600px">
    <template v-slot:activator="{ on, attrs }">
      <v-card
      elevation="0"
      outlined
      class="mx-auto mt-1" width="90%" v-bind="attrs" v-on="on">
        <v-card-title>{{ fileData.fileName }}</v-card-title>
        <v-card-text><Waveform :url="fileData.url" height="64" /></v-card-text>
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
    fileform: "",
  }),
  methods: {
    deleteThis() {
      this.$emit("delList", this.fileData.id);
    },
    addThis() {
      this.$store.commit("pushURL", this.fileData.url);
    },
  },
  created() {
    this.fileform = this.fileData;
  },
};
</script>

<style scoped >
</style>