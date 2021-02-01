<template>
  <div class="mb-3">
    <div class="text-center">
      <h3 class="text-h3 text-center mt-5"></h3>
      <div class="inner-container">
        <v-sheet color="white" elevation="2">
          <v-simple-table>
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-center">번호</th>
                  <th class="text-left">제목</th>
                  <th class="text-center">작성자</th>
                  <th class="text-center">날짜</th>
                  <th class="text-center">조회수</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="item in items" :key="item.id">
                  <td class="text-center">{{ item.id }}</td>
                  <td @click="clicktest(item.id)" style="cursor: pointer">
                    {{ item.title }}
                  </td>
                  <td class="text-center">{{ item.userid }}</td>
                  <td class="text-center">{{ item.date }}</td>
                  <td class="text-center">{{ item.count }}</td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-sheet>
        <div class="text-center mt-4">
          <v-pagination v-model="page" :length="6"></v-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class Boardlist extends Vue {
  private nowState = "free";
  private page = 1;
  private items = [
    {
      id: "공지",
      title: "Celebrity",
      userid: "아이유",
      date: "2020-01-28",
      count: 2,
    },
    {
      id: "공지1",
      title: "너무 재미있어요!!!",
      userid: "지나가는 사람",
      date: "2020-01-27",
      count: 1,
    },
    {
      id: "공지2",
      title: "제목은 title",
      userid: "글쓴이는누구",
      date: "2020-01-27",
      count: 2,
    },
    {
      id: "공지3",
      title: "피아노 추천해주세요!",
      userid: "피아노초보",
      date: "2020-01-27",
      count: 3,
    },
    {
      id: "공지4",
      title: "첫글",
      userid: "1위",
      date: "2020-01-26",
      count: 2,
    },
  ];
  clicktest(item: string) {
    console.log(item + "의 글을 눌렀어요");
    //this.$router.replace({
    //  name: "boardview",
    //  params: { articleid: item },
    //});
    // or router push
    this.$router.push({ path: `/notice/${item}` });
  }
}
</script>
<style>
</style>