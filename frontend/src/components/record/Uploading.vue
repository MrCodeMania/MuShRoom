<template>
  <v-overlay
    :value="uploading"
    absolute
    opacity="0.3"
    color="white">
    <v-card elevation="0" color="#00ff0000" class="text-center">
        <v-card-title>
            파일 업로드 중
        </v-card-title>
        <v-progress-circular
        indeterminate>
        </v-progress-circular>
    </v-card>
  </v-overlay>
</template>

<script>
export default {
    props: ['uploading']
}
</script>

<style>

</style>