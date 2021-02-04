<template>
  <v-app-bar
  color="light-green lighten-1"
  tile
  flat
  rounded
  height="auto">
      <v-img
      max-width="180"
      height="50"
      :src="logo">
      </v-img>
  </v-app-bar>
</template>

<script>
export default {
    data() {
        return {
            logo: require('@/assets/tmpLogo.png')
        }
    }

}
</script>

<style>
</style>