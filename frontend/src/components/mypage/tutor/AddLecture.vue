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
          label="class"
          filled
          ></v-text-field>
          <v-btn @click="addSection">section</v-btn>
        </div>
        <v-card v-for="(section,n) in sections" :key="n">
          <div class="d-flex">
            <v-text-field
            class="my-4"
            v-model="section.sectionName"
            label="section"
            filled
            ></v-text-field>
            <v-btn @click="addLecture(section.lectures)">lecture</v-btn>
          </div>
          <v-card v-for="(lecture,n) in section.lectures" :key="n">
            <v-text-field
            class="my-4"
            v-model="lecture.lectureName"
            label="lecture"
            filled
            ></v-text-field>
            <v-file-input
              show-size
              v-model="lecture.videoPath"
              label="강의 업로드"
              filled 
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
export default class AddLecture extends Vue{


  private className = "";

  private lectureItem: object = {
      lectureName: "", videoPath: null
    }
  private sectionItem: object = {
    sectionName: "",
    lectures: [
      {lectureName: "", videoPath: null}
    ]
  }
  private sections: object[] = [
    {sectionName: "",
    lectures: [
      {lectureName: "", videoPath: null}
    ]}
  ]

  addSection() {
    this.sections.push(this.sectionItem)
    console.log(this.sections)
  }

  addLecture(lectures: object[]) {
    // this.sections[n].lectures.push(this.lectureItem);
    lectures.push(this.lectureItem)
    console.log(this.sections)
    console.log(lectures)
  }

  createClass() {
    console.log(this.className)
    console.log(this.sections)
  }

}
</script>

<style>

</style>