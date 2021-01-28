<template>
  <div id="TuterRegister" style="max-width: 600px" class="mx-auto mb-3">
    <v-stepper v-model="nowpage" vertical class="mt-10">
      <v-stepper-step :complete="nowpage > 1" step="1" color="teal">
        회원정보
        <small>이름 아이디 비밀번호</small>
      </v-stepper-step>

      <v-stepper-content step="1">
        <v-card color="grey lighten-1" class="mb-12" height="400px">
          <Account></Account>
        </v-card>
        <v-btn color="teal" @click="nowpage = 2"> 계속하기 </v-btn>
        <v-btn text @click="nowpage = 1"> 이전으로 </v-btn>
      </v-stepper-content>
      <!--휴대전화-->
      <v-stepper-step :complete="nowpage > 2" step="2" color="teal">
        휴대전화 인증
        <small></small>
      </v-stepper-step>

      <v-stepper-content step="2">
        <v-card color="grey lighten-1" class="mb-12" height="200px">
          <Phone></Phone>
        </v-card>
        <v-btn color="teal" @click="nowpage = 3"> 계속하기 </v-btn>
        <v-btn text @click="nowpage = 1"> 이전으로 </v-btn>
      </v-stepper-content>
      <!--경력-->
      <v-stepper-step :complete="nowpage > 3" step="3" color="teal">
        경력
      </v-stepper-step>

      <v-stepper-content step="3">
        <v-card color="grey lighten-1" class="mb-12" height="100%">
          <Career></Career>
        </v-card>
        <v-btn color="teal" @click="nowpage = 4"> 계속하기 </v-btn>
        <v-btn text @click="nowpage = 2"> 이전으로 </v-btn>
      </v-stepper-content>
      <!--계좌-->
      <v-stepper-step :complete="nowpage > 4" step="4" color="teal">
        계좌 연결
      </v-stepper-step>

      <v-stepper-content step="4">
        <v-card color="grey lighten-1" class="mb-12" height="100%">
          <Bank></Bank>
        </v-card>
        <v-btn color="teal" @click="nowpage = 4"> 가입하기 </v-btn>
        <v-btn text @click="nowpage = 3"> 이전으로 </v-btn>
      </v-stepper-content>
    </v-stepper>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import Phone from "@/components/user/Phone.vue";
import Account from "@/components/user/Account.vue";
import Career from "@/components/user/tutor/TutorCareer.vue";
import Bank from "@/components/user/tutor/Bankaccount.vue";
@Component({
  components: {
    Phone,
    Account,
    Career,
    Bank,
  },
})
export default class TuterRegister extends Vue {
  //
  private nowpage = 1;
  // 가입 처리
  // 자식 컴포넌트에서 계정정보와 핸드폰 번호 그리고 악기정보 받아 오기
}
</script>

<style>
</style>