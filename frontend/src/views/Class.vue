<template>
  <div id="class">
    <v-card
    class="mx-auto mt-10"
    max-width="80%"
    color="amber darken-3"
    dark>
        <div
        class="pt-6">
            <v-btn 
            x-large
            depressed
            color="amber darken-3"
            v-for="category in categories"
            :key="category.title"
            @click="addQuery(category.title)">
                {{ category.title }}
            </v-btn>
        </div>
        <v-divider
        dark>
        </v-divider>
        <v-btn 
        x-large
        depressed
        color="amber darken-3"
        v-for="instrument in instruments"
        :key="instrument"
        @click="addQuery(instrument)">
            {{ instrument }}
        </v-btn>
        <v-divider
        dark>
        </v-divider>
            <div class="pb-6">
                <v-btn 
                x-large
                depressed
                color="amber darken-3"
                v-for="step in steps"
                :key="step.title"
                @click="addQuery(step.title)">
                    {{ step.title }}
                </v-btn>
            </div>
    </v-card>
    <ClassAllList :name="query"/>
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
  private query: string[] = [];
  private index = -1;

  addQuery(word: string) {
      this.index = this.query.indexOf(word);
      if(this.index === -1)
        this.query.push(word);
      else
        this.query.splice(this.index, 1);
  }
  
}
</script>