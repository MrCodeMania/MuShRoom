<template>
  <div id="curri">
    <v-timeline
    dense>
      <div v-for="(section, idx) in sections"
      :key="section.id">
        <v-list>
          <v-list-group>
            <template v-slot:activator>
              <v-list-item-title>
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
                        {{ section.sectionName }}
                      </h2>
                      <v-spacer></v-spacer>
                      <v-btn
                      v-if="isTaken"
                      dark
                      :color="color[idx]+' darken-4'"
                      to="/detail/feedback">
                        피드백 신청
                      </v-btn>
                    </v-card-title>
                  </v-card>
                </v-timeline-item>
              </v-list-item-title>
            </template>

            <v-list-item-content>
              <div
              v-for="(lecture, idx) in section.lectureList"
              :key="lecture.id">
                <v-timeline-item
                color="amber lighten-1"
                fill-dot
                left
                small
                class="pr-16"
                >
                  <v-card
                  class="mr-4">
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
                        <v-col md="3">
                          <v-card-text class="p-0 mt-2">
                            {{ lecture.id }}
                          </v-card-text>
                        </v-col>
                        <v-col md="6">
                          <v-progress-linear
                          v-if="isTaken"
                          v-model="progresses[idx].progress"
                          height="25"
                          color="amber darken-4"
                          >
                            <strong>{{ Math.ceil(progresses[idx].progress) }}%</strong>
                          </v-progress-linear>
                          <v-progress-linear
                          v-else
                          height="25"
                          color="amber darken-4"
                          >
                          </v-progress-linear>
                        </v-col>
                        <v-col md="2">
                          <v-btn
                          v-if="isTaken"
                          dark
                          color="amber darken-3"
                          to="/lesson">
                            강의 보러가기
                          </v-btn>
                        </v-col>
                      </v-row>
                    </v-container>
                  </v-card>
                </v-timeline-item>
              </div>
            </v-list-item-content>
          </v-list-group>
        </v-list>
      </div>
    </v-timeline>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import ClassDataService from '@/service/ClassDataService';
import { mapState } from 'vuex';

@Component({
  computed: {
    ...mapState(['userInfo'])
  }
})

export default class Curriculum extends Vue {
  @Prop(Boolean) isTaken!: boolean;
  private color: string[] = ['purple lighten-2', 'cyan lighten-1', 'red lighten-1', 'green lighten-1'];
  private userInfo!: {[key: string]: any};  // 사용자 정보
  private cid = this.$route.params.cid; // 조회하려는 클래스 id
  private sections: object[] = [];  // 현재 클래스의 섹션 리스트
  private progresses!: object[]; // 현재 클래스의 수강률

  created() {
    // 현재 클래스의 섹션 리스트 조회
    ClassDataService.getAllSection(this.cid)
    .then((response) => {
      this.sections = response.data;
      // 수강 중인 경우
      if(this.isTaken)
        this.setProgress;
    })
    .catch((error) => {
      console.log(error);
    });
  }

  // 수강률 조회 method
  setProgress(){
    const array = this.userInfo.lectureProgress;
    array.forEach((element: {[key: string]: any}) => {
      if(element.classId == this.cid)
        this.progresses.push({ 'lectureId' : element.lectureId, 'progress' : element.progress });
    });
  }
}
</script>

<style scoped lang="scss">
::v-deep .v-list-item {
  padding: 0 16px 0 0;
}
</style>