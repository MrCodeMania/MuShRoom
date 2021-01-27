<template>
  <div id="review">
    <v-card class="pb-4">
      <v-container>
        <v-row 
        v-for="(score, idx) in scores"
        :key="score"
        no-gutters>
          <v-col md="1">
            <v-card-text
            class="p-0 text-center">
              {{idx+1}} 점
            </v-card-text>
          </v-col>
          <v-col md="6">
            <v-progress-linear 
            :value="score"
            height="25"
            color="amber darken-4"
            >
              <strong>{{ Math.ceil(score) }}%</strong>
            </v-progress-linear>
          </v-col>
        </v-row>
      </v-container>
      <v-card
      v-if="isTaken"
      width="600"
      class="rounded-xl m-4">
        <v-card-title class="pl-4 p-0">
          <v-rating
          :empty-icon="empty"
          :full-icon="full"
          :half-icon="half"
          length="5"
          size="16"
          dense
          background-color="amber"
          color="amber darken-4"
          :value="starValue"
          ></v-rating>
        </v-card-title>
        <v-row
        no-gutters>
          <v-col md="2" class="ml-4">
            <h6>{{ user.nickname }}</h6>
          </v-col>
        </v-row>
        <v-divider class="m-1"></v-divider>
        <v-card-text>
          <v-textarea
          placeholder="후기를 작성해주세요"
          outlined
          v-model="content"
          ></v-textarea>
          <v-btn
          dark
          color="amber darken-4">
            작성
          </v-btn>
        </v-card-text>
      </v-card>
      <v-card
      width="600"
      class="rounded-xl m-4"
      v-for="review in reviews"
      :key="review.nickname">
        <v-card-title class="pl-4 p-0">
          <v-rating
          :empty-icon="empty"
          :full-icon="full"
          :half-icon="half"
          length="5"
          size="16"
          readonly
          dense
          background-color="amber"
          color="amber darken-4"
          :value="review.value"
          ></v-rating>
        </v-card-title>
        <v-row
        no-gutters>
          <v-col md="2" class="ml-4">
            <h6>{{ review.nickname }}</h6>
          </v-col>
          <v-col md="4">
            <v-card-text class="p-0">
              {{ review.date }}
            </v-card-text>
          </v-col>
        </v-row>
        <v-divider class="m-1"></v-divider>
        <v-card-text>
          {{ review.content }}
        </v-card-text>
      </v-card>
      <v-row>
        <v-col md=8>
          <v-pagination
          v-model="page"
          :length="10"
          circle
          ></v-pagination>
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';

@Component
export default class Review extends Vue {
  @Prop(Boolean) isTaken!: boolean;
  private empty = 'mdi-star-outline';
  private full = 'mdi-star';
  private half = 'mdi-star-half-full';
  private scores: number[] = [2, 8, 15, 60, 10]; 
  private reviews: object[] = [
    { nickname: '김싸피', value: 5, content: '최고의 강의! 추천합니다.', date: '2021-01-26 22:25'},
    { nickname: '박싸피', value: 4.5, content: '유익한 내용, 간결한 설명', date: '2021-01-26 18:30'},
    { nickname: '이싸피', value: 5, content: '머쉬룸 최고의 강의가 아닐까 싶네요..', date: '2021-01-26 16:08'},
    { nickname: '최싸피', value: 3.5, content: '무난하고 초보자도 들을만 했어요', date: '2021-01-26 09:00'},
  ];
  private page = 1;
  private user: object = {
    nickname: '홍싸피'
  }
  private starValue = 0;
  private content = '';
}
</script>