<template>
    <div id="detail">
        <v-container fluid>
            <v-row justify="center">
                <v-col md="2">
                    <v-avatar
                    height="200"
                    width="200"
                    >
                        <v-img
                        src="https://cdn.vuetifyjs.com/images/john.jpg"
                        alt="John">
                        </v-img>
                    </v-avatar>
                    <v-card
                    elevation="0"
                    class="mt-2"
                    max-width="250">
                        <v-card-actions>
                            <v-icon class="inline">
                                mdi-account-music
                            </v-icon>
                            <v-card-text
                            class="p-0 text-center font-weight-bold">
                                {{ name }}
                            </v-card-text>
                            <v-btn
                            depressed
                            color="white"
                            @click="clickHeart()">
                                <v-icon v-if="!heartClicked" class="inline">
                                    mdi-heart-outline
                                </v-icon>
                                <v-icon v-else class="inline">
                                    mdi-heart
                                </v-icon>
                            </v-btn>
                        </v-card-actions>
                    </v-card>
                </v-col>
                <v-col md="7">
                    <v-card
                    color="white"
                    elevation="1"
                    width="800"
                    height="250"
                    class="d-inline-block"
                    >
                        <v-img
                        src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
                        alt=""
                        max-height="180"></v-img>
                        <div class="d-inline-block">
                            <v-card-title class="pt-2">
                                {{ title }}
                            </v-card-title>
                            <v-card-subtitle>
                                {{ describe }}
                            </v-card-subtitle>
                        </div>
                        <v-btn
                        v-if="!isTaken"
                        dark
                        color="amber darken-3" 
                        class="mt-4 mr-4 float-right font-weight-bold">
                            수강신청
                        </v-btn>
                    </v-card>
                </v-col>
            </v-row>
            <v-row justify="center"
            no-gutters>
                <v-col md="9">
                    <v-card>
                        <v-tabs v-if="!isTaken">
                            <v-tab
                            to="/detail/intro">강의 소개</v-tab>
                            <v-tab
                            to="/detail/curriculum">커리큘럼</v-tab>
                            <v-tab
                            to="/detail/review">강의 후기</v-tab>
                        </v-tabs>
                        <v-tabs v-else>
                            <v-tab
                            to="/detail/feedback">피드백</v-tab>
                            <v-tab
                            to="/detail/curriculum">커리큘럼</v-tab>
                            <v-tab
                            to="/detail/review">강의 후기</v-tab>
                            <v-tab
                            to="/detail/qna">질의 응답</v-tab>
                        </v-tabs>
                        <router-view :isTaken="isTaken"/>
                    </v-card>
                </v-col>
            </v-row>
        </v-container>
    </div> 
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class ClassDetail extends Vue {
    private name = '김철수';
    private heartClicked = false;
    private title = '피아노로 연주하는 올드 팝';
    private describe = '비틀즈의 명곡을 피아노로 연주해 봅시다!';
    private isTaken = true;

    clickHeart(){
        this.heartClicked = !this.heartClicked;
    }
}
</script>