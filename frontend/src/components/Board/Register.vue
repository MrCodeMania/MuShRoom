<template>
  <div class="mt-10">
    <v-form>
      <v-container>
        <v-row>
          <v-text-field
            solo
            :counter="50"
            label="제목"
            name="title"
            required
            v-model="title"
            maxlength="50"
            single-line
          ></v-text-field>
        </v-row>
        <v-row class="mb-2"> 내용 </v-row>
        <v-row>
          <v-textarea
            solo
            filled
            name="context"
            v-model="context"
            hide-details
            :counter="1000"
            maxlength="1000"
          ></v-textarea>
        </v-row>
        <v-row>
          <v-col> <v-btn :to="{ name: 'boardlist' }">목록 </v-btn></v-col>
          <v-col> <v-btn block outlined color="blue"> 등록 </v-btn></v-col>
        </v-row>
      </v-container>
    </v-form>
  </div>
</template>
<script>
export default {
  data() {
    return { title: "", context: "" };
  },
};
</script>



<style>
</style>