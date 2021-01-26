<template>
  <div id="studentRegist">
    <v-stepper v-model="nowpage" vertical class="mt-10">
      <v-stepper-step :complete="nowpage > 1" step="1" color="teal">
        회원정보
        <small>이름 아이디 비밀번호</small>
      </v-stepper-step>

      <v-stepper-content step="1">
        <v-card color="grey lighten-1" class="mb-12" height="100%">
          <Account></Account>
        </v-card>
        <v-btn color="teal" @click="nowpage = 2"> Continue </v-btn>
        <v-btn text @click="nowpage = 1"> Cancel </v-btn>
      </v-stepper-content>

      <v-stepper-step :complete="nowpage > 2" step="2" color="teal">
        휴대전화 인증
        <small>전화 인증yo</small>
      </v-stepper-step>

      <v-stepper-content step="2">
        <v-card color="grey lighten-1" class="mb-12" height="200px">
          <Phone></Phone>
        </v-card>
        <v-btn color="teal" @click="nowpage = 3"> Continue </v-btn>
        <v-btn text @click="nowpage = 1"> Cancel </v-btn>
      </v-stepper-content>

      <v-stepper-step :complete="nowpage > 3" step="3" color="teal">
        악기
      </v-stepper-step>

      <v-stepper-content step="3">
        <v-card color="grey lighten-1" class="mb-12" height="100%">
          악기 추가 //page
        </v-card>
        <v-btn color="teal" @click="nowpage = 4"> 가입하기 </v-btn>
        <v-btn text @click="nowpage = 2"> 취소 </v-btn>
      </v-stepper-content>
    </v-stepper>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import Phone from "@/components/user/Phone.vue";
import Account from "@/components/user/Account.vue";

@Component({
  components: {
    Phone,
    Account,
  },
})
export default class StudentRegist extends Vue {
  //
  private nowpage = 1;
  // 가입 처리
  // 자식 컴포넌트에서 계정정보와 핸드폰 번호 그리고 악기정보 받아 오기
}
</script>

<style>
</style>