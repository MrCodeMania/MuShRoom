<template>
  <div id="classList">
      <v-container fluid
      class="my-16">
        <div class="ml-16">
            <v-card-title class="ml-16 text-h4">
                {{ name }} 의 검색 결과 입니다.
            </v-card-title>
        </div>
        <div class="d-flex justify-center">
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

                    <v-btn icon @click="clickHeart(card.title)">
                        <v-icon v-if="card.heart==0">mdi-heart-outline</v-icon>
                        <v-icon v-else>mdi-heart</v-icon>
                    </v-btn>

                </v-card-actions>
            </v-card>
        </div>
    </v-container>
    <div class="text-center mb-6">
        <v-pagination
        v-model="page"
        :length="10"
        ></v-pagination>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';

@Component
export default class ClassList extends Vue {
    @Prop(Array) name!: string[]
    private cards: {[key: string]: any}[] = [
        { title: '기타로 장범준 따라잡기', src: 'https://cdn.vuetifyjs.com/images/cards/house.jpg', heart: 1},
        { title: '초보자도 할 수 있는 피아노 반주', src: 'https://cdn.vuetifyjs.com/images/cards/road.jpg', heart: 0},
        { title: '오늘부터 바이올리니스트', src: 'https://cdn.vuetifyjs.com/images/cards/plane.jpg', heart: 0},
        { title: '드럼의 모든 것', src: 'https://cdn.vuetifyjs.com/images/cards/house.jpg', heart: 0},
    ];
    private page = 1;

    clickHeart(s: string) {
        this.cards.forEach(card => {
            if(card['title'] === s)
                card['heart'] = card['heart']==0 ? 1 : 0;
        });
    }
}
</script>