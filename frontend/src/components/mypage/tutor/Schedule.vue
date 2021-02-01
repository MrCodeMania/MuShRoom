<template>
  <div class="container">
    <h1>스케줄 관리(강사)</h1>
    <v-row>
      <v-col>
        <v-card>
          <v-date-picker  no-title full-width locale="kr-ko" v-model="picker"></v-date-picker>
        </v-card>
      </v-col>
      <v-col>
        <v-card>
          <div class="d-flex align-center justify-center">
            <v-card-title @click="prev"> {{ beforeDate }} </v-card-title>
            <v-btn fab text small color="grey darken-2" @click="prev">
                <v-icon small>mdi-chevron-left</v-icon>
            </v-btn>
            <v-card-title> {{ targetDate }} </v-card-title>
            <v-btn fab text small color="grey darken-2" @click="next">
                <v-icon small>mdi-chevron-right</v-icon>
            </v-btn>
            <v-card-title @click="next"> {{ afterDate }} </v-card-title>
          </div>
          <v-divider class="mx-4"></v-divider>
          <v-card-actions justify="around">
            <v-btn
              color="deep-purple lighten-2"
              text
              @click="selectMorning"
            >
              오전
            </v-btn>
            <v-btn
              color="deep-purple lighten-2"
              text
              @click="selectAfternoon"
            >
              오후
            </v-btn>
            <v-btn
              color="deep-purple lighten-2"
              text
              @click="selectAll"  
            >
              전체
            </v-btn>
            <v-spacer></v-spacer>
            <v-btn
              color="deep-purple lighten-2"
              text
              @click="removeAll"
            >
              전체취소
            </v-btn>
          </v-card-actions>
          <v-card-text>
            <v-chip-group
              v-model="selection"
              multiple
              column
              active-class="deep-purple accent-4 white--text"
            >
              <v-chip v-for="(time,n) in times" :key="n" :value="time">{{ time }}</v-chip>
 
            </v-chip-group>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="deep-purple lighten-2"
              text
              @click="updateSchedule"
            >
              등록하기
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch } from "vue-property-decorator";

@Component
export default class Schedule extends Vue{
  
  public timezoneOffset = new Date().getTimezoneOffset() * 60000;
  public timezoneDate = new Date(Date.now() - this.timezoneOffset);

  public picker = this.timezoneDate.toISOString().substring(0,10);

  public tempDate = this.picker.split('-')
  public pre = new Date(new Date(this.picker).setDate(new Date(this.picker).getDate() - 1)).toISOString().substring(5,10);
  public post = new Date(new Date(this.picker).setDate(new Date(this.picker).getDate() + 1)).toISOString().substring(5,10);
  public selection: string[] = []
  public times: string[] = []

  created() {
    for(let i=0;i<11;i++){
      let time = '0'+(i+9);
      time = time.substr(time.length-2);
      this.times.push(time+':00');
      this.times.push(time+':30');
    }
    this.selection = this.times
  }

  public availableTimes: object|string|string[]|any = { date: "", time: [] }



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
  onPickerChanged(value: string, oldValue: string) {
    this.pre = new Date(new Date(this.picker).setDate(new Date(this.picker).getDate() - 1)).toISOString().substring(5,10);
    this.post = new Date(new Date(this.picker).setDate(new Date(this.picker).getDate() + 1)).toISOString().substring(5,10);
    this.tempDate = this.picker.split('-')
  }

  selectMorning() {
    for (let i=0;i<6;i++){
      if (!(this.times[i] in this.selection)){
        this.selection.push(this.times[i])
      }
    }
  }
  selectAfternoon() {
    for (let i=6;i<22;i++){
      if (!(this.times[i] in this.selection)){
        this.selection.push(this.times[i])
      }
    }
  }
  selectAll() {
    this.selection = this.times
  }
  removeAll() {
    this.selection = []
  }

  updateSchedule() {
    this.availableTimes.date = this.picker
    this.availableTimes.time = this.selection
    console.log(this.availableTimes)
  }



}
</script>

<style>

</style>