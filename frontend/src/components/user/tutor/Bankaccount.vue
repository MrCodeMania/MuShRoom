<template>
  <div>
    <v-col cols="12">
      <v-form ref="form" v-model="valid" lazy-validation>
        <v-text-field
          solo
          v-model="bankname"
          label="은행"
          required
        ></v-text-field>

        <v-text-field
          solo
          v-model="account"
          label="계좌번호"
          required
        ></v-text-field>

        <v-btn :disabled="!valid" color="teal" class="mr-4" @click="validate">
          확인
        </v-btn>
      </v-form>
    </v-col>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class extends Vue {
  private valid = true;
  private bankname = "";
  private account = "";
  validate() {
    console.log(this.valid);
  }
}
</script>
<style>
</style>