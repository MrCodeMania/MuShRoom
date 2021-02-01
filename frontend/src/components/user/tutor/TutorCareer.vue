<template>
  <div>
    <v-col cols="12">
      <v-form ref="form" lazy-validation v-model="valid">
        <!--악기선택 창-->
        <p>강의 악기 선택</p>
        <v-autocomplete
          solo
          v-model="careerForm.instrument"
          :items="items"
          auto-select-first
          clearable
          hide-details
          no-data-text="찾으시는 악기가 없습니다"
        ></v-autocomplete>
        <v-divider></v-divider>
        <div>경력</div>
        <v-textarea
          solo
          v-model="careerForm.careertext"
          name="input-7-4"
          label="경력사항 입력"
          class="mt-2"
          hide-details
        ></v-textarea>
        <v-divider></v-divider>
        <div>수상이력</div>
        <v-textarea
          solo
          v-model="careerForm.award"
          name="input-7-4"
          label="수상 이력"
          class="mt-2"
          hide-details
        ></v-textarea>
        <v-divider></v-divider>
        <!--파일입력-->
        <div>증빙자료</div>
        <v-file-input
          solo
          show-size
          accept=".zip*"
          label="파일 업로드"
          prepend-inner-icon
          messages="경력사항을 압축파일로 업로드 해주세요"
          required
        ></v-file-input>
      </v-form>
    </v-col>
    <v-btn color="teal" :disabled="!valid" @click="validate"> 계속하기 </v-btn>
    <v-btn @click="prev"> 이전으로 </v-btn>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class extends Vue {
  private valid = false;

  private careerForm = [{ instrument: "" }, { careertext: "" }, { award: "" }];
  private items = ["피아노", "기타", "바이올린", "오보에", "단소"];
  private dialog = false;
  private selectInstrument = "";
  validate() {
    (this.$refs.form as Vue & { validate: () => boolean }).validate();
    console.log(this.careerForm);
    this.$emit("sendCareer", this.careerForm);
  }
  prev() {
    console.log("123");
    this.$emit("stepPrev");
  }
}
</script>
<style>
</style>