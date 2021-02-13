<template>
    <v-menu
        left
        top="top"
        offset-x="offset-x"
        :close-on-content-click="false">
        <template v-slot:activator="{ on, attrs }">
            <v-btn
                color="primary"
                dark="dark"
                @click="reset"
                v-bind="attrs"
                v-on="on">
                <v-icon class="pr-2">mdi-share-variant</v-icon>
                Share
            </v-btn>
        </template>

        <v-list class="component-color" width="20vw">
            <v-list-item v-for="(item, idx) in filtered_urls" :key="idx">
                <v-list-item-content style="padding: 0px !important;">
                    <v-list-item-title style="font-size:12pt !important; font-weight:bold;">
                        {{item.name}}
                    </v-list-item-title>
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
                            <v-icon small>mdi-content-copy</v-icon>
                        </v-btn>
                    </div>
                    <p v-if="copied === item.name" style="width: 12px; padding-left: 16px; font-size: 8px;">{{ item.name }} url copid on Clipboard!</p>
                </v-list-item-content>
            </v-list-item>
        </v-list>
    </v-menu>
</template>

<script>

export default {
    data() {
        return {
            roomNo:'',
            urls: [
                {
                    name: "Musician",
                    url: "http://i4a105.p.ssafy.io:8080/practiceroom/?room="
                }, {
                    name: "Audience",
                    url: "http://i4a105.p.ssafy.io:8080/practiceroom/?room="
                }
            ],
            readOnlyUrls: [
                {
                    name: "Audience",
                    url: "http://i4a105.p.ssafy.io:8080/practiceroom/?room="
                }
            ],
            copied: "",
        };
    },
    created() {
        this.status = this.$store.state.status;

        if(this.status === "Master")
        {
            this.urls[0].url = "http://i4a105.p.ssafy.io:8080/practiceroom?shareUrl=" + this.$store.state.shareUrl[0];
            this.urls[1].url = "http://i4a105.p.ssafy.io:8080/practiceroom?shareUrl=" + this.$store.state.shareUrl[1];
        }
        else
            this.readOnlyUrls[0].url = "http://i4a105.p.ssafy.io:8080/practiceroom?shareUrl=" + this.$store.state.shareUrl[1];
    },
    methods: {
        copyShareUrl(name) {
            this.copied = name;
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
        },
        reset() {
            this.copied = "";
        }
    },
    computed: {
        filtered_urls() {
            if (this.status === "Master") {
                return this.urls;
            } else {
                return this.urls;
            }
        },
    },
}
</script>

<style >
.share-component {
    display: flex;
    align-items: center;
    height: 30px;
    border: 2px solid #bbbbbb;
    margin-left: 4px;
}
.share-url-input {
    background-color: white;
    border: 2px solid #bbbbbb;
    border-left: 0px;
    flex: 5;
    font-size: 12px;
    height: 30px !important;
    padding-left: 4px;
}
.share-url-button {
    height: 30px !important;
    width: 30px !important;
    flex: 1;
    border-left: 4px solid #bbbbbb;
    border: 2px solid #bbbbbb;
    background-color: #efefef;
}
</style>