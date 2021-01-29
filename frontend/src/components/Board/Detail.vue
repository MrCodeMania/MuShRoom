<template>
  <div>
    <v-card class="mb-3">
      <v-system-bar></v-system-bar>
      <v-toolbar flat>
        <v-toolbar-title>{{ number }}번 글 제목.... 글제목</v-toolbar-title>
      </v-toolbar>
      <v-divider></v-divider>
      <v-banner single-line> 작성자 작성일 조회수 들어갈 예정입니다. </v-banner>
      <v-card-text class="grey lighten-4">
        <v-sheet max-width="800" height="300" class="mx-auto"
          >글 내용이 들어갈 예정입니다.</v-sheet
        >
        <v-row class="mt-2">
          <v-col> <v-btn :to="{ name: 'boardlist' }">목록 </v-btn></v-col>
        </v-row>
      </v-card-text>
    </v-card>
    <div class="mb-3">댓글예정</div>
  </div>
</template>
<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
@Component
export default class extends Vue {
  private number = "123";
  private created() {
    console.log(this.$route.params);
    this.number = this.$route.params.articleid;
  }
}
</script>
<style>
</style>