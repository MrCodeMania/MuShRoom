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
          <v-item-group class="d-flex" mandatory @change="changeLoginType">
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
          :disabled="!valid"
          class="mx-auto mt-5"
          width="100%"
          @click="validate"
        >
          로그인
        </v-btn>
      </v-form>
      <div class="align-items-center d-flex flex-column">
        <v-btn
          id="findID"
          class="mt-2 font-weight-light"
          plain
          @click="
            $router.push({
              name: linkName,
              params: { type: loginType ? `학생` : `튜터` },
            })
          "
        >
          아이디 비밀번호찾기
        </v-btn>
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
import UserService from "@/service/User/Signup";
@Component
export default class Login extends Vue {
  private loginType = false; // 로그인 타입 체크
  private valid = true; // 현재 아이디 비밀번호 유효성 체크
  private email = "";
  private password = "";
  private emailRules = [
    (v: string) => !!v || "이메일을 입력하세요", // 이메일입력받았는지 검사
    (v: string) => /.+@.+\..+/.test(v) || "이메일형식에 맞지 않습니다", // 이메일 형식 ~@~.~ 형식 검사
  ];
  private pwdRules = [(v: string) => !!v || "비밀번호를 입력하세요"];
  private list: string[] = ["학생", "튜터"];
  private linkName = "FindUser";
  changeLoginType() {
    this.loginType = !this.loginType;
  }
  go() {
    if (this.loginType === true) {
      console.log("학생 찾기");
    } else {
      console.log("튜터로그인 처리");
    }
  }

  validate() {
    (this.$refs.form as Vue & { validate: () => boolean }).validate();
    if (this.valid) {
      const loginForm = { email: this.email, password: this.password };
      if (this.email == "" || this.password == "") return;

      //login PART
      if (this.loginType === true) {
        UserService.loginStudent(loginForm)
          .then((response) => {
            const loginData = response.data;
            if (loginData === "") {
              alert("아이디 또는 비밀번호가 틀렸어요");
              return;
            }
            this.$store.commit("setUserInfo", loginData);
            this.$store.commit("setType", false);
            this.$router.push("/");
          })
          .catch((err) => {
            console.log(err);
          });
      } else {
        // UserService.loginTutor(loginForm)
        //   .then((response) => {
        //     console.log(response);
        //     this.$store.commit("setUserInfo", response.data);
        //     this.$store.commit("setType", true);
        //     this.$router.push("/");
        //   })
        //   .catch((err) => {
        //     console.log(err);
        //   });
        this.$store.commit("setType", true);
        this.$router.push("/");
      }
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