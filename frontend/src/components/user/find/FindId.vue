<template>
  <v-form ref="form" class="mt-4 mb-3">
    <v-container>
      <v-row cols="12" align="center">
        <v-col>
          <v-text-field
            solo
            v-model="findName"
            label="Name"
            class="mt-3"
            required
          ></v-text-field>
        </v-col>
        <Phone class="mt-n10" @sendPhonenumber="receivePhonenumber"></Phone>
      </v-row>
    </v-container>
  </v-form>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import Phone from "@/components/user/Phone.vue";
@Component({
  components: {
    Phone,
  },
})
export default class extends Vue {
  private findName = "";
  receivePhonenumber(newnumber: string) {
    const findIdForm = [{ name: this.findName }, { phone: newnumber }];
    //받으면 바로 상위 컴포넌트 찾기페이지에 정보 전송
    this.$emit("sendFindIdForm", findIdForm);
  }
}
</script>
<style>
</style>