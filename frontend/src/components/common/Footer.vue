<template>
    <v-footer padless height="100" color="#00ff0000">
        <v-card class="text-center"
        elevation="0"
        color="#00ff0000"
        tile
        width="100%">
            <v-img
            v-for="img in team"
            :key="img"
            :src="img"
            width="100"
            height="100"
            class="d-inline-block">
            </v-img>
            <!-- <v-card-text class="pa-0 font-weight-bold">
                <span style="color: white" v-for="name in team" :key="name">
                    {{ name }}
                </span>
            </v-card-text> -->
        </v-card>
    </v-footer>
</template>

<script>
export default {
    data() {
        return {
            team: [
                require('@/assets/bremen/JSR.png'),
                require('@/assets/bremen/KYW.png'),
                require('@/assets/bremen/SGH.png'),
                require('@/assets/bremen/LGW.png'),
                require('@/assets/bremen/LBH.png'),
                require('@/assets/bremen/JJH.png'),
            ],
            img: require("@/assets/bremen.png")
        }
    }
}
</script>

<style>

</style>