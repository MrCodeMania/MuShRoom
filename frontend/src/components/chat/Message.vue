<template>
    <!-- id 값에 따라 채팅 칸 위치, 색 달라짐 -->
    <v-card
    light
    width="150px"
    style="z-index: 99 !important;"
    :class="msg.id==id ? 'mine component-color' : 'others smallcomponent-color'"
    elevation="0">
        {{ msg.text }}
    </v-card>
</template>

<script>
export default {
    props: ['msg', 'id']
}
</script>

<style>
.mine {
    float: right;
    margin-top: 2px;
}

.others{
    float: left;
    margin-top: 2px;
}
</style>