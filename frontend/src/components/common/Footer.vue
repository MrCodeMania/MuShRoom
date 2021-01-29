<template>
  <div id="footer">
    <v-footer dark padless height="300">
      <v-card
        flat
        tile
        class="amber darken-1 white--text text-center"
        width="100%"
      >
        <v-card-text>
          <v-btn
            v-for="(category, i) in categories"
            :key="i"
            class="mx-4 white--text"
            depressed
            color="amber darken-1"
            :to="{ name: category.link }"
          >
            <div>
              {{ category.text }}
            </div>
          </v-btn>
        </v-card-text>

        <v-divider></v-divider>

        <v-card-text class="white--text">
          <div v-for="name in team" :key="name">
            {{ name }}
          </div>
        </v-card-text>
        <v-card-text class="white--text">
          {{ new Date().getFullYear() }} — <strong>브레멘 음악대</strong>
        </v-card-text>
      </v-card>
    </v-footer>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class Footer extends Vue {
  private categories: object = [
    { text: "공지사항", link: "notice" },
    { text: "1:1 문의사항", link: "voc-QnA" },
    { text: "자주 묻는 질문", link: "qlist" },
  ];
  private team: string[] = [
    "정수림",
    "권연욱",
    "송건호",
    "이건우",
    "이병희",
    "정지환",
  ];
}
</script>
