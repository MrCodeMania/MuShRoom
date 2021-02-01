<template>
  <v-card class="mx-auto mb-n2" max-width="500">
    <v-toolbar color="indigo" dark>
      <v-toolbar-title>자주 묻는 질문</v-toolbar-title>

      <v-spacer></v-spacer>
    </v-toolbar>

    <v-container fluid>
      <v-row dense>
        <v-col v-for="card in cards" :key="card.title" :cols="card.flex">
          <v-card>
            <v-img
              :src="card.src"
              class="white--text align-end"
              gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
              height="200px"
            >
              <v-card-title v-text="card.title"></v-card-title>
            </v-img>

            <v-card-actions>
              <v-btn color="orange lighten-2" text> 답변 </v-btn>

              <v-spacer></v-spacer>

              <v-btn icon @click="card.show = !card.show">
                <v-icon>{{
                  card.show ? "mdi-chevron-up" : "mdi-chevron-down"
                }}</v-icon>
              </v-btn>
            </v-card-actions>

            <v-expand-transition>
              <div v-show="card.show">
                <v-divider></v-divider>

                <v-card-text>{{ card.text }} </v-card-text>
              </div>
            </v-expand-transition>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-card>
</template>

<script>
export default {
  data: () => ({
    cards: [
      {
        title: "수강신청은 어떻게 하나요?",
        src: "https://cdn.vuetifyjs.com/images/cards/house.jpg",
        show: false,
        text: "버튼클릭!",
        flex: 12,
      },
      {
        title: "강의 결제는 어떻게하나요?",
        src: "https://cdn.vuetifyjs.com/images/cards/road.jpg",
        show: false,
        text: "카드결제!",
        flex: 6,
      },
      {
        title: "이런 강의가 필요해요!",
        src: "https://cdn.vuetifyjs.com/images/cards/plane.jpg",
        show: false,
        text: "와우",
        flex: 6,
      },
    ],
  }),
};
</script>
<style>
</style>