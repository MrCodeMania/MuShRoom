<template>
    <v-row justify="center">
        <v-dialog
        v-model="toggleUpdatePassword"
        persistent
        max-width="290"
        >
            <v-card>
                <v-card-title class="headline">
                    비밀번호변경
                </v-card-title>
                <v-card-text>대문자와 특수문자를 포함한 8~16자</v-card-text>
                <v-card-text>
                    <v-text-field
                        v-model="currentPassword"
                        :append-icon="showCurrent ? 'mdi-eye' : 'mdi-eye-off'"
                        :rules="[rules.required, rules.min, rules.max]"
                        :type="showCurrent ? 'text' : 'password'"
                        label="현재비밀번호"
                        hint="At least 8 characters"
                        counter
                        @click:append="showCurrent = !showCurrent"
                    ></v-text-field>
                    
                    <v-text-field
                        v-model="newPassword"
                        :append-icon="showNew ? 'mdi-eye' : 'mdi-eye-off'"
                        :rules="[rules.required, rules.min, rules.max]"
                        :type="showNew ? 'text' : 'password'"
                        label="새비밀번호"
                        hint="At least 8 characters"
                        counter
                        @click:append="showNew = !showNew"
                    ></v-text-field>
                    
                    <v-text-field
                        v-model="confirmPassword"
                        :append-icon="showConfirm ? 'mdi-eye' : 'mdi-eye-off'"
                        :rules="[rules.required, rules.min, rules.max]"
                        :type="showConfirm ? 'text' : 'password'"
                        label="새비밀번호확인"
                        :hint="newPassword === showConfirm? '비밀번호가 일치합니다.' : '비밀번호가 일치하지 않습니다.' "
                        counter
                        @click:append="showConfirm = !showConfirm"
                    ></v-text-field>
                    
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                        color="gray darken-1"
                        text
                        @click="cancleUpdatePassword"
                    >
                        취소
                    </v-btn>
                    <v-btn
                        color="green darken-1"
                        text
                        @click="confirmUpdatePassword"
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
export default class UpdatePassword extends Vue{

    @Prop(Boolean) toggleUpdatePassword!: boolean;

    private showCurrent = false;
    private showNew = false;
    private showConfirm = false;
    private rules = {
          required: (value: any) => !!value || 'Required.',
          min: (v: string|any[]) => v.length >= 8 || 'Min 8 characters',
          max: (v: string|any[]) => v.length < 17 || 'Max 16 characters',
          emailMatch: () => (`The email and password you entered don't match`),
    }


    private currentPassword = "";

    private newPassword = "";

    private confirmPassword = "";
    
    cancleUpdatePassword() {
        this.currentPassword = "";
        this.newPassword = "";
        this.confirmPassword = "";
        this.$emit("closeUpdatePassword")
    }

    confirmUpdatePassword() {
        this.currentPassword = "";
        this.newPassword = "";
        this.confirmPassword = "";
        this.$emit("closeUpdatePassword")
    }
}
</script>

<style>

</style>