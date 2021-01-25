<template>
  <div>
    <v-app-bar
    app
    color="amber darken-1"
    elevate-on-scroll>
      <v-app-bar-nav-icon>
          <v-img 
          src="@/assets/logo.png" 
          alt=""
          width="30"
          height="30"
          />
      </v-app-bar-nav-icon>

        <v-menu
        rounded="0"
        bottom
        offset-y
        origin="center center"
        transition="slide-y-transition">
            <template v-slot:activator="{ on, attrs }">
                <v-btn
                depressed
                dark
                color="amber darken-1"
                v-bind="attrs"
                v-on="on">
                    클래스
                    <v-icon>
                        mdi-chevron-down
                    </v-icon>
                </v-btn>
            </template>
            <v-list>
                <v-list-item
                v-for="(item, i) in category"
                :key="i"
                >
                <v-menu
                rounded="0"
                bottom
                offset-x
                origin="center center"
                transition="slide-x-transition">
                    <template v-slot:activator="{ on, attrs }">
                        <v-btn
                        depressed
                        color="white"
                        v-bind="attrs"
                        v-on="on">
                            <v-list-item-title>{{ item.title }}</v-list-item-title>
                        </v-btn>
                    </template>
                    <v-list>
                        <v-list-item
                        v-for="(item, i) in instruments"
                        :key="i"
                        >
                        <v-list-item-title>{{ item.title }}</v-list-item-title>
                        </v-list-item>
                    </v-list>
                </v-menu>
                </v-list-item>
            </v-list>
        </v-menu>
        <v-btn
        depressed
        dark
        color="amber darken-1"
        >
        연습실    
        </v-btn>
        <v-btn
        depressed
        dark
        color="amber darken-1"
        >
        커뮤니티
            <v-icon>
                mdi-chevron-down
            </v-icon>
        </v-btn>
        <v-spacer></v-spacer>
        <div class="search">
            <v-text-field
            solo>
            </v-text-field>
        </div>
        <v-btn icon
        dark>
            <v-icon>mdi-magnify</v-icon>
        </v-btn>
        <v-btn icon
        dark>
            <v-icon>mdi-bell</v-icon>
        </v-btn>
        <v-btn
        depressed
        dark
        color="amber darken-1">
            로그인
        </v-btn>
        <v-btn
        depressed
        dark
        color="amber darken-1">
            회원가입
        </v-btn>
    </v-app-bar>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
export default Vue.extend({
    data () {
      return {
          category: [
              { title:'현악기' },
              { title:'금관악기' },
              { title:'목관악기' },
              { title:'타악기' },
              { title:'전자악기' },
              { title:'노래' },
          ],
          instruments:[
              { title:'바이올린' },
              { title:'비올라' },
              { title:'첼로' },
          ]
      }
    },
})
</script>

<style>
.search {
    max-height: 50px;
}
</style>