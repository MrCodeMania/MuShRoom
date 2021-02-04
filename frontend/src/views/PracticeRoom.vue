<template>
  <v-main
  style="background-color: #FFF8E1">
    <Header/>

    <!-- 부모 row -->
    <v-row
    no-gutters>

      <v-col
      cols="1">

      </v-col>
      <!-- 왼쪽 컴포넌트들 -->
      <v-col cols="7"
      class="flex-grow-0 
      flex-shrink-0
      px-4">
      
        <!-- 페이지 확인, 넘기기 -->
        <v-row
        no-gutters
        style="height:10vh;">
          <v-card
          color="amber lighten-5"
          elevation="0"
          height="100%"
          width="100%"
          class="d-flex align-end">
            <v-container
            class="text-center
            align-end">
              <v-btn
              fab
              small
              elevation="0"
              color="amber lighten-5"
              :disabled="page == 1"
              @click="moveLeft()">
                <v-icon>
                  mdi-chevron-left
                </v-icon>
              </v-btn>
              <v-card 
              elevation="0"
              outlined
              class="d-inline-block pa-2 mx-1">
                {{page}}/{{length}}
              </v-card>
              <v-btn
              fab
              small
              elevation="0"
              color="amber lighten-5"
              :disabled="page == length"
              @click="moveRight()">
                <v-icon>
                  mdi-chevron-right
                </v-icon>
              </v-btn>
            </v-container>
          </v-card>
        </v-row>

        <!-- 녹음부스 -->
        <v-row
        no-gutters
        style="height:80vh">
          <v-card
          width="100%"
          height="100%">
            <MusicBoard :url="url"/>
          </v-card>
        </v-row>
      </v-col>

      <!-- 공백 -->
      <v-col cols="1">
      </v-col>

      <!-- 오른쪽 컴포넌트들 -->
      <v-col cols="3"
      class="flex-grow-0 
      flex-shrink-0
      px-4 pt-3">
        <!-- 매트로놈 -->
        <v-row
        no-gutters
        style="height:20vh">
          <v-card
          text
          width="100%"
          height="100%">
            <Metronome/>
          </v-card>
        </v-row>

        <!-- 파일 목록 -->
        <v-row
        no-gutters
        style="height:70vh">
          <v-card
          width="100%"
          height="100%">
            <Record/>
          </v-card>
        </v-row>
      </v-col>
    </v-row>

    <Footer/>
  </v-main>
</template>

<script>
import Header from '@/components/common/Header.vue';
import Footer from '@/components/common/Footer.vue';
import Metronome from '@/components/practiceroom/Metronome.vue';
import MusicBoard from '@/components/MusicBoard.vue';
import Record from '@/components/record.vue';

export default {
  components: {
    Header,
    Footer,
    Metronome,
    MusicBoard,
    Record
  },
  data() {
    return {
      page: 1,  //  현재 페이지
      length: 10, // 전체 페이지 수,
      url: 'http://i4a105.p.ssafy.io:8080/downloadFile/sample.mp3'
    }
  },
  methods: {
    //  페이지 왼쪽 이동
    moveLeft() {
      this.page = this.page != 1 ? this.page-1 : this.page;
    },
    //  페이지 오른쪽 이동
    moveRight() {
      this.page = this.page != this.length ? this.page+1 : this.page;
    },
  },
}
</script>

<style>

</style>