<template>
  <div class="container">
    <h1>피드백 관리(강사)</h1>
    <v-row>
      <v-col>
        <v-card>
          <v-date-picker  no-title full-width locale="kr-ko" v-model="picker"></v-date-picker>
        </v-card>
      </v-col>
      <v-col>
        <v-card>
          <div class="d-flex align-center justify-center">
            <v-card-title> {{ beforeDate }} </v-card-title>
            <v-btn fab text small color="grey darken-2" @click="prev">
                <v-icon small>mdi-chevron-left</v-icon>
            </v-btn>
            <v-card-title> {{ targetDate }} </v-card-title>
            <v-btn fab text small color="grey darken-2" @click="next">
                <v-icon small>mdi-chevron-right</v-icon>
            </v-btn>
            <v-card-title> {{ afterDate }} </v-card-title>
          </div>
          <v-divider class="mx-4"></v-divider>
          <div  v-for="(schedule,n) in schedules" :key="n">
            <v-card-text v-if="picker === schedule.date">
              <v-card class="mb-3" v-for="(feedback,n) in schedule.feedbacks" :key="n">
                <v-card-text class="d-flex align-center">
                    <div class="d-flex justify-space-around">
                      <div class="schedule-text">{{ feedback.time }}</div>
                      <div class="schedule-text">{{ feedback.student }}</div>
                      <div class="schedule-text">{{ feedback.class }}</div>
                    </div>
                    <v-spacer></v-spacer>
                    <v-btn
                      color="amber"
                      @click="dialog = true"
                    >관리</v-btn>
                </v-card-text>
              </v-card>
            </v-card-text>
          </div>
        </v-card>
      </v-col>
    </v-row>
    <v-dialog
      v-model="dialog"
      persistent
      max-width="290"
    >
      <v-card>
        <v-card-title class="headline">
          피드백 일정 취소
        </v-card-title>
        <v-card-text> 해당 일정을 취소하겠습니까.</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="red darken-1"
            text
            @click="dialog = false"
          >
            아니요
          </v-btn>
          <v-btn
            color="green darken-1"
            text
            @click="postpone"
          >
            예
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch } from "vue-property-decorator";

@Component
export default class Feedback extends Vue{
  public dialog = false;
  public timezoneOffset = new Date().getTimezoneOffset() * 60000;
  public timezoneDate = new Date(Date.now() - this.timezoneOffset);

  public picker = this.timezoneDate.toISOString().substring(0,10);

  public tempDate = this.picker.split('-')
  public pre = new Date(new Date(this.picker).setDate(new Date(this.picker).getDate() - 1)).toISOString().substring(5,10);
  public post = new Date(new Date(this.picker).setDate(new Date(this.picker).getDate() + 1)).toISOString().substring(5,10);
  // public feedbacks: string[] = []
  public schedules: object[]|string|string[] = [
    { date: "2021-01-27", 
      feedbacks: [
        { student: "김학생", class: "바이올린", time: "09:00" },
        { student: "이학생", class: "바이올린", time: "09:30" },
        { student: "박학생", class: "바이올린", time: "10:00" },
        { student: "황학생", class: "바이올린", time: "10:30" },
        { student: "공학생", class: "바이올린", time: "17:00" },
        { student: "민학생", class: "바이올린", time: "17:30" },
        { student: "송학생", class: "바이올린", time: "19:00" }
      ]},
    { date: "2021-01-28", 
      feedbacks: [
        { student: "김학생", class: "바이올린", time: "09:00" },
        { student: "이학생", class: "바이올린", time: "09:30" },
        { student: "라학생", class: "바이올린", time: "10:00" },
        { student: "진학생", class: "바이올린", time: "12:30" },
        { student: "백학생", class: "바이올린", time: "17:00" },
        { student: "황학생", class: "바이올린", time: "17:30" },
        { student: "우학생", class: "바이올린", time: "19:00" }
      ]},
    { date: "2021-01-31", 
      feedbacks: [
        { student: "김학생", class: "바이올린", time: "09:00" },
        { student: "박학생", class: "바이올린", time: "09:30" },
        { student: "이학생", class: "바이올린", time: "10:00" },
        { student: "수학생", class: "바이올린", time: "11:30" },
        { student: "신학생", class: "바이올린", time: "17:00" },
        { student: "최학생", class: "바이올린", time: "17:30" },
        { student: "길학생", class: "바이올린", time: "19:00" }
      ]},
  ]



  created() {
    // db에서 예약되어있는 피드백목록을 받아온다
    
  }


  prev () {
      this.picker = new Date(new Date(this.picker).setDate(new Date(this.picker).getDate() - 1)).toISOString().substring(0,10)
  }

  next () {
      this.picker = new Date(new Date(this.picker).setDate(new Date(this.picker).getDate() + 1)).toISOString().substring(0,10)
  }

  get beforeDate() {
    const tempDate = this.pre.split("-")
    return this.pre = tempDate[0] + "월 " + tempDate[1] + "일" 
  }

  get afterDate() {
    const tempDate = this.post.split("-")
    return this.post = tempDate[0] + "월 " + tempDate[1] + "일" 
  }

  get targetDate () {
    const tempDate = this.picker.split("-")
    return tempDate[0] + "년 " + tempDate[1] + "월 " + tempDate[2] + "일" 
  }


  @Watch('picker')
  onPickerChanged() {
    this.pre = new Date(new Date(this.picker).setDate(new Date(this.picker).getDate() - 1)).toISOString().substring(5,10);
    this.post = new Date(new Date(this.picker).setDate(new Date(this.picker).getDate() + 1)).toISOString().substring(5,10);
    this.tempDate = this.picker.split('-')
  }

  postpone() {
    this.dialog = false;
    console.log("예")
  }

}
</script>

<style>

.schedule-text {
  font-size: 1.25em;
  color: black;
}


</style>