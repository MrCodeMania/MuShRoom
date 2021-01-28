<template>
  <div id="classList">
      <v-container fluid
      class="mt-16">
        <div class="ml-16">
            <v-card-title class="ml-16 text-h4">
                {{ name }}
            </v-card-title>
        </div>
        <div class="d-flex justify-center">
            <v-btn icon
            class="my-auto">
                <v-icon>mdi-chevron-left</v-icon>
            </v-btn>
            <v-card 
            v-for="card in cards"
            :key="card.title"
            class="d-inline-block mx-3"
            width="300">
                <v-btn
                text
                to="/detail/curriculum"
                class="p-0 text-center"
                height="200">
                    <v-img
                    :src="card.src"
                    class="white--text align-end"
                    gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                    width="300"
                    height="200"
                    >
                        <v-card-title
                        class="justify-center"
                        v-text="card.title"></v-card-title>
                    </v-img>
                </v-btn>

                <v-card-actions>
                    <v-spacer></v-spacer>

                    <v-btn icon>
                        <v-icon>mdi-heart-outline</v-icon>
                    </v-btn>

                </v-card-actions>
            </v-card>
            <v-btn icon
            class="my-auto">
                <v-icon>mdi-chevron-right</v-icon>
            </v-btn>
        </div>
    </v-container>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';

@Component
export default class ClassList extends Vue {
  private cards: object[] = [
    { title: '기타로 장범준 따라잡기', src: 'https://cdn.vuetifyjs.com/images/cards/house.jpg'},
    { title: '초보자도 할 수 있는 피아노 반주', src: 'https://cdn.vuetifyjs.com/images/cards/road.jpg'},
    { title: '오늘부터 바이올리니스트', src: 'https://cdn.vuetifyjs.com/images/cards/plane.jpg'},
    { title: '드럼의 모든 것', src: 'https://cdn.vuetifyjs.com/images/cards/house.jpg'},
  ];
  @Prop(String) name!: string
}
</script>