<template>
  <div id="intro">
      <v-card
      class="mt-2">
        <v-card-text>
          <h1 class="mb-10">{{ describe.title }}</h1>
          <h4 
          v-for="(str, idx) in describe.desc"
          :key="str"
          class="mb-5"
          >
          <v-icon v-if="idx != 0">
            mdi-check-bold
          </v-icon>
          {{ str }}</h4>
        </v-card-text>
        <v-img v-for="img in describe.img"
        :key="img"
        :src="img"
        class="mb-5"
        alt="">
        </v-img>
      </v-card>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class Introduction extends Vue {
  private describe = {
    title : '입문자를 위한 코드부터 차근차근 시작하는 강의입니다!',
    desc : ['이런 분들에게 추천합니다.', '한 곡 완주가 목표이신 분들', '악보 보기 어려우신 분들', '악기가 배우고 싶은 분들', '이걸 보고계신 분들'],
    img : ['https://cdn.vuetifyjs.com/images/parallax/material.jpg', 'https://cdn.vuetifyjs.com/images/parallax/material2.jpg']
    };
}
</script>