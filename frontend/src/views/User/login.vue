<template>
  <div
    id="login"
    class="wrapper d-flex flex-column align-center justify-center"
  >
    <div class="singup mt-10">
      <v-card style="text-align: center" width="200px">
        <v-icon class="mt-10">mdi-music-circle-outline</v-icon>
        <v-card-text>logo 이미지 들어감 </v-card-text>
      </v-card>
    </div>
    <v-form
      ref="form"
      v-model="valid"
      class="align-center mt-10"
      lazy-validation
      width="500"
    >
      <div>
        <v-item-group class="d-flex" mandatory @change="test">
          <v-item v-slot="{ active, toggle }" v-for="n in list" :key="n">
            <v-card
              :color="active ? 'teal' : ''"
              class="d-flex align-center"
              height="60"
              width="50%"
              @click="toggle"
            >
              <v-scroll-y-transition>
                <div
                  v-if="active"
                  class="display-1 flex-grow-1 text-center font-weight-black"
                >
                  {{ n }}
                </div>
                <div
                  v-else
                  class="display-1 flex-grow-1 text-center font-weight-thin"
                >
                  {{ n }}
                </div>
              </v-scroll-y-transition>
            </v-card>
          </v-item>
        </v-item-group>
      </div>
      <v-text-field
        v-model="email"
        :rules="emailRules"
        label="이메일"
        required
        class="mt-4"
      ></v-text-field>

      <v-text-field
        v-model="password"
        :rules="pwdRules"
        label="비밀번호"
        required
        type="password"
      ></v-text-field>

      <v-btn :disabled="!valid" color="teal" class="mx-auto mt-5" width="400">
        로그인
      </v-btn>
    </v-form>
    <div id="findID" class="mt-2 font-weight-light" @click="go">
      아이디 비밀번호찾기
    </div>
    <v-sheet
      color="teal"
      elevation="1"
      height="1"
      width="400"
      class="mt-2"
    ></v-sheet>

    <div class="mt-2">
      <v-btn
        color="teal lighten-2"
        class="mx-auto mt-5"
        width="200"
        @click="register"
      >
        <span v-if="loginType">학생 회원가입</span>
        <span v-else>튜터 회원가입</span>
      </v-btn>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class Login extends Vue {
  private loginType = false;
  private valid = true;
  private email = "";
  private password = "";
  private emailRules = [
    (v: string) => !!v || "이메일을 입력하세요",
    (v: string) => /.+@.+\..+/.test(v) || "이메일형식에 맞지 않습니다",
  ];
  private pwdRules = [(v: string) => !!v || "비밀번호를 입력하세요"];
  private list: any = ["학생", "튜터"];
  test() {
    this.loginType = !this.loginType;
    console.log(this.loginType ? "학생" : "튜터");
  }
  go() {
    console.log(
      this.loginType ? "학생 ~~~~~~~~~~" : "튜터ㅓㅓㅓ어어어어ㅓ어어"
    );
    console.log("아이디 비밀번호 찾기하러 가즈아ㅏㅏㅏㅏㅏㅏ");
  }
  register() {
    console.log("회원가입페이지로ㅗㅗㅗ오오오오오롱ㄹㅇ와ㅣ잉");
  }
}
</script>
<style>
.wrapper {
  position: relative;
  width: 100%;
}

.inputtype {
  width: 100%;
}
.logintype {
  width: 50%;
}
#findID {
  color: gray;
  opacity: 0.66;
  font-size: 6px;
}
#findID:hover {
  color: black;
  opacity: 1;
  font-size: 11px;
  font: bold;
  cursor: pointer;
}
</style>