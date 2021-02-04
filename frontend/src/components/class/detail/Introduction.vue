<template>
  <div id="intro">
      <v-card
      class="mt-2">
        <v-card-text>
          <h1 class="mb-10">
            {{ intro.subtitle }}
          </h1>
          <h4 class="mb-5">
            {{ intro.description }}
          </h4>
        </v-card-text>
        <v-img
        :src="intro.img"
        class="mb-5"
        alt="">
        </v-img>
      </v-card>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import ClassDataService from '@/service/ClassDataService';

@Component
export default class Introduction extends Vue {
  private cid = this.$route.params.cid;   // 조회하려는 클래스 id
  private intro: {[key: string]: any} = {}; // 강의 소개
  
  created() {
    // 강의 소개 조회
    ClassDataService.getOneClass(this.cid)
    .then((response) => {
        this.intro = response.data.profile.intro;
    }).catch((error) => {
        console.log(error);
    });
  }
}
</script>