<template>
    <div id="detail">
        <v-container fluid
        class="mb-16">
            <v-row justify="center">
                <v-col md="2">
                    <v-avatar
                    height="200"
                    width="200"
                    class="pr-6"
                    >
                        <v-img
                        :src="tutorInfo.tutorProfile == null ? sampleImg : tutorInfo.tutorProfile.imagePath">
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
                                {{ tutorInfo.name == null ? name : tutorInfo.name }}
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
                        :src="profileImage === 'testPath' ? sampleImg : profileImage"
                        alt=""
                        max-height="180"></v-img>
                        <div class="d-inline-block">
                            <v-card-title class="pt-2">
                                {{ classInfo.title }}
                            </v-card-title>
                        </div>
                        <v-btn
                        v-if="!isTaken"
                        dark
                        color="amber darken-3" 
                        class="mt-4 mr-4 float-right font-weight-bold"
                        @click="clickRegister"
                        >
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
                            :to="'/detail/'+cid+'/intro'">강의 소개</v-tab>
                            <v-tab
                            :to="'/detail/'+cid+'/curriculum'">커리큘럼</v-tab>
                            <v-tab
                            :to="'/detail/'+cid+'/review'">강의 후기</v-tab>
                        </v-tabs>
                        <v-tabs v-else>
                            <v-tab
                            :to="'/detail/'+cid+'/feedback'">피드백</v-tab>
                            <v-tab
                            :to="'/detail/'+cid+'/curriculum'">커리큘럼</v-tab>
                            <v-tab
                            :to="'/detail/'+cid+'/review'">강의 후기</v-tab>
                            <v-tab
                            :to="'/detail/'+cid+'/qna'">질의 응답</v-tab>
                        </v-tabs>
                        <router-view/>
                    </v-card>
                </v-col>
            </v-row>
        </v-container>
        <RegisterClass
        :register="register"
        @clickClose="closeEventHandler"/>
    </div> 
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import RegisterClass from '@/components/class/modal/RegisterClass.vue';
import ClassDataService from '@/service/ClassDataService';
import { mapState } from 'vuex';

@Component({
    components:{
        RegisterClass,
    },
    computed: {
        ...mapState(['userInfo'])
    }
})

export default class ClassDetail extends Vue {
    private name = '김철수';
    private heartClicked = false;
    private isTaken = true;
    private register = false;
    private cid = this.$route.params.cid;   // 조회하려는 클래스 id
    private classInfo: {[key: string]: any} = {};  // 클래스 정보
    private tutorInfo: {[key: string]: any} = {}; // 강사 정보
    private userInfo!: {[key: string]: any};  // 사용자 정보
    private sampleImg = require('@/assets/mushroom.png');   // 기본 썸네일 경로
    private profileImage = '';  // 대문 사진

    created() {
        // 강의 정보 조회
        ClassDataService.getOneClass(this.cid)
        .then((response) => {
            this.classInfo = response.data;
            this.tutorInfo = response.data.tutor;
            console.log(this.userInfo);
            this.profileImage = this.classInfo.profile.imagePath;
            // this.checkLikeTutor;
        }).catch((error) => {
            console.log(error);
        });
    }

    // 사용자의 강의 수강 여부 확인 method
    checkTakeClass(){
        const userClass = this.userInfo.userClass;
        if(userClass != null){
            if(userClass.progress.length != 0){
                // 
            }
            if(userClass.done.length != 0){
                //
            }
        }
    }

    // 좋아하는 강사 목록 확인 method
    checkLikeTutor(){
        const list = this.userInfo.like.tutorId;
        for(const tid in list){
            if(tid === this.tutorInfo['id'])
                this.heartClicked = true;
        }
    }

    // like 객체 생성 필수
    clickHeart(){
        this.userInfo.like.tutorId.push(this.tutorInfo.id);
        ClassDataService.updateUserInfo(this.userInfo)
        .then((response) => {
            this.checkLikeTutor;
        }).catch((error) => {
            console.log(error);
        });
    }
    closeEventHandler(close: boolean){
        this.register = close;
    }
    clickRegister(){
        this.register = true;
    }
}
</script>