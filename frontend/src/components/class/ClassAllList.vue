<template>
  <div id="classList">
      <v-container fluid
      class="my-16">
        <div class="pl-16 ml-16">
            <div
            class="ml-16">
                <v-btn 
                v-for="filter in filters"
                :key="filter"
                rounded
                color="teal lighten-2"
                dark
                class="mr-2"
                @click="deleteFilter(filter)"
                >
                    {{ filter }}
                    <v-icon
                    size="4">
                        mdi-close
                    </v-icon>
                </v-btn>
            </div>
            <v-card-title class="ml-16 text-h4">
                '{{ query }}' 의 검색 결과 입니다.
            </v-card-title>
        </div>
        <div class="d-flex justify-center">
            <v-card 
            v-for="cls in classes"
            :key="cls.title"
            class="d-inline-block mx-3"
            width="300">
                <v-btn
                text
                to="/detail/curriculum"
                class="p-0 text-center"
                height="200">
                    <v-img
                    :src="cls.profile.imagePath === 'testPath' ? sampleImg : cls.profile.imagePath"
                    class="white--text align-end"
                    gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                    width="300"
                    height="200"
                    >
                        <v-card-title
                        class="justify-center"
                        v-text="cls.title"></v-card-title>
                    </v-img>
                </v-btn>

                <v-card-actions>
                    <v-spacer></v-spacer>

                    <v-btn icon @click="clickHeart(cls.id)">
                        <v-icon v-if="cls.like==0">mdi-heart-outline</v-icon>
                        <v-icon v-else>mdi-heart</v-icon>
                    </v-btn>

                </v-card-actions>
            </v-card>
        </div>
    </v-container>
    <div class="text-center mb-6">
        <v-pagination
        v-model="page"
        :total-visible="10"
        :length="numOfPages"
        ></v-pagination>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Emit, Prop, Vue, Watch } from 'vue-property-decorator';
import ClassDataService from '@/service/ClassDataService';
const size = 12;

@Component
export default class ClassList extends Vue {
    @Prop(Array) filters!: string[]
    @Prop(String) query!: string

    private page = 1;   // 현재 페이지
    private classes: {[key: string]: any}[] = [];   // 전체 클래스 목록
    private classList: {[key: string]: any}[] = []; // 현재 페이지 클래스 목록
    private sampleImg = require('../../assets/mushroom.png');   // 기본 썸네일 경로
    private numOfPages = 0; // 전체 페이지 수

    created() {
        // 전체 클래스 목록 get request
        // 클래스 검색 api 필요
        ClassDataService.getAllClass()
        .then((response) => {
            this.classes = response.data;
            this.numOfPages = Math.ceil(this.classes.length/size);
            this.calculateListOfPage();
        })
        .catch((error) => {
            console.log(error.data);
        });
    }

    // 현재 페이지 번호에 맞춰 클래스 목록 계산하는 method
    @Watch('page')
    calculateListOfPage(){
        const start = (this.page-1) * size;
        const end = start + size;
        this.classList = this.classes.slice(start, end);
    }

    // class마다 like 추가/삭제하는 method
    // like update하는 api 필요
    clickHeart(id: string) {
        this.classes.forEach(cls => {
            if(cls['id'] === id)
                cls['like'] = cls['like']==0 ? 1 : 0;
        });
    }

    @Emit('deleteFilter')
    deleteFilter(filter: string){
        //
    }
}
</script>