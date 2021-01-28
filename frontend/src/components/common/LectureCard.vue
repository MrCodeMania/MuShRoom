<template>
    <v-card class="mx-auto" max-width="250">
        <v-row dense>
            <v-col v-for="card in cards" :key="card.title">
                <v-card>
                    <v-img :src="card.src" class="white--text align-end"
                        gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)" height="200px">
                        <v-card-title v-text="card.title"></v-card-title>
                    </v-img>

                    <v-card-actions>
                        <v-spacer></v-spacer>

                        <v-btn icon>
                            <v-icon>mdi-heart</v-icon>
                        </v-btn>

                    </v-card-actions>
                </v-card>
            </v-col>
        </v-row>
    </v-card>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";

@Component
export default class LectureCard extends Vue{

    // @Prop(Object) cards!: Record<string, any>;
}
</script>

<style>

</style>