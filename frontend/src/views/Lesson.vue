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
                    <v-card>
                        <v-card-title>
                            {{ title }}
                        </v-card-title>
                        <v-card-text
                        class="p-0 pl-4 pb-1"
                        v-for="bookmark in bookmarks"
                        :key="bookmark.time">
                            <v-divider
                            class="m-1">
                            </v-divider>
                            <v-row>
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
                        <v-card
                        class="mt-2"
                        tile
                        color="amber lighten-4">
                            <v-card-text
                            class="black--text">
                                {{ lectureNum }}
                                {{ title }}
                            </v-card-text>
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
            </v-row>
        </v-container>
    </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class MyPage extends Vue{
    private isPlay = false;
    private title = '코드 4개로 완곡하기 (벚꽃엔딩)';
    private lectureNum = '1-1';
    private bookmarks: object[] = [
        { time: '02:43', name: 'C코드' },
        { time: '04:32', name: 'E코드' },
        { time: '06:45', name: 'D코드' },
        { time: '08:02', name: 'G코드' },
    ];
}
</script>