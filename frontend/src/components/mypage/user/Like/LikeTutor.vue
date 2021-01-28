<template>
    <div class="container">
        <v-row class="my-4">
            <h1>강사</h1>
        </v-row>
        <v-row dense class="mb-10">
            <v-col
            cols="3"
            v-for="(tutor,key) in tutors"
            :key=key
            >
                <v-card width="300" class="mx-auto my-5">
                    <v-list-item three-line>
                        <v-list-item-avatar
                            tile
                            size="80"
                            color="grey"
                        >
                            <v-img :src="tutor.img"></v-img>
                        </v-list-item-avatar>
                        <v-list-item-content>
                            <div class="headline mb-1">
                            {{ tutor.name }}
                            </div>
                            <v-list-item-title class="overline mb-4">
                            {{ tutor.nickname }}
                            </v-list-item-title>
                            <v-list-item-subtitle>{{ tutor.content }}</v-list-item-subtitle>
                        </v-list-item-content>

                    </v-list-item>

                    <v-card-actions>
                        <v-btn
                            outlined
                            rounded
                            text
                        >
                            프로필보기
                        </v-btn>
                        <v-spacer></v-spacer>
                        <v-btn
                        outlined
                        rounded
                        text
                        >
                            팔로우 취소
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-col>
        </v-row>
    </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class LikeTutor extends Vue{
    
    private tutors: object[] = [
        { name: "김강사", nickname: "김기타", content: "나를 소개하지", img: "https://cdn.vuetifyjs.com/images/john.png" },
        { name: "이강사", nickname: "이드럼", content: "나를 소개하지", img: "https://cdn.vuetifyjs.com/images/john.png" },
        { name: "박강사", nickname: "박리코더", content: "나를 소개하지", img: "https://cdn.vuetifyjs.com/images/john.png" },
        { name: "박강사", nickname: "박리코더", content: "나를 소개하지", img: "https://cdn.vuetifyjs.com/images/john.png" },
        { name: "박강사", nickname: "박리코더", content: "나를 소개하지", img: "https://cdn.vuetifyjs.com/images/john.png" },
    ]
    
    
}
</script>

<style>

</style>