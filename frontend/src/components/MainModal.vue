<template>
    <div id="registerModal">
        <v-row justify="center">
            <v-dialog 
            v-model="showModal"
            max-width="500"
            persistent>
                <v-card height="40vh">
                    <v-row align="center">
                        <v-col cols="12" md="8">
                            <label>링크: </label><input class="input" ref="link" :value="address">
                        </v-col>
                        <v-col cols="12" md="4">
                            <v-btn @click="copy">복사</v-btn>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-btn @click="close" :to="{ name: 'PracticeRoom' }">입장하기</v-btn>
                    </v-row>
                </v-card>
            </v-dialog>
        </v-row>
    </div>
</template>

<script>
export default {
    props: ['showModal'],
    data() {
        return {
            address: "http://mushroom.com/123483750"
        }
    },
    methods: {
        close() {
            this.$emit('close', false);
        },
        copy() {
            this.$refs.link.select();
            document.execCommand("Copy");
        }
    }
}
</script>

<style>

</style>