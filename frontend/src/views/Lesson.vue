<template>
    <div id="lesson">
        <v-container fluid>
            <v-row>
                <v-col cols="12" md="8">
                    <v-card
                    min-width="850"
                    min-height="600">
                        <v-card-title
                        class="justify-center">
                            <v-card
                            min-width="800"
                            min-height="500"
                            class="text-center"
                            color="black"
                            >
                                <v-btn
                                elevation="0"
                                fab
                                text
                                large
                                absolute
                                :style="{top:'40%', border:'none'}">
                                    <v-icon
                                    class="text-white"
                                    size="64">
                                        mdi-play-circle
                                    </v-icon>
                                </v-btn>
                            </v-card>
                        </v-card-title>
                        <v-row
                        class="text-center">
                            <v-col cols="12" md="2">
                            </v-col>
                            <v-col md="1">
                                <v-btn
                                icon>
                                    <v-icon>
                                        mdi-chevron-double-left
                                    </v-icon>
                                </v-btn>
                            </v-col>
                            <v-col md="1">
                                <v-btn
                                icon>
                                    <v-icon>
                                        mdi-chevron-left
                                    </v-icon>
                                </v-btn>
                            </v-col>
                            <v-col md="4">
                                <v-btn
                                icon>
                                    <v-icon v-if="!isPlay">
                                        mdi-play
                                    </v-icon>
                                    <v-icon v-else>
                                        mdi-pause
                                    </v-icon>
                                </v-btn>
                            </v-col>
                            <v-col md="1">
                                <v-btn
                                icon>
                                    <v-icon>
                                        mdi-chevron-right
                                    </v-icon>
                                </v-btn>
                            </v-col>
                            <v-col md="1">
                                <v-btn
                                icon>
                                    <v-icon>
                                        mdi-chevron-double-right
                                    </v-icon>
                                </v-btn>
                            </v-col>
                            <v-col md="2">
                            </v-col>
                        </v-row>
                    </v-card>
                </v-col>
                <v-col md="4">
                    <v-row
                    no-gutters>
                        <v-col cols="12" md="11">
                            <v-card>
                                <v-card-title>
                                    {{ title }}
                                </v-card-title>
                                <v-card
                                elevation="0"
                                v-if="menu === '1'">
                                    <v-card-text
                                    class="p-0 pl-4 pb-1"
                                    v-for="bookmark in bookmarks"
                                    :key="bookmark.time">
                                        <v-divider
                                        class="m-1">
                                        </v-divider>
                                        <v-row
                                        class="pb-2">
                                            <v-col cols="12" md="6"
                                            class="pl-4 pt-4">
                                                <a href="#">{{ bookmark.time }}</a>
                                            </v-col>
                                            <v-col md="6"
                                            class="pt-4">
                                                {{ bookmark.name }}
                                            </v-col>
                                        </v-row>
                                    </v-card-text>
                                </v-card>
                                <v-card v-else-if="menu === '2'"
                                elevation="0"
                                v-scroll.self="onScroll"
                                class="overflow-y-auto"
                                height="300">
                                    <v-card
                                    elevation="0"
                                    v-for="question in questions"
                                    :key="question.nickname">
                                        <v-card
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
                                        color="amber lighten-4"
                                        class="rounded m-4 p-1">
                                        <v-icon>
                                            mdi-arrow-right-bold
                                        </v-icon>
                                        <v-card-text>
                                            {{ question.comment }}
                                        </v-card-text>
                                        </v-card>
                                    </v-card>
                                </v-card>
                                <v-card
                                elevation="0"
                                v-else>
                                    <v-row
                                    no-gutters>
                                    <v-col md="2" class="ml-4">
                                    </v-col>
                                    </v-row>
                                    <v-divider class="m-1"></v-divider>
                                    <v-card-text>
                                    <v-textarea
                                    background-color="white"
                                    placeholder="강의에 대한 질문을 남겨요"
                                    outlined
                                    ></v-textarea>
                                    <v-btn
                                    dark
                                    color="amber darken-4">
                                        작성
                                    </v-btn>
                                    </v-card-text>
                                </v-card>
                            </v-card>
                        </v-col>
                        <v-col md="1">
                            <v-btn-toggle
                            v-model="menu">
                                <v-btn
                                value='1'>
                                    <v-icon>
                                        mdi-bookmark-music
                                    </v-icon>
                                </v-btn>
                                <v-btn
                                value='2'>
                                    <v-icon>
                                        mdi-frequently-asked-questions
                                    </v-icon>
                                </v-btn>
                                <v-btn
                                value='3'>
                                    <v-icon>
                                        mdi-message-question
                                    </v-icon>
                                </v-btn>
                            </v-btn-toggle>
                        </v-col>
                    </v-row>
                </v-col>
            </v-row>
        </v-container>
    </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class MyPage extends Vue{
    private isPlay = false;
    private title = '1-1 코드 4개로 완곡하기 (벚꽃엔딩)';
    private bookmarks: object[] = [
        { time: '02:43', name: 'C코드' },
        { time: '04:32', name: 'E코드' },
        { time: '06:45', name: 'D코드' },
        { time: '08:02', name: 'G코드' },
    ];
    private questions: object[] = [
    { lecture: '1-1강', nickname: '이싸피', content: '저는 한손으로 코드가 다 안눌리는데 어떻게 하면 좋을까요;-;',
    comment: '손이 많이 작으시다면 간략화된 코드버전이 1-2 강의에 나옵니다^^!', date: '2021-01-26 16:08' },
    { lecture: '1-1강', nickname: '최싸피', content: '다음 강의에도 렛잇비가 나오던데 이번 강의랑 무슨 차이인가요?',
    comment: '이번 강의는 총 3부로 제작되어 그렇습니다^^ 연결되는 내용입니다^^!', date: '2021-01-26 09:00'},
  ];
    private menu = '1';
}
</script>
<style>
.v-btn-toggle {
  flex-direction: column;
}
</style>