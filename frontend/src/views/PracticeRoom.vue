<template>
  <v-main style="background-color: #FFECB3">
    <Header />

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
        <v-row no-gutters style="height:10vh;">
          <v-card
            color="amber lighten-4"
            elevation="0"
            height="100%"
            width="100%"
            class="d-flex align-end"
          >
            <v-container
            class="text-center
            align-end">
              <v-btn
              fab
              icon 
              color="black"
              :disabled="page == 1"
              @click="moveLeft()">
                <v-icon>
                  mdi-chevron-left
                </v-icon>
              </v-btn>
              <v-card elevation="0" outlined class="d-inline-block pa-2 mx-1">
                {{ page }}/{{ length }}
              </v-card>
              <v-btn
              fab
              icon 
              color="black"
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
        <v-row no-gutters style="height:80vh">
          <v-card elevation="0" width="100%" height="100%">
            <MusicBoard :page="page" 
            v-on:add="moveRight"
            v-on:remove="moveLeft"/>
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
        style="height:70vh">
          <v-card
          elevation="0"
          width="100%"
          height="100%">
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
      page: 1, //  현재 페이지
    };
  },
  computed: {
    length: function() {
      return this.$store.getters.getPageLength;
    }
  },
  methods: {
    //  페이지 왼쪽 이동
    moveLeft() {
      this.page = this.page == 1 ? 1 : this.page - 1;
    },
    //  페이지 오른쪽 이동
    moveRight() {
      this.page = this.page == 5 ? 5 : this.page + 1;
    },
  },
};
</script>

<style></style>
