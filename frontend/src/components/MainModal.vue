<template>
    <div id="registerModal">
        <v-dialog 
        v-model="showModal"
        max-width="500"
        persistent>
            <v-card>
                <v-row>
                    <label>링크: </label><input ref="link" :value="address">
                    <v-btn @click="copy">복사</v-btn>
                </v-row>
                <v-row>
                    <v-btn @click="close" :to="{ name: 'PracticeRoom' }">입장하기</v-btn>
                </v-row>
            </v-card>
        </v-dialog>
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