<template>
    <v-footer padless height="100">
        <v-card class="text-center"
        color="nav-color"
        tile
        width="100%">
            <v-img
            :src="img"
            width="100"
            height="100"
            class="d-inline-block">
            </v-img>
            <v-card-text class="pt-0 font-weight-bold">
                <span style="color: white" v-for="name in team" :key="name">
                    {{ name }}
                </span>
            </v-card-text>
        </v-card>
    </v-footer>
</template>

<script>
export default {
    data() {
        return {
            team: [
                "정수림,",
                "권연욱,",
                "송건호,",
                "이건우,",
                "이병희,",
                "정지환",
            ],
            img: require("@/assets/bremen.png")
        }
    }
}
</script>

<style>

</style>