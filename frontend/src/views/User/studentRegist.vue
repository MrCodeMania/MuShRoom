<template>
  <div id="StudentRegist" style="max-width: 600px" class="mx-auto mb-3">
    <v-stepper v-model="nowpage" vertical class="mt-10">
      <v-stepper-step :complete="nowpage > 1" step="1" color="teal">
        회원정보
        <small>이름 아이디 비밀번호</small>
      </v-stepper-step>

      <v-stepper-content step="1">
        <v-card color="grey lighten-1" class="mb-12" height="100%">
          <Account @sendform="receiveform"></Account>
        </v-card>
      </v-stepper-content>

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

      <v-stepper-step :complete="nowpage > 3" step="3" color="teal">
        악기(선택사항)
      </v-stepper-step>

      <v-stepper-content step="3">
        <v-card color="grey lighten-1" class="mb-12" height="100%">
          <Instrument @formSend="receiveInst"></Instrument>
        </v-card>
        <v-btn color="teal" @click="register"> 가입하기 </v-btn>
        <v-btn text @click="nowpage = 2"> 이전으로 </v-btn>
      </v-stepper-content>
    </v-stepper>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import Phone from "@/components/user/Phone.vue";
import Account from "@/components/user/Account.vue";
import Instrument from "@/components/user/Instrument.vue";
import UserService from "@/service/User/Signup";
import { Grademap, IDmap } from "../../utils/instrmentID";
@Component({
  components: {
    Phone,
    Account,
    Instrument,
  },
})
export default class StudentRegist extends Vue {
  //

  //
  private nowpage = 1;
  private phoneCheck = false;
  private data: object[] = [];
  private tempInstrument = [];
  private registerForm: any = {
    email: "",
    nickname: "",
    instrument: [],
    password: "",
    phone: "",
  };
  // 가입 처리
  // 자식 컴포넌트에서 계정정보와 핸드폰 번호 그리고 악기정보 받아 오기
  receiveform(form: { email: ""; nickname: ""; password: "" }) {
    //인적정보 받아오는 부분
    this.nowpage = 2;
    this.registerForm.email = form["email"];
    this.registerForm.nickname = form["nickname"];
    this.registerForm.password = form["password"];
    console.log(this.registerForm);
  }
  receivePhonenumber(phoneNumber: string) {
    // 핸드폰 번호 받아오기
    this.registerForm.phone = phoneNumber;
    this.phoneCheck = true;
  }
  receiveInst(v: []) {
    this.tempInstrument = v;
  }
  register() {
    console.log("가입완료처리");
    const newInsForm = [];
    for (const item of Array.from(this.tempInstrument)) {
      const tempData = {
        instrumentId: IDmap.get(item["name"]),
        level: Grademap.get(item["grade"]),
      };
      newInsForm.push(tempData);
    }
    this.registerForm.instrument = newInsForm;
    UserService.pushUser(this.registerForm)
      .then((response) => {
        this.data = response.data;
        console.log(this.data);
      })
      .catch((error) => {
        console.log(error.data);
      });
  }
}
</script>

<style>
</style>