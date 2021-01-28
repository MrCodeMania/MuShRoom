<template>
    <v-row justify="center">
        <v-dialog
        v-model="toggleUpdateBank"
        persistent
        max-width="290"
        >
            <v-card>
                <v-card-title class="headline">
                    계좌 변경
                </v-card-title>
                <v-card-text>
                    <!-- 이후에 드랍다운 형식으로 (v-select) 응행 선택하는걸로 수정 -->
                    <v-text-field
                        v-model="bank.name"
                        label="은행"
                    ></v-text-field>
                    <v-text-field
                        v-model="bank.account"
                        label="계좌번호"
                    ></v-text-field>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                        color="gray darken-1"
                        text
                        @click="cancleUpdateBank"
                    >
                        취소
                    </v-btn>
                    <v-btn
                        color="green darken-1"
                        text
                        @click="confirmUpdateBank"
                    >
                        변경
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-row>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";

@Component
export default class UpdateBank extends Vue{

    @Prop(Boolean) toggleUpdateBank!: boolean;
    @Prop(Object) bankInfo!: object|any;

    private bank: object|any = {
        name: "",
        account: "",
    }

    cancleUpdateBank() {
        this.bank.name = "";
        this.bank.account = "";
        this.$emit("closeUpdateBank")
    }


    confirmUpdateBank() {

        this.$emit("closeUpdateBank", this.bank)
    }

}
</script>

<style>

</style>