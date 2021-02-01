<template>
  <v-col cols="12">
    <v-form
      ref="form"
      v-model="valid"
      lazy-validation
      class="align-items-center"
    >
      <v-text-field
        class="mt-3"
        solo
        v-model="name"
        :rules="nameRules"
        label="이름"
        prepend-inner-icon="mdi-human-handsup"
        required
      ></v-text-field>

      <v-text-field
        solo
        v-model="nickname"
        :rules="nicknameRules"
        label="별명"
        prepend-inner-icon="mdi-rename-box"
        required
      ></v-text-field>

      <v-text-field
        solo
        v-model="email"
        :rules="emailRules"
        label="E-mail"
        prepend-inner-icon="mdi-email"
        required
      ></v-text-field>
      <v-text-field
        solo
        type="password"
        v-model="password"
        :rules="passwordRules"
        label="비밀번호"
        prepend-inner-icon="mdi-lock"
        required
      ></v-text-field>
      <v-text-field
        solo
        type="password"
        v-model="confirmPassword"
        :rules="[password === confirmPassword || '비밀번호 일치안함']"
        label="비밀번호 확인"
        prepend-inner-icon="mdi-lock-check"
        required
      ></v-text-field>
      <v-btn color="teal" :disabled="!valid" @click="validate">
        계속하기
      </v-btn>
    </v-form>
  </v-col>
</template>
<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
@Component
export default class Account extends Vue {
  private valid = false;
  //이름
  private name = "";
  private nameRules = [(v: string) => !!v || "이름이 필요해요"];
  //닉네임
  private nickname = "";
  private nicknameRules = [(v: string) => !!v || "별명이 필요해요"];
  //이메일
  private email = "";
  private emailRules = [
    (v: string) => !!v || "이메일이 필요해요",
    (v: string) => /.+@.+\..+/.test(v) || "이메일형식이 맞지 않습니다",
  ];
  //비밀번호
  private password = "";
  private passwordRules = [
    (v: string) => !!v || "비밀번호를 입력하세요",
    (v: string) => (v && v.length >= 8) || "비밀번호는 8자리 이상 입니다.",
    (v: string) =>
      /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/.test(v) ||
      "특수문자 영어 숫자 포함해야합니다.",
  ];
  // 비밀번호 재입력
  private confirmPassword = "";

  //입력폼 함수
  validate() {
    (this.$refs.form as Vue & { validate: () => boolean }).validate();
    const registerForm = {
      name: this.name,
      nickname: this.nickname,
      email: this.email,
      password: this.password,
    };
    if (this.name === "" || this.email === "" || this.password === "") return;
    this.$emit("sendform", registerForm);
  }
}
</script>
<style>
</style>