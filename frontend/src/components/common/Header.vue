<template>
  <v-app-bar
  color="black"
  tile
  flat
  class="pt-2 pb-1"
  rounded
  height="auto"
  >
    <v-btn
      text       
      max-width="180"
      height="50" 
      @click="toMain">
      <v-img
        max-width="180"
        height="50"
        :src="logo">
      </v-img>
    </v-btn>
    <v-spacer></v-spacer>
    <v-btn
    dark
    text
    @click="showChat()"
    v-if="!openChat">
      <v-icon
      class="pr-2"
      dark>
        mdi-chat
      </v-icon>
      Chat
    </v-btn>
    <span style="color: white;">|</span>
    <Share/>
  </v-app-bar>
</template>

<script>
import Share from '../practiceroom/Share.vue'
export default {
    components: { Share },
    props: {
      openChat: Boolean
    },
    data() {
        return {
            logo: require('@/assets/tmpLogo.png'),
        }
    },
    methods: {
        toMain() {
            this.$router.push({name : 'Home'})
        },

        showChat() {
          this.$emit("showChat");
        }
    }

}
</script>

<style>
</style>