<template>
  <div>
    <div>
      <v-sheet width="600px" class="mx-auto"
        ><router-view :key="$route.fullPath"></router-view
      ></v-sheet>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
@Component
export default class Board extends Vue {
  name = "Board";
}
</script>
<style>
</style>