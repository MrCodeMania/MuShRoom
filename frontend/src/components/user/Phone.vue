<template>
  <v-form ref="form">
    <v-container>
      <v-row class="mt-2" cols="12">
        <v-col cols="9" align="center">
          <v-text-field
            solo
            v-model="phoneNumber"
            label="전화번호 입력"
            :rules="phoneNumberRules"
            required
          ></v-text-field>
        </v-col>
        <v-col cols="1"> <v-btn @click="phoneCheck">보내기</v-btn></v-col>
      </v-row>
      <v-row cols="12">
        <v-col cols="9">
          <v-text-field
            solo
            v-model="confirmNumber"
            label="인증번호"
            required
          ></v-text-field>
        </v-col>
        <v-col cols="1"> <v-btn @click="sendPhoneNumber">확인</v-btn></v-col>
      </v-row>
    </v-container>
  </v-form>
</template>
<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
@Component
export default class Phone extends Vue {
  private phoneNumber = "";
  private confirmNumber = "";
  private phoneNumberRules = [
    (v: string) => !!v || "핸드폰번호를 입력하세요",
    (v: string) => !/[^0-9]/.test(v) || "숫자만 입력해야합니다.",
  ];
  private phoneCheck() {
    console.log(this.phoneNumber);
    this.$emit("emitEvent", this.phoneNumber);
  }
  private sendPhoneNumber() {
    // 인증번호맞는지 확인하고 emit보내기
    //console.log(this.phoneNumber);
    this.$emit("sendPhonenumber", this.phoneNumber);
  }
}
</script>
<style>
</style>