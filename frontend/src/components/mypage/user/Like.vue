<template>
    <div class="container">
        <v-row class="my-4">
                <h1>찜 목록</h1>
        </v-row>
        <v-row>
            <v-tabs>
                <v-tab @click="selectTab">눈여겨본 강의</v-tab>
                <v-tab @click="selectTab">팔로우 중인 강사</v-tab>
            </v-tabs>
        </v-row>

        <v-row>
            <like-class v-if="select" />
            <like-tutor v-else />
        </v-row>
    </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import LikeClass from "@/components/mypage/user/Like/LikeClass.vue";
import LikeTutor from "@/components/mypage/user/Like/LikeTutor.vue";



@Component({
    components: {
        LikeClass, LikeTutor
    }
})
export default class Like extends Vue{

    private select = true;


    private cards: object[] = [
    { title: '기타로 장범준 따라잡기', src: 'https://cdn.vuetifyjs.com/images/cards/house.jpg', progress: 100, tutor: '김김김', overview: '장범준 기타 내 기타', rate: 5},
    { title: '초보자도 할 수 있는 피아노 반주', src: 'https://cdn.vuetifyjs.com/images/cards/road.jpg', progress: 35, tutor: '이이이', overview: '말할수 없는 비밀 보고 시작했어요', rate: 2},
    { title: '오늘부터 바이올리니스트', src: 'https://cdn.vuetifyjs.com/images/cards/plane.jpg', progress: 50, tutor: '박박박', overview: '헨리 바이올린 딱 기다려', rate: 3.5},
    { title: '드럼의 모든 것', src: 'https://cdn.vuetifyjs.com/images/cards/house.jpg', progress: 65, tutor: '조조조', overview: '드럼을 오래 치면 힘드럼...', rate: 1},
      ];
    
    selectTab() {
        this.select = !this.select;
    }

}
</script>

<style>

</style>