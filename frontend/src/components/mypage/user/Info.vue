<template>
  <v-container>
    <v-row class="my-4">
      <h1>회원정보(학생)</h1>
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
      height= "300" width="600" class="d-flex flex-column mx-auto my-5 justify-center align-center" color="brown lighten-3"
      >
      <p class="my-2 infotext">이름 : {{ userInfo.name }}</p>
      <p class="my-2 infotext">별명 : {{ userInfo.nickname }}</p>
      <p class="my-2 infotext">이메일 : {{ userInfo.email }}</p>
      <p class="my-2 infotext">번호 : {{ userInfo.phone }}</p>
      </v-card>
    </v-row>

    <v-row>
      <v-card
      min-height= "300" width="600" class="d-flex flex-column mx-auto my-5 justify-center align-center" color="brown lighten-3"
      >
      <p class="mt-2 mb-7 infotext">관심사: 
        <v-simple-table
            fixed-header
            height="auto"
            style="width: 300px;"
          >
            <thead>
              <tr>
                <th class="text-left">
                  악기
                </th>
                <th class="text-left">
                  숙련도
                </th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="(item,i) in userInfo.interest"
                :key="i"
              >
                <td>{{ item.instrument }}</td>
                <td>{{ item.level }}</td>
              </tr>
            </tbody>
        </v-simple-table>
      </p>
      </v-card>
    </v-row>

    <v-row>
      <v-btn
      :to="{ name: 'updateuser', params: {userInfo: userInfo} }"
      outlined width="100" class="mx-auto my-2 mb-10">회원정보수정</v-btn>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";


@Component
export default class Info extends Vue{

  private userInfo: object| object[]| string = {
    name: "이름받을까요", nickname: "별명은있고", 
    img: "https://cdn.vuetifyjs.com/images/profiles/marcus.jpg",
    interest: [
      { instrument: "바이올린", level: "초급"},
      { instrument: "첼로", level: "중급"},
      { instrument: "비올라", level: "고급"},
    ],
    email: "XXXXX@XXXX.com",
    phone: "010-1234-1234",
    
  }

}
</script>

<style>

.infotext {
  font-size: 1.5em;
  font-weight: 600;
}


</style>