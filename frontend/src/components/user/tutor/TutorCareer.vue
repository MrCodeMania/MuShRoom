<template>
  <div>
    <v-col cols="12">
      <v-form ref="form" lazy-validation>
        <!--악기선택 창-->
        <p>강의할 악기 선택</p>
        <v-dialog v-model="dialog" persistent max-width="600px">
          <template v-slot:activator="{ on, attrs }">
            <v-sheet elevation="2" rounded>
              <v-btn color="teal" dark v-bind="attrs" v-on="on"
                ><v-icon>mdi-selection-search</v-icon>
              </v-btn>
              <span class="align-center">
                test
                {{ selectInstrument }}
              </span>
            </v-sheet>
          </template>
          <v-card>
            <v-card-title>
              <span class="headline">수강 악기 선택</span>
            </v-card-title>
            <v-card-text> 악기 선택 구현 중 </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="dialog = false">
                Close
              </v-btn>
              <v-btn color="teal darken-1" text @click="selectItems">
                Save
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-divider></v-divider>
        <div>경력</div>
        <v-divider></v-divider>
        <div>수상이력</div>
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
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class extends Vue {
  private dialog = false;
  private selectInstrument = "";
  private item = ["피아노", "기타", "바이올린", "오보에", "단소"];
  selectItems(s: string) {
    this.dialog = false;
    this.selectInstrument = s;
  }
}
</script>
<style>
</style>