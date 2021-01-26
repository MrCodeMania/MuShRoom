<template>
  <div class="container">
    <h1>회원정보(학생)</h1>
    <v-avatar
    class="profile"
    color="grey"
    size="164"
    tile
    >
      <v-img src="https://cdn.vuetifyjs.com/images/profiles/marcus.jpg"></v-img>
    </v-avatar>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import profileCard from "@/components/common/ProfileCard.vue";


@Component({
  components: {
    profileCard,
  }
})
export default class Info extends Vue{

}
</script>

<style>

</style>