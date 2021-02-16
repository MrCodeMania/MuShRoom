import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    url: "",
    fileName: "",
    helpShow: false,
    data: { // 연습실이 갖는 전체 데이터
      musicBoard: { //  왼쪽 컴포넌트
        idx: 0,
        list: [] // 추가된 음악 리스트
      },
      recordBoard: [] // 오른쪽 컴포넌트
    },
    status: "",
    recordStartState: "",
    shareUrl: [],
    idx: 0
  },
  mutations: {
    helpShowChange(state) {
      state.helpShow = !state.helpShow;
    },
    pushURL(state, e = "") {
      state.url = e;
    },
    pushName(state, name = "") {
      state.fileName = name;
    },
    //  새로고침 시 data 초기화
    setData(state) {
      state.data = {
        musicBoard: { 
          idx: 0,
          list: []
        },
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
    addMusic(state, {
      record
    }) {
      state.data.musicBoard.list.push({
        id: state.idx++,
        // id: ++state.idx + record.downloadURL,
        url: record.downloadURL,
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
        },
        reverb: {
          object: null,
          value: 0,
        }
      });
    },
    // player에서 바꾼 option update
    updateMusic(state, {
      music
    }) {
      state.data.musicBoard.list.splice(music.id, 1, music);

    },
    // musicBoard에서 음악 삭제
    deleteMusic(state, {
      idx
    }) {
      state.data.musicBoard.list.splice(idx, 1);
      state.data.musicBoard.idx--;
    },
    pushStatus(state, status) {
      state.status = status;
    },
    pushShareUrl(state, shareUrl) {
      state.shareUrl = shareUrl;
    },

    setRC(state, start = "") {
      state.recordStartState = start
    }
  },
  getters: {
    getHelp(state) {
      return state.helpShow;
    },
    getURL(state) {
      return [state.url, state.fileName];
    },
    // return recordBoard
    getRecords(state) {
      return state.data.recordBoard;
    },
    // return musicBoard
    getBoard: (state) => {
      return state.data.musicBoard.list;
    },
    getRC(state) {
      return state.recordStartState;
    }
  },
  actions: {},
  modules: {}
})