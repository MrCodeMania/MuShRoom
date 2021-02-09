<template>
    <v-container id="registerModal">
        <v-row justify="center">
            <v-dialog 
            v-model="showModal"
            max-width="500"
            persistent>
                <v-card
                class="d-flex flex-column" 
                height="24vh"
                >
                    <v-card-title class="card-title">
                        <p>방 url</p>
                    </v-card-title>
                    <v-divider class="mx-2"></v-divider>
                    <v-card-text class="pt-4 pl-8 d-flex justify-space-between align-center">
                        <div>
                            <label>링크: </label><input style="width: 20em;" class="input" ref="link" :value="address">
                        </div>
                        <v-btn @click="copy"><v-icon>mdi-content-copy</v-icon></v-btn>
                    </v-card-text>
                    <v-spacer></v-spacer>
                    <v-card-actions class="card-actions pb-4">
                        <v-btn @click="close" >취소</v-btn>
                        <v-btn @click="enter" >입장하기</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-row>
    </v-container>
</template>

<script>
export default {
    props: ['showModal'],
    data() {
        return {
            address: "http://mushroom.com/123483750",
        }
    },
    methods: {
        close() {
            this.$emit('close', false);
        },
        enter() {
            this.$emit('close', false);
            this.$router.push({ name: 'PracticeRoom'})
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
    position: relative;
    background-color: rgb(236, 154, 45);
    height: 50px;
}

.card-title > p {
    padding-top: 10px;
    padding-left: 20px;
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