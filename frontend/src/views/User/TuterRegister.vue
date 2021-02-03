<template>
  <div id="TuterRegister" style="max-width: 600px" class="mx-auto mb-3">
    <v-stepper v-model="nowpage" vertical class="mt-10">
      <v-stepper-step :complete="nowpage > 1" step="1" color="teal">
        회원정보
        <small>이름 아이디 비밀번호</small>
      </v-stepper-step>

      <v-stepper-content step="1">
        <v-card color="grey lighten-1" class="mb-12" height="400px">
          <Account @sendform="receiveform"></Account>
        </v-card>
      </v-stepper-content>
      <!--휴대전화-->
      <v-stepper-step :complete="nowpage > 2" step="2" color="teal">
        휴대전화 인증
        <small></small>
      </v-stepper-step>

      <v-stepper-content step="2">
        <v-card color="grey lighten-1" class="mb-12" height="200px">
          <Phone @sendPhonenumber="receivePhonenumber"></Phone>
        </v-card>
        <v-btn color="teal" :disabled="!phoneCheck" @click="nowpage = 3">
          계속하기
        </v-btn>
        <v-btn text @click="nowpage = 1"> 이전으로 </v-btn>
      </v-stepper-content>
      <!--경력-->
      <v-stepper-step :complete="nowpage > 3" step="3" color="teal">
        경력
      </v-stepper-step>

      <v-stepper-content step="3">
        <v-card color="grey lighten-1" class="mb-12" height="100%">
          <Career @stepPrev="nowpage = 2" @sendCareer="receiveCareer"></Career>
        </v-card>
      </v-stepper-content>
      <!--계좌-->
      <v-stepper-step :complete="nowpage > 4" step="4" color="teal">
        계좌 연결
      </v-stepper-step>

      <v-stepper-content step="4">
        <v-card color="grey lighten-1" class="mb-12" height="100%">
          <Bank @sendBank="registerFormWrite"></Bank>
        </v-card>
        <v-btn color="teal" :disabled="!registerCheck" @click="register">
          가입하기
        </v-btn>
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
import { IDmap } from "../../utils/instrmentID";
import UserService from "@/service/User/Signup";
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
  private phoneCheck = false;
  private registerCheck = false;
  private registerForm: any = {
    email: "",
    name: "",
    nickname: "",
    password: "",
    phoneNumber: "",
    tutorAccount: { accountNumber: "", bankName: "" },
    tutorProfile: {
      imagePath: "empty",
      intro: "empty",
    },
  };
  receiveform(form: { name: ""; nickname: ""; email: ""; password: "" }) {
    //인적정보 받아오는 부분
    this.registerForm.email = form["email"];
    this.registerForm.name = form["name"];
    this.registerForm.nickname = form["nickname"];
    this.registerForm.password = form["password"];
    this.nowpage = 2;
  }
  receivePhonenumber(phoneNumber: string) {
    this.registerForm.phoneNumber = phoneNumber;
    this.phoneCheck = true;
  }
  receiveCareer(v: { instrument: "" }) {
    // this.registerForm.instList.push(Number(IDmap.get(v["instrument"])));
    this.nowpage = 4;
  }
  registerFormWrite(b: string, a: string) {
    this.registerForm.tutorAccount.accountNumber = a;
    this.registerForm.tutorAccount.bankName = b;
    this.registerCheck = true;
  }
  register() {
    console.log("가입하기");
    //console.log(this.registerForm);
    UserService.signupTutor(this.registerForm)
      .then((response) => {
        console.log(response);
      })
      .catch((err) => {
        console.log(err);
      });
  }
}
</script>

<style>
</style>