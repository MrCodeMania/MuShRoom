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
                    <div>
                        <div style="color: white">닉네임 설정</div>
                        <div style="font-size: 16px; color: grey;">
                        <v-icon small color="grey">mdi-alert-circle</v-icon>
                            닉네임 설정은 처음에만 가능합니다
                        </div>
                   </div>
                </v-card-title>
                <v-card-text class="pl-8 pt-2 d-flex justify-space-between align-center main-color">
                    <div>
                        <v-text-field 
                        style="width: 25em;" 
                        label="닉네임: " 
                        v-model="nickName"
                        :rules="rule"
                        @keyup.enter="enter()"
                        ></v-text-field>
                    </div>
                </v-card-text>
                <v-card-actions class="card-actions main-color">
                    <v-btn class="negative" text @click="close" >취소</v-btn>
                    <v-btn class="positive" text @click="enter" >채팅하기</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-container>
</template>

<script>
export default {
    props: ['showModal'],
    data() {
        return {
            nickName: '',
            rule: [val => (val || '').length > 0 || '닉네임을 입력해주세요']
        }
    },
    methods: {
        close() {
            this.$emit('close');
        },
        enter() {
            // 닉네임이 빈칸이면 채팅 불가
            if(this.nickName.length==0)
                return;

            this.$emit('setNickName', this.nickName);
        },
    },
}
</script>

<style scoped lang="scss">

.card-title {
    padding: 10px !important;
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