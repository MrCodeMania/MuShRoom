<template>
    <div id="registerModal">
        <v-row justify="center">
            <v-dialog
            v-model="register"
            max-width="800"
            persistent
            >
                <v-card>
                    <v-card-title class="headline justify-center">
                    수강 신청
                    </v-card-title>
                    <v-card-text
                    class="text-center">
                        <v-row>
                            <v-col cols="12" md="2">
                            </v-col>
                            <v-col md="10">
                                <v-radio-group
                                v-model="classType">
                                    <v-row>
                                        <v-col cols="12" md="8">
                                            <v-radio
                                            :label="classVideo"
                                            color="orange"
                                            value="0"
                                            ></v-radio>
                                        </v-col>
                                        <v-col md="4">
                                            <v-card-text>
                                                {{ videoCost }}원
                                            </v-card-text>
                                        </v-col>
                                    </v-row>
                                    <v-row>
                                        <v-col cols="12" md="8">
                                        <v-radio
                                        :label="classFeedback"
                                        color="orange"
                                        value="1"
                                        :disabled="!isSemester"
                                        :class="{ 'text-decoration-line-through' : !isSemester}"
                                        ></v-radio>
                                        <v-card-subtitle
                                        v-if="!isSemester"
                                        class="p-0 pl-8 red--text"
                                        >
                                            이번 학기는 마감되었습니다.
                                        </v-card-subtitle>
                                        </v-col>
                                        <v-col md="4">
                                            <v-card-text>
                                                {{ feedbackCost }}원
                                            </v-card-text>
                                        </v-col>
                                    </v-row>
                                </v-radio-group>
                            </v-col>
                        </v-row>
                        <v-row>
                            <v-col cols="12" md="2">
                            </v-col>
                            <v-col md="8">
                                <v-card-title>
                                    <h2>포인트</h2>
                                </v-card-title>
                                <v-card-text
                                class="text-left">
                                    <h6>결제 금액의 10%까지 포인트로 사용 가능합니다.</h6>
                                </v-card-text>
                                <v-card-text
                                class="text-left pt-0">
                                    <h6><span class="text-no-warp light-green lighten-3">
                                        최대 사용가능: {{ available }}p 보유 포인트: {{point}}p
                                    </span></h6>
                                </v-card-text>
                                <v-row>
                                    <v-col cols="12" md="7">
                                        <v-text-field
                                        class="ml-4"
                                        outlined
                                        v-model="usePoint">
                                        </v-text-field>
                                    </v-col>
                                    <v-col md="5"
                                    class="p-2 pb-10">
                                        <v-btn
                                        width="60%"
                                        height="90%"
                                        @click="useAllPoint"
                                        >
                                        최대 사용
                                        </v-btn>
                                    </v-col>
                                </v-row>
                            </v-col>
                            <v-col md="2">
                            </v-col>
                        </v-row>
                        <v-row>
                            <v-col cols="12" md="2">
                            </v-col>
                            <v-col md="8">
                                <v-card-title>
                                    <h2>결제 금액 확인</h2>
                                </v-card-title>
                                    <v-card
                                    elevation="0"
                                    class="rounded-0"
                                    color="light-green lighten-5">
                                    <v-row>
                                        <v-col cols="12" md="8">
                                            <v-card-text
                                            class="p-0"
                                            v-if="classType=='0'">
                                                {{ classVideo }}
                                            </v-card-text>
                                            <v-card-text
                                            class="p-0"
                                            v-else>
                                                {{ classFeedback }}
                                            </v-card-text>
                                        </v-col>
                                        <v-col
                                        md="4">
                                            <v-card-text
                                            class="p-0"
                                            v-if="classType=='0'">
                                                {{ videoCost }}원
                                            </v-card-text>
                                            <v-card-text
                                            class="p-0"
                                            v-else>
                                                {{ feedbackCost }}원
                                            </v-card-text>
                                        </v-col>
                                    </v-row>
                                    <v-row>
                                        <v-col cols="12" md="8"
                                        class="text-left pl-12">
                                            포인트 할인
                                        </v-col>
                                        <v-col md="4">
                                            - {{ usePoint }}원
                                        </v-col>
                                    </v-row>
                                    <v-divider></v-divider>
                                    <v-row>
                                        <v-col cols="12" md="8"
                                        class="text-left pl-12">
                                            <h4>합계</h4>
                                        </v-col>
                                        <v-col md="4">
                                            <h5>{{ totalCost }}원</h5>
                                        </v-col>
                                    </v-row>
                                </v-card>
                            </v-col>
                            <v-col md="3">
                            </v-col>
                        </v-row>
                        <v-row>
                            <v-col cols="12" md="2">
                            </v-col>
                            <v-col md="8">
                                <v-card-title>
                                    <h2>결제 수단</h2>
                                </v-card-title>
                                <v-row>
                                    <v-col>
                                        <v-btn-toggle v-model="payment"
                                        class="justify-center">
                                            <v-btn
                                            width="100%"
                                            color="light-green lighten-3"
                                            class="font-weight-bold"
                                            value="1"
                                            >
                                                신용카드
                                            </v-btn>
                                            <v-btn
                                            width="100%"
                                            color="light-green lighten-3"
                                            class="font-weight-bold"
                                            value="2"
                                            >
                                                계좌이체
                                            </v-btn>
                                        </v-btn-toggle>
                                    </v-col>
                                </v-row>
                                <v-row
                                class="mt-16">
                                    <v-col>
                                        <v-btn
                                        color="orange darken-1"
                                        class="font-weight-bold"
                                        @click="(event) => clickClose(false)"
                                        width="100%"
                                        >
                                            결제하기
                                        </v-btn>
                                    </v-col>
                                    <v-col>
                                        <v-btn
                                        color="orange darken-1"
                                        class="font-weight-bold"
                                        @click="(event) => clickClose(false)"
                                        width="100%"
                                        >
                                            취소
                                        </v-btn>
                                    </v-col>
                                </v-row>
                            </v-col>
                            <v-col md="2">
                            </v-col>
                        </v-row>
                    </v-card-text>
                </v-card>
            </v-dialog>
        </v-row>
    </div>
</template>

<script lang="ts">
import { Component, Emit, Prop, Vue, Watch } from 'vue-property-decorator';

@Component
export default class ClassDetail extends Vue {
    @Prop(Boolean) register!: boolean;
    @Prop(String) title!: string;
    private classVideo = this.title + ' 동영상 강의';
    private classFeedback = this.title + ' 동영상 강의 + 피드백';
    private classType = '0';
    private isSemester = false;
    private videoCost = 120000;
    private feedbackCost = 250000;
    private available = 0;
    private point = 9000;
    private usePoint = 0;
    private totalCost = this.videoCost;
    private payment = '0';

    created() {
        this.available = this.videoCost*0.1;
    }

    useAllPoint(){
        this.usePoint = this.available;
    }

    @Watch('usePoint')
    checkPoint(){
        if(this.usePoint > this.available){
            alert('최대 사용 포인트를 초과했습니다.');
            this.usePoint = this.available;
        }
        this.totalCost = this.videoCost-this.usePoint;
    }

    @Emit('clickClose')
    clickClose(close: boolean){
        //
    }

}
</script>