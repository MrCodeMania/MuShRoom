<template>
  <v-app-bar
  color="#00ff0000"
  tile
  flat
  rounded
  height="auto"
  >
    <v-img
      max-width="180"
      height="50"
      :src="logo"
      @click="toMain">
    </v-img>
    <v-spacer></v-spacer>
    <Share/>
  </v-app-bar>
</template>

<script>
import Share from '../practiceroom/Share.vue'
export default {
    components: { Share },
    data() {
        return {
            logo: require('@/assets/tmpLogo.png'),
        }
    },
    methods: {
        toMain() {
            this.$router.push({name : 'Home'})
        }
    }

}
</script>

<style>
</style>