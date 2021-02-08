import { Time } from 'tone';
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    url: "",
    fileName: "",
    myName: "여기에 이름",
    data: { // 연습실이 갖는 전체 데이터
      musicBoard: [{  //  왼쪽 컴포넌트
        list: [{  // 추가된 음악 리스트
          id: Number,
          url: String,
          fileName: String,
          timestamp: Time,
          distortion: {
            object: Object,
            value: Number
          },
          volume: {
            object: Object,
            value: Number
          },
          gain: {
            object: Object,
            value: Number
          },
        }]
      }],
      recordBoard: [{ // 오른쪽 컴포넌트
        id: Number,
        url: String,
        fileName: String,
      }]
    }
  },
  mutations: {
    pushURL(state,e=""){
      state.url = e;
    },
    pushName(state, name = "") {
      state.fileName= name
    },
    //  새로고침 시 data 초기화
    setData(state) {
      state.data = {
        musicBoard: [{ list: [] }],
        recordBoard: []
      };
    },
    //  recordBoard에 음악 추가
    updateRecord(state, record) {
      state.data.recordBoard.push(record);
    },
    //  recordBoard에서 음악 삭제
    deleteRecord(state, idx) {
      state.data.recordBoard.splice(idx, 1);
    },
    //  recordBoard에서 musicBoard으로 음악 추가
    addMusic(state, { page, record }) {
      state.data.musicBoard[page - 1].list.push({
        url: record.url,
        fileName: record.fileName,
        timestamp: '',
        distortion: {
          object: null,
          value: 0,
        },
        volume: {
          object: null,
          value: -5,
        },
        gain: {
          object: null,
          value: 0,
        }
      });
      console.log(state.data.musicBoard[page - 1].list);
    },
    // player에서 바꾼 option update
    updateMusic(state, {page, music}) {
      state.data.musicBoard[page - 1].list.splice(music.id, 1, music);
    },
    // musicBoard에서 음악 삭제
    deleteMusic(state, { page, idx }) {
      state.data.musicBoard[page - 1].list.splice(idx, 1);
      // state.data.musicBoard[page-1].list.splice(idx, 1);
      console.log(state.data.musicBoard[page - 1].list);
    },
    //  musicBoard에 페이지 추가
    addPage(state, pageIdx) {
      state.data.musicBoard.splice(pageIdx, 0, {
        list: []
      });
      console.log(state.data.musicBoard);
    },
    //  musicBoard에서 페이지 삭제
    removePage(state, pageIdx) {
      state.data.musicBoard.splice(pageIdx-1, 1);
      console.log(state.data.musicBoard);
    }
  },
  getters: {
    getURL(state) {
      return [state.url, state.fileName];
    },
    // return 페이지 개수
    getPageLength(state) {
      return Object.keys(state.data.musicBoard).length;
    },
    // return recordBoard
    getRecords(state) {
      return state.data.recordBoard;
    },
    // return page 번호와 일치하는 musicBoard
    getBoard: (state) => (page) => {
      return state.data.musicBoard[page-1].list;
    },
  },
  actions: {
  },
  modules: {
  }
})
