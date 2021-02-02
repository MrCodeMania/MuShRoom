<template>
    <div class="container  mb-10">
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
            <v-card class="d-flex justify-center my-4">
                <p class="mt-2 mb-7 infotext">관심사: 
                    <v-simple-table
                        fixed-header
                        height="auto"
                        style="width: 300px;"
                    >
                        <thead>
                        <tr>
                            <th class="text-left">
                            악기
                            </th>
                            <th class="text-left">
                            숙련도
                            </th>
                            <th class="text-left">
                            관리
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr
                            v-for="(item,i) in userInfo.interest"
                            :key="i"
                        >
                            <td>{{ item.instrument }}</td>
                            <td>{{ item.level }}</td>
                            <td>
                                <v-btn outlined @click="deleteInterest(i)">-</v-btn>

                            </td>
                        </tr>
                        </tbody>
                    </v-simple-table>
                </p>
                <div class="control">
                    <v-select
                    :items="instrument"
                    placeholder="악기"
                    dense
                    solo
                    v-model="interest.instrument"
                    ></v-select>
                </div>
                <div class="control ml-6" style="width: 130px;">
                    <v-select
                    :items="level"
                    placeholder="실력"
                    dense
                    solo
                    v-model="interest.level"
                    ></v-select>
                </div>
                <v-btn outlined @click="addInterest"> + </v-btn>
            </v-card>
        </v-row>
        <v-row>
            <v-btn @click="updateInfo">
                회원정보수정
            </v-btn>
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

    private userInfo: object| any = this.$route.params.userInfo;

    private toggleUpdatePassword = false;

    private instrument: string[] = [
        "바이올린", "비올라", "첼로"
    ]

    private level: string[] = [
        "초급", "중급", "고급"
    ]

    private interest: object|any = {
        instrument: "", level: "초급"
    }

    deleteInterest(n: number) {
        this.userInfo.interest.splice(n, 1);
        console.log(this.userInfo.interest)
    }

    addInterest() {
        const newInterest = {
            instrument: this.interest.instrument, level: this.interest.level
        }
        this.userInfo.interest.push(newInterest);
        this.interest.instrument = "";
        this.interest.level = "초급";
    }

    toggleEventPassword() {
        this.toggleUpdatePassword = true;
    }

    closeUpdatePassword() {
        this.toggleUpdatePassword = false;
    }

    updateInfo() {
        console.log(this.userInfo)
    }
}
</script>

<style>

</style>