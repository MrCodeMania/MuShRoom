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
    <div v-if="isRoom">
        <Share/>
    </div>
  </v-app-bar>
</template>

<script>
import Share from '../practiceroom/Share.vue'
export default {
    components: { Share },
    data() {
        return {
            logo: require('@/assets/tmpLogo.png'),
            isRoom:false
        }
    },
    created(){
        let Room = document.location.href.split('/');
        // 정확한 URL 나오면 수정 요망
        if(Room[3] == 'practiceroom')
            this.isRoom = true;
        else
            this.isRoom = false;
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