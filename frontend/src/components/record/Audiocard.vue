<template>
  <div>
    <v-card
      elevation="0"
      outlined
      class="mx-auto mt-2 smallcomponent-color"
      width="90%"
      height="auto"
      style="border: 4px solid rgb(61, 61, 61); border-radius: 5px;"
    >
      <v-card-title width="50%" max-width="60px"
        ><span class="ml-2 medium">{{
          fileData.fileName
        }}</span>
        <v-spacer></v-spacer>
        <v-btn icon dark plain @click="addThis"
          ><v-icon>mdi-plus </v-icon></v-btn
        >
        <v-btn class="mr-2" plain icon dark @click="deleteThis"
          ><v-icon>mdi-delete</v-icon></v-btn
        >
      </v-card-title>

      <Waveform class="mx-auto none" :url="fileData.downloadURL" height="60" full="true" color="#999"/>
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

.v-card__subtitle, .v-card__text, .v-card__title {
    padding: 0px;
}



</style>