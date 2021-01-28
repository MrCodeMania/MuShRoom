<template>
  <div class="home">
    <v-carousel v-model="model"
    height=300
    cycle
    hide-delimiter-background
    show-arrows-on-hover>
      <v-carousel-item
        v-for="(color, i) in colors"
        :key="color"
      >
        <v-sheet
          :color="color"
          height="100%"
          tile
        >
          <v-row
            class="fill-height"
            align="center"
            justify="center"
          >
            <div class="display-3 text-center">
              Slide {{ i + 1 }}
            </div>
          </v-row>
        </v-sheet>
      </v-carousel-item>
    </v-carousel>
    <div 
    class="mb-16"
    v-for="title in titles"
    :key="title">
      <ClassList :name="title" />
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import ClassList from '@/components/class/ClassList.vue';

@Component({
  components: {
    ClassList
  },
})
export default class Home extends Vue {
  private model = 0;
  private colors: string[] = [
    'primary',
    'secondary',
    'yellow darken-2',
    'red',
    'orange',
  ];
  private titles: string[] =[
    'Top 10 인기 강의',
    '신규 강의',
    '꾸준히 사랑받는 강의'
  ]
}
</script>
