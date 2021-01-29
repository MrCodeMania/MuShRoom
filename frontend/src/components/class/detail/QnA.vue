<template>
  <div id="qna">
    <v-card
    class="pb-4 pt-4">
      <v-card
      width="800"
      class="px-4"
      elevation="0"
      v-for="question in questions"
      :key="question.nickname">
        <v-card
        width="600"
        class="rounded m-4">
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
        <v-card 
        v-if="question.comment != ''"
        width="600"
        color="amber lighten-4"
        class="rounded m-4 p-4">
          <v-icon>
            mdi-arrow-right-bold
          </v-icon>
            {{ question.comment }}
          <div 
          v-if="isTutor"
          class="mt-4 text-end">
            <v-btn
            dark
            color="amber darken-4"
            class="mr-4">
              수정
            </v-btn>
            <v-btn
            dark
            color="amber darken-4">
              삭제
            </v-btn>
          </div>
        </v-card>
        <v-card 
        v-else-if="isTutor"
        width="600"
        color="amber lighten-4"
        class="rounded m-4 p-4">
          <v-icon>
            mdi-arrow-right-bold
          </v-icon>
          <v-textarea
          class="mt-1"
          outlined
          placeholder="답변을 남겨주세요"
          v-model="answer">
          </v-textarea>
          <div
          class="text-end">
            <v-btn
            dark
            color="amber darken-4"
            @click="writeAnswer">
              작성
            </v-btn>
          </div>
        </v-card>
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
    { nickname: '김싸피', content: '강의에서 쓰였던 악보 공유받을 수 있을까요', 
    comment: '', date: '2021-01-26 22:25'},
    { nickname: '박싸피', content: '강의에 나온 코드들 표로 정리해주실 수 있나요',
    comment: 'https://blog.cheolsooblog.com/materials로 오시면 볼 수 있습니다^^!', date: '2021-01-26 18:30'},
    { nickname: '이싸피', content: '비틀즈 말고 다른 가수 노래는 안하시나요',
    comment: 'https://blog.cheolsooblog.com/schedule에 강의 계획 올려뒀어요^^', date: '2021-01-26 16:08'},
    { nickname: '최싸피', content: '혹시 선생님은 교포이신가요', 
    comment: '첫 강의에 소개했었는데, 네 맞습니다^^', date: '2021-01-26 09:00'},
  ];
  private page = 1;
  private isTutor = true;
  private answer = '';

  writeAnswer(){
    alert('작성되었습니다');
    this.answer = '';
  }
}
</script>