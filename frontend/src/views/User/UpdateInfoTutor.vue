<template>
    <div class="container mb-10">
        <v-row class="my-4">
            <v-card class="mx-auto my-4">
                <h1 class="text-center">회원정보수정페이지</h1>
            </v-card>
        </v-row>
        <v-row>
            <v-col cols="2">
                <v-avatar
                style="border-radius: 50%;"
                size="200"
                >
                <v-img :src="userInfo.img"></v-img>
                </v-avatar>
            </v-col>
            <v-col class="d-flex align-end">
                <!-- 여기서 입력받은 파일 위에 이미지랑 어떻게 연결하지 -->
                <v-file-input
                    label="File input"
                    filled
                    append-icon="mdi-camera"
                ></v-file-input>
            </v-col>
        </v-row>
        <v-row>
            <v-text-field
            v-model="userInfo.name"
            label="Name"
            filled
            ></v-text-field>
            <v-text-field
            v-model="userInfo.nickname"
            label="Nickname"
            filled
            ></v-text-field>
            <v-text-field
            v-model="userInfo.email"
            label="Email"
            filled
            ></v-text-field>
            <!-- 휴대폰번호 수정 후 인증절차 다시 생각해보자 -->
            <div class="d-flex">
                <v-text-field
                v-model="userInfo.phone"
                label="Phone"
                filled
                ></v-text-field>
                <v-btn>인증</v-btn>
            </div>
            <v-btn 
            @click="toggleEventPassword"
            >
                비밀번호 변경
            </v-btn>
            
            <v-btn 
            @click="toggleEventBank"
            >
                계좌변경
            </v-btn>
            
        </v-row>
        <v-row>
            <v-card class="d-flex justify-center my-4">
                <v-chip close v-for="(instrument,n) in userInfo.instrument" :key="n" @click="userInfo.instrument.splice(n,1)">{{instrument}} </v-chip>    
                <div class="ml-6 d-flex" style="width: 200px;">
                    <v-select
                        class="control"
                        :items="instrument"
                        placeholder="악기"
                        dense
                        solo
                        v-model="selectInstrument"
                    ></v-select>
                    <v-btn outlined @click="addInstrument"> + </v-btn>
                </div>
            </v-card>
        </v-row>
        <v-row>
            <v-btn @click="updateInfo">
                회원정보수정
            </v-btn>
        </v-row>
        <UpdatePassword :toggleUpdatePassword="toggleUpdatePassword" @closeUpdatePassword="closeUpdatePassword" />
        <UpdateBank :bankInfo="userInfo.bankInfo" :toggleUpdateBank="toggleUpdateBank" @closeUpdateBank="closeUpdateBank($event)" />
    </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import UpdatePassword from '@/components/user/UpdatePassword.vue';
import UpdateBank from '@/components/user/UpdateBank.vue';

@Component({
    components: {
        UpdatePassword,
        UpdateBank,
    }
})
export default class UpdateInfoTutor extends Vue {

    private userInfo: any = this.$route.params.userInfo;

    private toggleUpdatePassword = false;
    private toggleUpdateBank = false;
    private instrument: string[] = [
        "바이올린", "비올라", "첼로"
    ]
    private selectInstrument = "";

    toggleEventPassword() {
        this.toggleUpdatePassword = true;
    }
    closeUpdatePassword() {
        this.toggleUpdatePassword = false;
    }

    toggleEventBank() {
        this.toggleUpdateBank = true;
    }
    closeUpdateBank(bank: object|string) {
        if (bank) {
            console.log(bank)
            console.log("왔어")
        } else {
            console.log("안왔어")
        }
        this.toggleUpdateBank = false;
    }

    addInstrument() {
        this.userInfo.instrument.push(this.selectInstrument);
        this.selectInstrument = "";
    }

    updateInfo() {
        console.log(this.userInfo)
    }
}
</script>

<style>

</style>