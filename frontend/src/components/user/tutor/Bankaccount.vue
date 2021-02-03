<template>
  <div>
    <v-col cols="12">
      <v-form ref="form" v-model="valid" lazy-validation>
        <v-text-field
          solo
          v-model="bankname"
          :rules="check"
          label="은행"
          required
        ></v-text-field>

        <v-text-field
          solo
          v-model="account"
          :rules="check"
          label="계좌번호"
          required
        ></v-text-field>

        <v-btn :disabled="!valid" color="teal" class="mr-4" @click="validate">
          확인하기
        </v-btn>
      </v-form>
    </v-col>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class extends Vue {
  private valid = false;
  private bankname = "";
  private account = "";
  private check = [(v: string) => !!v || "값 있어야합니다"];
  validate() {
    (this.$refs.form as Vue & { validate: () => boolean }).validate();
    this.$emit("sendBank", this.bankname, this.account);
  }
}
</script>
<style>
</style>