<template>
  <div id="qna">
    <v-card
    class="pb-4">
      <v-card
      width="600"
      class="rounded-xl m-4"
      v-for="question in questions"
      :key="question.nickname">
        <v-card-title>
          {{ question.lecture }}
        </v-card-title>
        <v-row
        no-gutters>
          <v-col md="2" class="ml-4">
            <h6>{{ question.nickname }}</h6>
          </v-col>
          <v-col md="4">
            <v-card-text class="p-0">
              {{ question.date }}
            </v-card-text>
          </v-col>
        </v-row>
        <v-divider class="m-1"></v-divider>
        <v-card-text>
          {{ question.content }}
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
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class Qna extends Vue {
  private questions: object[] = [
    { lecture: '1-1강', nickname: '김싸피', content: '강의에서 쓰였던 악보 공유받을 수 있을까요', date: '2021-01-26 22:25'},
    { lecture: '2-3강', nickname: '박싸피', content: '강의에 나온 코드들 표로 정리해주실 수 있나요', date: '2021-01-26 18:30'},
    { lecture: '4-4강', nickname: '이싸피', content: '저는 한손으로 코드가 다 안눌리는데 어떻게 하면 좋을까요;-;', date: '2021-01-26 16:08'},
    { lecture: '1-2강', nickname: '최싸피', content: '저번 강의에서도 렛잇비가 나왔는데 이번 강의랑 무슨 차이인가요?', date: '2021-01-26 09:00'},
  ];
  private page = 1;
}
</script>