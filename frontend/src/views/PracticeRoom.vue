<template>
  <v-main
  class="main-color">
    <Header/>

    <!-- 부모 row -->
    <v-row no-gutters>
      <!-- 왼쪽 컴포넌트들 -->
      <v-col
        cols="8"
        class="flex-grow-0 
      flex-shrink-0
      pa-4"
      >
        <!-- 페이지 확인, 넘기기 -->
        <v-row no-gutters style="height:80vh;">
          <v-card
            elevation="0"
            height="100%"
            width="100%"
            color="#00ff0000">
            <v-tabs
            background-color="#00ff0000"
            slider-color="black"
            color="white"
            v-model="page">
              <v-tab
                class="rounded-t"
                style="background-color: grey;"
                v-for="i in range"
                :key="i">
                  {{ i }}
              </v-tab>
            </v-tabs>
            <v-tabs-items v-model="page"
            class="rounded-tr">
              <v-tab-item
              v-for="i in range"
              :key="i"
              :transition="false" :reverse-transition="false">
                <v-card elevation="0" width="100%" height="100%">
                  <MusicBoard :page="page" 
                  v-on:add="moveRight"
                  v-on:remove="moveLeft"/>
                </v-card>
                </v-tab-item>
            </v-tabs-items>
          </v-card>
        </v-row>

      </v-col>

      <!-- 오른쪽 컴포넌트들 -->
      <v-col
        cols="4"
        class="flex-grow-0 
      flex-shrink-0
      pa-4"
      >
        <!-- 매트로놈 -->
        <v-row
        no-gutters
        style="height:18vh">
          <v-card
          elevation="0"
          width="100%"
          height="100%">
            <Metronome/>
          </v-card>
        </v-row>

        <!-- 파일 목록 -->
        <v-row 
        v-if="status === 'Master'"
        no-gutters
        style="height:72vh">
          <v-card
          elevation="0"
          width="100%"
          height="100%"
          class="component-color">
            <Record :page="page"/>
          </v-card>
        </v-row>
      </v-col>
    </v-row>
  </v-main>
</template>

<script>
import Header from '@/components/common/Header.vue';
import Metronome from '@/components/practiceroom/Metronome.vue';
import MusicBoard from '@/components/MusicBoard.vue';
import Record from '@/components/record.vue';

export default {
  components: {
    Header,
    Metronome,
    MusicBoard,
    Record,
  },
  created() {
    this.$store.commit('setData');

    // Status 저장

    // URL을 읽거나 DB에서 받거나
    // Status를 얻은 뒤, vuex에 저장
    this.$store.commit("pushStatus", "Master");
    //this.$store.commit("pushStatus", "ReadOnly");
    
    this.status = this.$store.state.status;
  },
  data() {
    return {
      page: 0, //  현재 페이지
    };
  },
  computed: {
    length: function() {
      return this.$store.getters.getPageLength;
    },
    range() {
      let pages = [];
      for(let i=1;i<=this.length;i++)
        pages.push(i);
      return pages;
    }
  },
  methods: {
    //  페이지 왼쪽 이동
    moveLeft() {
      this.page = this.page == 0 ? 0 : this.page - 1;
    },
    //  페이지 오른쪽 이동
    moveRight() {
      this.page = this.page == 4 ? 4 : this.page + 1;
    },
  },
};
</script>

<style></style>
