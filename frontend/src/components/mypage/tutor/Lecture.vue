<template>
    <div class="container">
        <v-row class="my-4">
            <h1>내 강의(강사)</h1>
        </v-row>
        <v-row class="mt-2 mb-7">
            <div class="d-flex flex-row align-end mt-1 ml-0.75">
                <div class="control">
                    <label class="label">정렬 기준</label>
                    <v-select
                    :items="sort1"
                    dense
                    solo
                    v-model="select_sort1"
                    ></v-select>
                </div>
                <div class="control ml-6" style="width: 130px;">
                    <label class="label">기간</label>
                    <v-select
                    :items="sort2"
                    dense
                    solo
                    v-model="select_sort2"
                    ></v-select>
                </div>
                <v-spacer></v-spacer>
                <div class="control d-flex" style="width: 300px;">
                    <v-text-field
                        label="검색어 입력"
                        outlined
                        dense
                    ></v-text-field>
                    <v-btn 
                    depressed
                    dark
                    color="amber darken-1">검색</v-btn> 
                </div>
            </div>
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
                            <v-card-text>수강생 수: {{ card.count }}</v-card-text>
                            <v-rating
                            class="m-3"
                            :value="card.rate"
                            color="amber"
                            dense
                            half-increments
                            readonly
                            size="18"
                            ></v-rating>
                        </v-col>
                    </v-row>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn text>관리하기</v-btn>
                    </v-card-actions>

                    
                </v-card>
            </v-col>
        </v-row>
    </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import ClassDataService from '@/service/ClassDataService';


@Component( {
    components: {
        // LectureCard,
    }
})
export default class Lecture extends Vue {
    
    private sort1: string[] = [
        '최근 등록한 순', '평가순', '인원순'
    ]

    private select_sort1 = "최근 등록한 순";
    
    private sort2: string[] = [
        '진행중', '진행완료', '전체보기'
    ]

    private select_sort2 = "전체보기";

    private cards: object[] = [
    { title: '기타로 장범준 따라잡기', src: 'https://cdn.vuetifyjs.com/images/cards/house.jpg', count: 100, tutor: '김김김', overview: '장범준 기타 내 기타', rate: 5},
    { title: '초보자도 할 수 있는 피아노 반주', src: 'https://cdn.vuetifyjs.com/images/cards/road.jpg', count: 35, tutor: '이이이', overview: '말할수 없는 비밀 보고 시작했어요', rate: 2},
    { title: '오늘부터 바이올리니스트', src: 'https://cdn.vuetifyjs.com/images/cards/plane.jpg', count: 50, tutor: '박박박', overview: '헨리 바이올린 딱 기다려', rate: 3.5},
    { title: '드럼의 모든 것', src: 'https://cdn.vuetifyjs.com/images/cards/house.jpg', count: 65, tutor: '조조조', overview: '드럼을 오래 치면 힘드럼...', rate: 1},
      ];

    created() {
        this.getAllClass()
    }

    getAllClass(){
        ClassDataService.getAllClass()
        .then((response) => {
            console.log(response.data);
        })
        .catch((error) => {
            console.log(error.data);
        });
    }
  


}
</script>

<style>

.control {
    width: 170px;
    font-size: 1rem;
    position: relative;
    text-align: left;
}

.label {
    font-size: .85rem;
    font-weight: 400;
    color: #929292;

}

</style>