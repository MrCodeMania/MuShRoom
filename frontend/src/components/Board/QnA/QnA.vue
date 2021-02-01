<template>
  <div>
    <v-sheet color="grey" elevation="2" width="100%">
      <v-form class="text-center"
        ><v-container
          ><v-row class="mt-4"
            ><v-col :cols="4"
              ><v-overflow-btn
                width="400px"
                color="white "
                allow-overflow
                :items="dropdown_edit"
                hide-details
                solo
                label="분류"
              ></v-overflow-btn></v-col
            ><v-col :cols="8"
              ><v-text-field
                label="제목"
                solo
                hide-details
              ></v-text-field></v-col
          ></v-row>
          <v-divider></v-divider
          ><v-row
            ><v-col><v-textarea solo label="내용"></v-textarea></v-col
          ></v-row>
          <v-divider></v-divider>
          <v-row
            ><v-col
              ><v-file-input
                solo
                label="첨부파일"
                truncate-length="15"
              ></v-file-input></v-col></v-row></v-container
      ></v-form>
      <v-divider></v-divider
      ><v-row>
        <v-col align="end" :cols="11"
          ><v-btn color="success">보내기</v-btn></v-col
        ></v-row
      ></v-sheet
    >
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class QnA extends Vue {
  private dropdown_edit = [
    { text: "강의" },
    { text: "연습실" },
    { text: "커뮤니티" },
    { text: "결제" },
    { text: "기타" },
  ];
}
</script>

<style>
</style>