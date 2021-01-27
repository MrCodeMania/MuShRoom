<template>
  <div id="FindUser" style="max-width: 600px" class="mx-auto mb-3">
    <div class="mt-4">
      <v-card>
        <v-card-title class="text-center justify-center py-6">
          <h1 class="font-weight-bold display-3">{{ paramsText }}LOGO</h1>
        </v-card-title>

        <v-tabs v-model="tab" background-color="transparent" grow>
          <v-tab v-for="item in items" :key="item">
            {{ item }}
          </v-tab>
        </v-tabs>

        <v-tabs-items v-model="tab">
          <v-tab-item v-for="(item, index) in items" :key="item">
            <v-card flat>
              <div v-if="index == 0">
                <FindId @sendFindIdForm="receiveID"></FindId>
              </div>
              <div v-else>
                <FindPassword @sendFindPwdForm="receivePWD"></FindPassword>
              </div>
            </v-card>
          </v-tab-item>
        </v-tabs-items>
        <v-tabs-items align="center">
          <v-btn class="text-center mb-4" @click="findStart"
            >찾기</v-btn
          ></v-tabs-items
        >
      </v-card>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import FindId from "@/components/user/find/FindId.vue";
import FindPassword from "@/components/user/find/FindPassword.vue";
@Component({
  components: {
    FindId,
    FindPassword,
  },
})
export default class extends Vue {
  private tab = null;
  private items = ["아이디", "비밀번호"];
  private paramsText = "";
  private form = {};
  private state = 1;
  private mounted() {
    console.log(this.$route.params);
    const { type } = this.$route.params;
    this.paramsText = type;
  }
  receiveID(findIdForm: object) {
    this.state = 2;
    this.form = findIdForm;
  }
  receivePWD(findPWdForm: object) {
    this.state = 3;
    this.form = findPWdForm;
  }
  findStart() {
    if (this.state === 2) {
      console.log("아이디 찾기");
      console.log(this.form);
    } else if (this.state === 3) {
      console.log("비밀번호 찾기");
      console.log(this.form);
    }
  }
}
</script>
<style>
</style>