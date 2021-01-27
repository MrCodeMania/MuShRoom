<template>
    <div class="container">
        <v-row class="my-4">
            <h1>수업</h1>
        </v-row>
        <v-row dense class="mb-10">
            <v-col v-for="card in cards" :key="card.title" cols="6">
                <v-card class="mx-auto my-5" max-width="500">
                    <v-row>
                        <v-col>
                            <v-img :src="card.src" class="white--text align-end"
                                gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)" width="200px" height="200px">
                                <v-card-title v-text="card.title"></v-card-title>
                            </v-img>
                        </v-col>
                        <v-col>
                            <!-- 강사 이름 클릭 시 강사 소개 페이지로 이동 -->
                            <v-card-title v-text="card.tutor"></v-card-title>
                            <v-card-subtitle v-text="card.overview"></v-card-subtitle>
                            <v-rating
                            class="m-3"
                            :value="card.rate"
                            color="amber"
                            dense
                            half-increments
                            readonly
                            size="18"
                            ></v-rating>
                            <v-btn class="ml-4" color="amber">수강신청</v-btn>
                        </v-col>
                    </v-row>             
                </v-card>
            </v-col>
        </v-row>
    </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class LikeClass extends Vue{
    private cards: object[] = [
    { title: '기타로 장범준 따라잡기', src: 'https://cdn.vuetifyjs.com/images/cards/house.jpg', progress: 100, tutor: '김김김', overview: '장범준 기타 내 기타', rate: 5},
    { title: '초보자도 할 수 있는 피아노 반주', src: 'https://cdn.vuetifyjs.com/images/cards/road.jpg', progress: 35, tutor: '이이이', overview: '말할수 없는 비밀 보고 시작했어요', rate: 2},
    { title: '오늘부터 바이올리니스트', src: 'https://cdn.vuetifyjs.com/images/cards/plane.jpg', progress: 50, tutor: '박박박', overview: '헨리 바이올린 딱 기다려', rate: 3.5},
    { title: '드럼의 모든 것', src: 'https://cdn.vuetifyjs.com/images/cards/house.jpg', progress: 65, tutor: '조조조', overview: '드럼을 오래 치면 힘드럼...', rate: 1},
      ];
    
    
}
</script>

<style>

</style>