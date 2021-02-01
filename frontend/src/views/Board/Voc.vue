<template>
  <div>
    <div class="text-center">
      <v-sheet class="px-7 pt-7 pb-4 mx-auto text-center d-inline-block">
        <v-btn-toggle mandatory color="teal">
          <v-card-text>
            <v-btn
              v-for="(category, i) in categories"
              :key="i"
              plain
              class="mx-4 white--text"
              depressed
              color="teal darken-1"
              outlined
              :to="{ name: category.link }"
            >
              <div>
                {{ category.text }}
              </div>
            </v-btn>
          </v-card-text>
        </v-btn-toggle>
      </v-sheet>
    </div>
    <div>
      <v-sheet width="600px" class="mx-auto mb-10" color="white" elevation="2"
        ><router-view :key="$route.fullPath"></router-view
      ></v-sheet>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
@Component
export default class Board extends Vue {
  name = "Board";
  private categories: object = [
    { text: "공지사항", link: "notice" },
    { text: "1:1 문의사항", link: "voc-QnA" },
    { text: "자주 묻는 질문", link: "qlist" },
  ];
}
</script>
<style>
</style>