<template>
    <v-container id="registerModal">
        <v-dialog 
        v-model="showModal"
        max-width="500"
        persistent>
            <v-card
            class="d-flex flex-column" 
            height="auto"
            >
                <v-card-title class="card-title nav-color">
                    <p>방 url</p>
                </v-card-title>
                <v-card-text class="pl-8 pt-2 d-flex justify-space-between align-center main-color">
                    <div>
                        <label>링크: </label><input style="width: 25em;" class="input" ref="link" :value="address">
                    </div>
                    <v-btn icon large color="black" @click="copy"><v-icon>mdi-content-copy</v-icon></v-btn>
                </v-card-text>
                <v-card-actions class="card-actions main-color">
                    <v-btn class="negative" text @click="close" >취소</v-btn>
                    <v-btn class="positive" text @click="enter" >입장하기</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-container>
</template>

<script>
export default {
    props: ['showModal', 'address'],
    methods: {
        close() {
            this.$emit('close', false, false);
        },
        enter() {
            this.$emit('close', false, true);
            // master code
            console.log(this.address.split('=')[1]);
            // address push
            this.$router.push({ name: 'PracticeRoom', query: {shareUrl: this.address.split('=')[1]}});
        },
        copy() {
            this.$refs.link.select();
            document.execCommand("Copy");
        },

    },

}
</script>

<style scoped lang="scss">

.card-title {
    padding: 10px !important;
    height: 50px;
}

.card-title > p {
    color: white;
}

label {
    color: black;
    font-size: 1rem;
    font-weight: 700;
}

.card-actions {
    display: flex;
    justify-content: center;
}




</style>