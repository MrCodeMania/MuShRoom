<template>
    <div class="container">
        <v-row class="my-4">
            <v-card class="mx-auto my-4">
                <h1 class="text-center">회원정보수정페이지</h1>
                {{ userInfo }}
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
        </v-row>
        <v-row>
            <!-- 테이블에서 추가 삭제하는 방법 찾아서 추가 -->
        </v-row>
        <UpdatePassword :toggleUpdatePassword="toggleUpdatePassword" @closeUpdatePassword="closeUpdatePassword" />
    </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import UpdatePassword from '@/components/user/UpdatePassword.vue';

@Component({
    components: {
        UpdatePassword,
    }
})
export default class UpdateInfoUser extends Vue {

    private userInfo = this.$route.params.userInfo;

    private toggleUpdatePassword = false;


    toggleEventPassword() {
        this.toggleUpdatePassword = true;
    }

    closeUpdatePassword() {
        this.toggleUpdatePassword = false;
    }
}
</script>

<style>

</style>