<template>
  <div
    id="login"
    class="wrapper d-flex flex-column align-center justify-center mb-4"
  >
    <v-sheet
      max-width="400px"
      width="50%"
      color="white"
      elevation="2"
      class="px-10 mt-4"
    >
      <div class="singup mx-auto mt-4">
        <v-card style="text-align: center" width="200px" class="mx-auto">
          <v-icon class="mt-10">mdi-music-circle-outline</v-icon>
          <v-card-text>logo 이미지 들어감 </v-card-text>
        </v-card>
      </div>
      <v-form
        ref="form"
        v-model="valid"
        lazy-validation
        class="align-center mt-10"
        width="400"
        min-width="400"
        validate
      >
        <div>
          <v-item-group class="d-flex" mandatory @change="changeState">
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
          @keydown.enter="logincheck"
        ></v-text-field>

        <v-btn
          color="teal"
          class="mx-auto mt-5"
          width="100%"
          :rules="[emailRules, pwdRules]"
          @click="logincheck"
        >
          로그인
        </v-btn>
      </v-form>
      <div class="align-items-center d-flex flex-column">
        <div
          id="findID"
          class="mt-2 font-weight-light"
          @click="$router.push({ path: linkName })"
        >
          아이디 비밀번호찾기
        </div>
        <v-sheet
          color="teal"
          elevation="1"
          height="1"
          width="100%"
          class="mt-2"
        ></v-sheet>

        <div class="mt-2 align-center d-flex justify-center">
          <v-btn
            v-if="loginType"
            color="teal lighten-2"
            class="mt-5 mb-10"
            width="100%"
            :to="{ name: 'StudentRegist' }"
            >학생 회원가입
          </v-btn>
          <v-btn
            v-else
            color="teal lighten-2"
            class="mt-5 mb-10"
            width="100%"
            :to="{ name: 'TutorRegist' }"
            >튜터 회원가입
          </v-btn>
        </div>
      </div>
    </v-sheet>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class Login extends Vue {
  private loginType = false;
  private valid = false;
  private email = "";
  private password = "";
  private emailRules = [
    (v: string) => !!v || "이메일을 입력하세요",
    (v: string) => /.+@.+\..+/.test(v) || "이메일형식에 맞지 않습니다",
  ];
  private pwdRules = [(v: string) => !!v || "비밀번호를 입력하세요"];
  private list: string[] = ["학생", "튜터"];
  private linkName = "FindUser";

  changeState() {
    if (!this.loginType) {
      this.linkName = "/user/find/student";
    } else {
      this.linkName = "/user/find/tutor";
    }
    this.loginType = !this.loginType;
  }
  go() {
    if (this.loginType === true) {
      console.log("학생 찾기");
    } else {
      console.log("튜터로그인 처리");
    }
  }
  logincheck() {
    const loginForm = { id: this.email, password: this.password };
    console.log(loginForm);
    if (this.loginType === true) {
      //학생로그인처리
    } else {
      console.log("튜터로그인 처리");
    }
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