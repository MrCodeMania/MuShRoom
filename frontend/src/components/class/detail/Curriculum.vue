<template>
  <div id="curri">
    <v-timeline
    dense>
      <div v-for="(section, idx) in sections"
      :key="section.title">
        <v-timeline-item
          :color="color[idx]"
          fill-dot
          right
        >
          <v-card>
            <v-card-title :class="color[idx]">
              <v-icon
                dark
                size="42"
                class="mr-4"
              >
                mdi-music-note-quarter
              </v-icon>
              <h2 class="white--text font-weight-bold">
                {{ section.title }}
              </h2>
              <v-spacer></v-spacer>
              <v-btn
              v-if="isTaken"
              dark
              :color="color[idx]+' darken-4'">
                피드백 신청
              </v-btn>
            </v-card-title>
          </v-card>
        </v-timeline-item>

        <div
        v-for="lecture in section.lectures"
        :key="lecture.title">
          <v-timeline-item
          color="amber lighten-1"
          fill-dot
          left
          small
          >
            <v-card>
              <v-container>
                <v-row>
                  <v-col cols="12" md="1">
                    <v-icon
                    size="32"
                    class="mr-4"
                    >
                      mdi-music-note
                    </v-icon>
                  </v-col>
                  <v-col md="1">
                    <v-card-text class="p-0 mt-2">
                      {{ lecture.title }}
                    </v-card-text>
                  </v-col>
                  <v-col md="6">
                    <v-progress-linear 
                    v-model="lecture.value"
                    height="25"
                    color="amber darken-4"
                    >
                      <strong>{{ Math.ceil(lecture.value) }}%</strong>
                    </v-progress-linear>
                  </v-col>
                  <v-col md="4">
                    <v-btn
                    v-if="isTaken"
                    dark
                    color="amber darken-3">
                      강의 보러가기
                    </v-btn>
                  </v-col>
                </v-row>
              </v-container>
            </v-card>
          </v-timeline-item>
        </div>
      </div>
    </v-timeline>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';

@Component
export default class Curriculum extends Vue {
  @Prop(Boolean) isTaken!: boolean;
  private color: string[] = ['purple lighten-2', 'cyan lighten-1', 'red lighten-1', 'green lighten-1'];
  private sections: object[] = [];

  created() {
    if(this.isTaken){
      this.sections = [{title:'1강', lectures: [{title: '1-1강', value: 100}, {title: '1-2강', value: 100}, {title: '1-3강', value: 100}]},
        {title:'2강', lectures: [{title: '2-1강', value: 96}, {title: '2-2강', value: 92}, {title: '2-3강', value: 86}]},
        {title:'3강', lectures: [{title: '3-1강', value: 72}, {title: '3-2강', value: 32}, {title: '3-3강', value: 16}]},
        {title:'4강', lectures: [{title: '4-1강', value: 0}, {title: '4-2강', value: 0}, {title: '4-3강', value: 0}]}];  
    } else {
      this.sections = [{title:'1강', lectures: [{title: '1-1강', value: 0}, {title: '1-2강', value: 0}, {title: '1-3강', value: 0}]},
        {title:'2강', lectures: [{title: '2-1강', value: 0}, {title: '2-2강', value: 0}, {title: '2-3강', value: 0}]},
        {title:'3강', lectures: [{title: '3-1강', value: 0}, {title: '3-2강', value: 0}, {title: '3-3강', value: 0}]},
        {title:'4강', lectures: [{title: '4-1강', value: 0}, {title: '4-2강', value: 0}, {title: '4-3강', value: 0}]}];  

    }
  }
}
</script>