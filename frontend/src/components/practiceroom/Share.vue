<template>
    <v-menu
        open-on-hover="open-on-hover"
        top="top"
        offset-x="offset-x"
        :close-on-content-click="false">
        <template v-slot:activator="{ on, attrs }">
            <v-btn
                color="primary"
                dark="dark"
                v-bind="attrs"
                v-on="on">
                Share
            </v-btn>
        </template>

        <v-list>
            <v-list-item v-for="item in urls" :key="item.name">
                <v-list-item-content>
                    <v-list-item-title style="font-size:20pt; font-weight:bold;">{{ item.name }}</v-list-item-title>
                    <div class="share-component">
                        <input
                            class="share-url-input"
                            v-model="item.url"
                            type="text"
                            readonly="readonly"
                            ref="textToCopy">
                        <v-btn
                            fab="fab"
                            tile="tile"
                            text="text"
                            class="share-url-button"
                            @click="copyShareUrl(item.name)">
                            <v-icon dark="dark" large="large">mdi-content-copy</v-icon>
                        </v-btn>
                    </div>
                </v-list-item-content>
            </v-list-item>
        </v-list>
    </v-menu>
</template>

<script>
import Axios from 'axios'
import Sha256 from '@/lib/sha256.js'

export default {
    data() {
        return {
            roomNo:'',
            urls: [
                {
                    name: "Master",
                    url: "http://i4a105.p.ssafy.io:8080/practiceroom/?room="
                }, {
                    name: "ReadOnly",
                    url: "http://i4a105.p.ssafy.io:8080/practiceroom/?room="
                }
            ],
        };
    },
    created() {
        // 현재 URL을 저장
        // split해서 파라미터만 저장할지?
        console.log(Sha256("url")); // sha256 test
        this.roomNo = document.location.href.split('?')[1];
        this.getShareUrl();
    },
    methods: {
        // url 받아오는 메소드
        
        getShareUrl() {
          Axios.get('',{
            params:{roomNo:this.baseUrl}
            }).then(res => {
              this.urls[0].url = res[0].data;
              this.urls[1].url = res[1].data;
            })
        },
        copyShareUrl(name) {
            let copied = this.$refs.textToCopy;
            if (name === "Master") {
                console.log(this.urls[0].url);
                console.log(copied);
                copied[0].select()
                document.execCommand("copy");
            } else if (name === "ReadOnly") {
                console.log(this.urls[1].url);
                console.log(copied);
                copied[1].select()
                document.execCommand("copy");
            }
        }
    }
}
</script>

<style >
.share-component {
    display: flex;
    flex-wrap: nowrap;
    align-items: center;
    border: 2px solid #bbbbbb;
}
.share-url-input {
    flex: 5;
    font-size: 16pt;
    padding: 15px 5px;
}
.share-url-button {
    flex: 1;
    border-left: 2px solid #bbbbbb;
    background-color: #efefef;
}
</style>