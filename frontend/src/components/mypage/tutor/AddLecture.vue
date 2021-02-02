<template>
  <div class="container">
    <v-row class="my-4">
      <h1>강의 등록(강사)</h1>
    </v-row>

    <v-row class="d-flex justify-center my-4">
      <v-card max-width="800" min-height="400">
        <div class="d-flex">
            <v-text-field
            class="my-4"
            v-model="className"
            placeholder="class"
            outlined
            ></v-text-field>
          <v-btn @click="addSection">add section</v-btn>
        </div>
        <v-file-input
          show-size
          v-model="profile.imagePath"
          placeholder="이미지 추가"
          outlined
        ></v-file-input>
        <v-textarea
          outlined
          placeholder="강의 상세"
          v-model="profile.intro"
        ></v-textarea>
        <v-card class="my-4" v-for="(section,n) in sections" :key="n">
          <div class="d-flex">
            <v-text-field
            class="my-4"
            v-model="section.sectionName"
            placeholder="section"
            outlined
            ></v-text-field>
            <div class="d-flex flex-column">
                <v-btn @click="addLecture(section.lectures)">lecture</v-btn>
                <v-btn @click="deleteSection(n)">delete section</v-btn>
            </div>
          </div>
          <v-card v-for="(lecture,n) in section.lectures" :key="n">
            <div class="d-flex">
              <v-text-field
              class="my-4"
              v-model="lecture.lectureName"
              placeholder="lecture"
              outlined
              ></v-text-field>
              <v-btn @click="deleteLecture(section.lectures, n)">delete lecture</v-btn>
            </div>

            <v-file-input
              show-size
              v-model="lecture.videoPath"
              placeholder="강의 업로드"
              outlined
            ></v-file-input>
          </v-card>
        </v-card>
      </v-card>
    </v-row>

    <v-row class="d-flex justify-center">
      <v-btn depressed color="amber" width="200" class="mx-1 my-2 mb-10" @click="createClass">등록신청</v-btn>
    </v-row>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class AddLecture1 extends Vue{


  private className = "";
  private profile: object|string = {
    imagePath : null, intro : ""
  }

  private sections: object[] = [
    {sectionName: "",
    lectures: [
      {lectureName: "", videoPath: null}
    ]}
  ]

  addSection() {
    const sectionItem: object = {
      sectionName: "",
      lectures: [
        {lectureName: "", videoPath: null}
      ]
    }
    this.sections.push(sectionItem);
    // console.log(this.sections);
  }

  deleteSection(n: number) {
    // console.log(n);
    this.sections.splice(n, 1);
    // console.log(this.sections);
  }

  addLecture(lectures: object[]) {
    const lectureItem: object = {
      lectureName: "", videoPath: null
    }
    lectures.push(lectureItem);
    // console.log(this.sections);
  }

  deleteLecture(lectures: object[], n: number) {
    // console.log(n);
    lectures.splice(n, 1);
  }

  createClass() {
    console.log(this.className);
    console.log(this.profile)
    console.log(this.sections);
  }

}
</script>

<style>
/* 
.v-text-field .v-input__control .v-input__slot {
min-height: auto !important;
display: flex !important;
align-items: center !important;
} */


</style>