import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    url: "",
    fileName: "",
    helpShow: false,
    pageLen: 1,
    data: { // 연습실이 갖는 전체 데이터
      musicBoard: [{ 
        pageName:"New Page",
        idx: 0,
        list: [] 
      }, { 
        pageName:"New Page",
        idx: 0,
        list: [] 
      }, { 
        pageName:"New Page",
        idx: 0,
        list: [] 
      }, { 
        pageName:"New Page",
        idx: 0,
        list: [] 
      }, { 
        pageName:"New Page",
        idx: 0,
        list: [] 
      }],
      recordBoard: [] // 오른쪽 컴포넌트
    },
    status: "",
    recordStartState: "",
    shareUrl: []
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
        musicBoard: [{ 
          pageName:"New Page",
          idx: 0,
          list: [] 
        }, { 
          pageName:"New Page",
          idx: 0,
          list: [] 
        }, { 
          pageName:"New Page",
          idx: 0,
          list: [] 
        }, { 
          pageName:"New Page",
          idx: 0,
          list: [] 
        }, { 
          pageName:"New Page",
          idx: 0,
          list: [] 
        }],
        recordBoard: []
      };
    },
    //  recordBoard에 음악 추가
    updateRecord(state, record) {
      state.data.recordBoard.push(record);
    },
    //  recordBoard에서 음악 삭제
    deleteRecord(state, idx) {
      console.log(idx);
      state.data.recordBoard.splice(idx, 1);
    },
    //  recordBoard에서 musicBoard으로 음악 추가
    addMusic(state, {
      page,
      record
    }) {
      state.data.musicBoard[page].list.push({
        id: state.data.musicBoard[page].idx++,
        url: record.downloadURL,
        fileName: record.fileName,
        timestamp: record.timestamp,
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
      page,
      music
    }) {
      state.data.musicBoard[page].list.splice(music.id, 1, music);
    },
    // musicBoard에서 음악 삭제
    deleteMusic(state, {
      page,
      idx
    }) {
      state.data.musicBoard[page].list.splice(idx, 1);
      
      if(state.data.musicBoard[page].idx > 0)
        state.data.musicBoard[page].idx--;

      for(let i = idx; i < state.data.musicBoard[page].list.length; i++)
        state.data.musicBoard[page].list[i].id--;      
    },
    //  musicBoard에 페이지 추가
    addPage(state, pageIdx) {
      state.data.musicBoard[pageIdx] = { 
        pageName:"New Page",
        idx: 0,
        list: [] 
      };
      state.pageLen += 1;
    },
    //  musicBoard에서 페이지 삭제
    removePage(state, pageIdx) {
      for(let i = pageIdx; i < state.pageLen; i++)
        state.data.musicBoard[i] = state.data.musicBoard[i + 1];
      
      state.data.musicBoard[state.pageLen - 1] = { 
          pageName:"New Page",
          idx: 0,
          list: [] 
        };

      state.pageLen -= 1;
    },
    updatePageName(state, {pageIdx, pageName}) {
      console.log(pageIdx, pageName);
      state.data.musicBoard[pageIdx].pageName = pageName;
      
      console.log(state.data.musicBoard[pageIdx].pageName);
    },
    pushStatus(state, status) {
      state.status = status;
      console.log(state.status);
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
    // return 페이지 개수
    getPageLength(state) {
      return state.pageLen;
    },
    // return recordBoard
    getRecords(state) {
      return state.data.recordBoard;
    },
    // return page 번호와 일치하는 musicBoard
    getBoard: (state) => (page) => {
      return state.data.musicBoard[page].list;
    },
    getRC(state) {
      return state.recordStartState;
    }
  },
  actions: {},
  modules: {}
})