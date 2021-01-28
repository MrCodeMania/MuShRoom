<template>
  <div id="class">
    <v-card
    class="mx-auto mt-10"
    max-width="80%"
    color="amber darken-3"
    dark>
        <v-btn 
        x-large
        height="80"
        depressed
        color="amber darken-3"
        v-for="category in categories"
        :key="category.title"
        @click="addQuery(category.title)">
            {{ category.title }}
        </v-btn>
        <v-divider
        class="m-0"
        dark>
        </v-divider>
        <v-btn 
        x-large
        height="80"
        depressed
        color="amber darken-3"
        v-for="instrument in instruments"
        :key="instrument"
        @click="addQuery(instrument)">
            {{ instrument }}
        </v-btn>
        <v-divider
        class="m-0"
        dark>
        </v-divider>
        <v-btn 
        x-large
        height="80"
        depressed
        color="amber darken-3"
        v-for="step in steps"
        :key="step.title"
        @click="addQuery(step.title)">
            {{ step.title }}
        </v-btn>
    </v-card>
    <v-card
    elevation="0"
    class="mx-auto">
      <v-card
      elevation="0"
      max-width="288"
      height="80"
      color="amber"
      class="mx-auto mt-6 p-3 pr-0">
        <div class="d-inline-block">
          <v-text-field
          v-model="keyword"
          solo>
          </v-text-field>
        </div>
        <v-btn
        class="mt-2 justify-center p-0"
        @click="searchWord"
        text>
          <v-icon>
            mdi-magnify
          </v-icon>
        </v-btn>
      </v-card>
    </v-card>
    <ClassAllList :filters="filters" 
    :query="query"
    @deleteFilter="addQuery"/>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import ClassAllList from '@/components/class/ClassAllList.vue';

@Component({
  components: {
    ClassAllList
  },
})
export default class Class extends Vue {
  private categories: object[] = [
    { title: '현악기', idx: 1 },
    { title: '금관악기', idx: 2 },
    { title: '목관악기', idx: 3 },
    { title: '타악기', idx: 4 },
    { title: '전자악기', idx: 5 },
    { title: '노래', idx: 6 },
  ];
  private instruments: string[] = [
      '바이올린', 
      '비올라', 
      '첼로', 
      '콘트라 베이스'
    ];
  private number = 1;
  private steps: object[] = [
      { level: 1, title: '초급' },
      { level: 2, title: '중급' },
      { level: 3, title: '고급' },
  ];
  private filters: string[] = [];
  private index = -1;
  private keyword = '';
  private query = '';

  addQuery(word: string) {
      this.index = this.filters.indexOf(word);
      if(this.index === -1)
        this.filters.push(word);
      else
        this.filters.splice(this.index, 1);
  }
  
  searchWord(){
    this.query = this.keyword;
  }
}
</script>