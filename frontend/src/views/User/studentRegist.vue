<template>
  <div>
    <div class="d-flex justify-content-center align-content-center">
      <v-col cols="12" md="5">
        <v-form
          ref="form"
          v-model="valid"
          lazy-validation
          class="align-items-center"
        >
          <v-text-field
            solo
            v-model="name"
            :rules="nameRules"
            label="이름"
            prepend-inner-icon="mdi-human-handsup"
            required
          ></v-text-field>

          <v-text-field
            v-model="email"
            :rules="emailRules"
            label="E-mail"
            required
          ></v-text-field>
          <v-text-field
            type="password"
            v-model="password"
            :rules="passwordRules"
            label="비밀번호"
            required
          ></v-text-field>
          <v-text-field
            type="password"
            v-model="confirmPassword"
            :rules="[password === confirmPassword || '비밀번호 일치안함']"
            label="비밀번호 확인"
            required
          ></v-text-field>
          <v-btn
            :disabled="!valid"
            color="teal"
            id="registerEnter"
            @click="validate"
          >
            가입하기
          </v-btn>
        </v-form>
      </v-col>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from "vue";
export default Vue.extend({
  data: () => ({
    valid: false,
    name: "",
    nameRules: [(v: string) => !!v || "이름이 필요해요"],
    email: "",
    emailRules: [
      (v: string) => !!v || "이메일이 필요해요",
      (v: string) => /.+@.+\..+/.test(v) || "이메일형식이 맞지 않습니다",
    ],
    password: "",
    passwordRules: [
      (v: string) => !!v || "비밀번호를 입력하세요",
      (v: string) => (v && v.length >= 8) || "비밀번호는 8자리 이상 입니다.",
      (v: string) =>
        /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/.test(v) ||
        "특수문자 영어 숫자 포함해야합니다.",
    ],
    confirmPassword: "",
  }),
  computed: {},
  methods: {
    validate() {
      const registerForm = [this.name, this.email, this.password];
      console.log(registerForm);
    },
  },
});
</script>

<style>
#registerEnter {
  top: 0;
  bottom: 0;
  margin-top: auto;
  margin-bottom: auto;
}
</style>