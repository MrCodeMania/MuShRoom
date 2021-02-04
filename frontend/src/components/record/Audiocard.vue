<template>
  <v-menu v-model="showMenu" absolute offset-y style="max-width: 600px">
    <template v-slot:activator="{ on, attrs }">
      <v-card class="mx-auto mt-3" max-width="344" v-bind="attrs" v-on="on">
        <v-card-title>파일이름</v-card-title>
        <v-card-text
          ><v-img
            width="100%"
            height="50px"
            lazy-src="http://www.pngall.com/wp-content/uploads/2/Sound-Waves-PNG.png"
          ></v-img
        ></v-card-text>
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
export default {
  props: ["fileData"],
  data: () => ({
    showMenu: false,
    fileform: "",
  }),
  methods: {
    deleteThis() {
      console.log(this.fileData.url);
    },
    addThis() {
      console.log(this.fileData.url);
    },
  },
  created() {
    this.fileform = this.fileData;
  },
};
</script>

<style scoped >
</style>