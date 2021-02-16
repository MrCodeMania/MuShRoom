<template>
    <!-- id 값에 따라 채팅 칸 위치, 색 달라짐 -->
    <div>
        <div 
        style="width: 150px;"
        :class="msg.id==id ? 'myInfo text-right':'yourInfo'">
            {{ msg.nickName }}
        </div>
        <v-card
        light
        width="150px"
        style="z-index: 99 !important;"
        :class="msg.id==id ? 'mine' : 'others'"
        class="px-2"
        elevation="0">
            {{ msg.text }}
        </v-card>
        <div
        :class="msg.id==id ? 'myInfo':'yourInfo'"
        style="width: 150px; font-size : 10px; color : grey;">
            {{ msg.time }}
        </div>
    </div>
</template>

<script>
export default {
    props: ['msg', 'id'],
}
</script>

<style>
.mine {
    float: right;
    margin-top: 2px;
    background-color: #f0f0f0f1 !important;
}

.others{
    float: left;
    margin-top: 2px;
    background-color: #f0f0f09f !important;
}

.myInfo {
    float: right;
}

.yourInfo {
    float: left;
}

</style>