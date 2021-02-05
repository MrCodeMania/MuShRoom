<template>
  <v-main
  style="background-color: #FFECB3">
    <Header/>

    <!-- 부모 row -->
    <v-row
    no-gutters>

      <!-- 왼쪽 컴포넌트들 -->
      <v-col cols="8"
      class="flex-grow-0 
      flex-shrink-0
      pa-4">
      
        <!-- 페이지 확인, 넘기기 -->
        <v-row
        no-gutters
        style="height:10vh;">
          <v-card
          color="amber lighten-4"
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
              color="amber lighten-4"
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
              color="amber lighten-4"
              :disabled="page == 5 || page == length"
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
          elevation="0"
          width="100%"
          height="100%">
            <MusicBoard :pageData="pageData" :length="length"
            v-on:add="addPage" v-on:remove="removePage"
            :page="page"
            />
          </v-card>
        </v-row>
      </v-col>

      <!-- 오른쪽 컴포넌트들 -->
      <v-col cols="4"
      class="flex-grow-0 
      flex-shrink-0
      pa-4">
        <!-- 매트로놈 -->
        <v-row
        no-gutters
        style="height:20vh">
          <v-card
          elevation="0"
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
          elevation="0"
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
      length: 1, // 전체 페이지 수
      pageList: [  // 페이지 리스트
        [] // 저장된 데이터
      ],
      pageData: [],  // 현재 페이지 데이터
    }
  },
  methods: {
    //  페이지 저장
    savePage(data){
      this.pageList.forEach((obj, idx) => {
        if(idx == this.page-1)
          obj.data = data;
      });
    },
    //  페이지 불러오기
    findPage() {
      this.pageList.forEach((obj, idx) => {
        if(idx == this.page-1)
          this.pageData = obj.data;
      });
    },
    //  페이지 왼쪽 이동
    moveLeft() {
      this.savePage(this.pageData);
      this.page -= 1;
      this.findPage();
    },
    //  페이지 오른쪽 이동
    moveRight() {
      this.savePage(this.pageData);
      this.page += 1;
      this.findPage();
    },
    //  페이지 생성
    addPage(data){
      this.savePage(data);
      this.length += 1;
      this.page += 1;
      this.pageList.push({page: this.page, data: []});
      this.findPage();
    },
    // 페이지 삭제
    removePage(){
      this.pageList.forEach((obj, idx) => {
        if(idx == this.page-1)
          this.pageList.splice(idx, 1);
      })
      this.length -= 1;
      if(this.page != 1)
        this.page -= 1;
      this.findPage();
    }
  },
}
</script>

<style>

</style>