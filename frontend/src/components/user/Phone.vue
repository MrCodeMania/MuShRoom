<template>
  <v-form ref="form">
    <span>핸드폰 번호</span>
    <v-container>
      <v-row>
        <v-col>
          <v-text-field
            solo
            v-model="start"
            label="010"
            required
          ></v-text-field>
        </v-col>
        <v-col>
          <v-text-field solo v-model="mid" label="XXXX" required></v-text-field>
        </v-col>
        <v-col>
          <v-text-field solo v-model="end" label="XXXX" required></v-text-field>
        </v-col>
        <v-btn @click="phoneCheck">check</v-btn>
      </v-row>
    </v-container>
  </v-form>
</template>
<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
@Component
export default class Phone extends Vue {
  private phoneNumber = "";
  private start = "";
  private mid = "";
  private end = "";
  private phoneCheck() {
    this.phoneNumber = this.start + this.mid + this.end;
    console.log(this.phoneNumber);
    this.$emit("emitEvent", this.phoneNumber);
  }
}
</script>
<style>
</style>