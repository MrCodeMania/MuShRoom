<template>
  <v-container>
    <v-row class="my-4">
      <h1>회원정보(강사)</h1>
    </v-row>
    <v-row>
      <v-card
      height= "300" width="600" class="d-flex flex-column mx-auto my-5 justify-center align-center" color="brown lighten-3"
      >
        <p class="infotext">프로필 사진</p>
        <v-avatar
        style="border-radius: 50%; border-color: black;"
        size="200"
        >
          <v-img :src="userInfo.img"></v-img>
        </v-avatar>
      </v-card>
    </v-row>

    <v-row>
      <v-card
      min-height= "300" width="600" class="d-flex flex-column mx-auto my-5 justify-center align-center" color="brown lighten-3"
      >
      <p class="my-2 infotext">이름 : {{ userInfo.name }}</p>
      <p class="my-2 infotext">별명 : {{ userInfo.nickname }}</p>
      <p class="my-2 infotext">이메일 : {{ userInfo.email }}</p>
      <p class="my-2 infotext">번호 : {{ userInfo.phone }}</p>
      <p class="my-2 infotext">계좌 : {{ userInfo.bankInfo.name }} {{ userInfo.bankInfo.account }}</p>
      </v-card>
    </v-row>
    <v-row>
      <v-card
      min-height= "300" width="600" class="d-flex flex-column mx-auto my-5 justify-center align-center" color="brown lighten-3"
      >
      <v-list-item two-line>
        <v-list-item-content>
          <v-list-item-title class="infotext" >경력</v-list-item-title>
          <v-list-item-subtitle class="ml-5 infotext" v-for="(info,i) in userInfo.certification" :key="i" >
            <p v-if="info.type === '경력'"> {{ info.content }}</p>
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
      <v-list-item two-line>
        <v-list-item-content>
          <v-list-item-title class="infotext" >수상</v-list-item-title>
          <v-list-item-subtitle class="ml-5 infotext" v-for="(info,i) in userInfo.certification" :key="i" >
            <p v-if="info.type === '수상'"> {{ info.content }}</p>
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
      </v-card>
    </v-row>

    <v-row>
      <v-btn
      :to="{ name: 'updatetutor', params: {userInfo: userInfo} }"
      outlined width="100" class="mx-auto my-2 mb-10">회원정보수정</v-btn>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class Info extends Vue{

  private userInfo: object = {
    name: "김강사", nickname: "김바이올린", 
    img: "https://cdn.vuetifyjs.com/images/profiles/marcus.jpg",
    instrument: ["바이올린", "첼로", "비올라"],
    email: "XXXXX@XXXX.com",
    phone: "010-1234-1234",
    bankInfo: {
      name: "신한은행",
      account: "111-111-111111",
      },
    certification: [
      { type: "경력", content: "버클리음대 수석 졸업" },
      { type: "수상", content: "전국노래자랑 최우수상" },
    ]
  }
  
}

</script>

<style>

.infotext {
  font-size: 1.5em;
  font-weight: 600;
}


</style>