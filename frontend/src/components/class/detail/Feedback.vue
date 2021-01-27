<template>
  <div id="feedback">
    <v-card class="p-4 text-center">
      <v-row>
        <v-col>
          <v-card
          width="400"
          class="mx-auto mb-2 text-center"
          elevation="0"
          color="cyan darken-1"
          dark>
            <v-card-title
            class="justify-center">
              {{ title }}
            </v-card-title>
          </v-card>
          <div>
            <v-calendar
            ref="calendar"
            event-color="amber"
            :events="dates"
            @click:date="viewDay"
          ></v-calendar>
          </div>
        </v-col>
        <v-col>
          <v-card>
            <v-card-title
            class="justify-center">
              {{ date }}
            </v-card-title>
            <v-card-text
            class="p-0 pb-4">
              <v-row
              no-gutters
              v-for="time in times"
              :key="time[0]">
                <v-col
                v-for="part in time"
                :key="part">
                  <v-btn
                  :disabled="scheduled(part)">
                    {{ part }}
                  </v-btn>
                </v-col>
              </v-row>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class Feedback extends Vue {
  private schedules: {[key: string]: any}[] = [
    { date: '2021-01-26', timetable: ['14:30', '16:00', '18:00'] },
    { date: '2021-01-27', timetable: ['15:30', '16:00', '16:30', '18:30'] },
    { date: '2021-01-29', timetable: ['10:00', '19:00'] }
  ];
  private myFeedback: {[key: string]: any}[] = [
    { date: '2021-01-26', timetable: ['13:30', '16:00'] },
    { date: '2021-01-28', timetable: ['15:30'] },
    { date: '2021-01-29', timetable: ['14:00'] }
  ];
  private times: string[][] = [];
  private dates: object[] = [];
  private isClicked = false;
  private title = '';
  private name = '김철수';
  private date = new Date().toISOString().substr(0, 10);

  created() {
    for(let i=0;i<11;i++){
      let time = '0'+(i+9);
      time = time.substr(time.length-2);
      this.times.push([]);
      this.times[i].push(time+':00');
      this.times[i].push(time+':30');
    }
    this.schedules.forEach(obj => {
      this.dates.push({ start: obj['date'], name: '예약' });
    });
    this.title = this.name + ' 선생님 스케줄';
  }

  viewDay({ date }: { date: string}){
    this.date = date;
  }
  scheduled(part: string){
    this.schedules.forEach(obj => {
      if(obj.date === this.date){
        obj.timetable.forEach( (str: string) => {
          if(str === part)
            return true;
        });
      }
    });
    this.myFeedback.forEach(obj => {
      if(obj.date === this.date){
        obj.timetable.forEach( (str: string) => {
          if(str === part)
            return true;
        });
      }
    });

    return false;
  }
}
</script>