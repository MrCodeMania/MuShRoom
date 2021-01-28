<template>
  <div class="wrapper">
    <v-list shaped id="sidebar">
      <v-list-item>
        <v-list-item-avatar>
          <v-img src="https://cdn.vuetifyjs.com/images/john.png"></v-img>
        </v-list-item-avatar>
        <v-list-item-title v-text="user_info.nickname" class="nickname"/>
      </v-list-item>
      <v-divider style="color: black;"></v-divider>
      <v-list-item-group
        v-model="selectedItem"
        color="warning"
      >
      <div v-if="user_info.isStudent">
        <v-list-item
          v-for="(item, i) in student_items"
          :key="i"
          :to="{name: item.link}"
        >
          <v-list-item-content>
            <v-list-item-title v-text="item.text" class="menu_items"/>
          </v-list-item-content>
        </v-list-item>
      </div>
      <div v-else>
        <v-list-item
          v-for="(item, i) in tutor_items"
          :key="i"
          :to="{name: item.link}"
        >
          <v-list-item-content>
            <v-list-item-title v-text="item.text" class="menu_items"/>
          </v-list-item-content>
        </v-list-item>
        <v-list-item
          class="align-end"
          :to="{name: 'tutor-addlecture'}"
        >
          <v-list-item-content>
            <v-list-item-title class="menu_items">강의 등록</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </div>
      </v-list-item-group>
    </v-list>
    <router-view/>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";


@Component
export default class MyPage extends Vue{

  private user_info: any = {
    isStudent: false,
    nickname: "XXXXXXXXXXXXXX",
  }

  private selectedItem = null;

  private student_items: any = [
        { text: '회원정보확인', link: "user-info" },
        { text: '내 강의 목록', link: "user-lecture" },
        { text: '찜 목록', link: "user-like"},
        { text: '피드백', link: "user-feedback"},
        { text: '연습', link: "user-practice"},
      ];

  private tutor_items: any = [
      { text: '회원정보확인', link: "tutor-info" },
      { text: '내 강의', link: "tutor-lecture" },
      { text: '피드백 관리', link: "tutor-feedback" },
      { text: '스케줄 관리', link: "tutor-schedule" },
    ];

}
</script>
  
<style>
.wrapper {
  display: flex;
  width: 100%;
}

#sidebar {
  width: 250px;
  top: 0;
  left: 0;
  min-height: 800px;
  background: #7386D5;
  color: #fff;
}

.nickname {
  color: yellow;
}

.menu_items {
  color: black;
}
</style>