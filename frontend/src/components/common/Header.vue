<template>
  <v-app-bar
  color="light-green lighten-1"
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
     <Share/>
  </v-app-bar>
</template>

<script>
import Share from '../practiceroom/Share.vue'
export default {
  components: { Share },
    data() {
        return {
            logo: require('@/assets/tmpLogo.png')
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
.share-component{
    
}
</style>