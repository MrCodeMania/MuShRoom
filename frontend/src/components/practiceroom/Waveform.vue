<template>
    <div style="background-color: #3C3C3C">
        <div style="position: relative">
            <div v-show="timeline" :id="'container'+idx"></div>
            <vue-wave-surfer 
                ref="waveform"
                :src="url"
                :options="options"
            ></vue-wave-surfer>
        </div>
        <div v-if="!isReady" style="position: relative; top: -45px; height: 0" class="text-center py-2">
            <v-progress-circular indeterminate></v-progress-circular>
        </div>
    </div>
</template>

<script>
import Timeline from 'wavesurfer.js/dist/plugin/wavesurfer.timeline';
export default {
    props: ['url', 'height', 'mouse', 'audioCtx', 'timeline', 'idx', 'full'],
    data() {
        return {
            options: {
                // barWidth: 2,
                normalize: false,
                cursorColor: '#00ff0000',
                progressColor: '#FED134', //color of waveform behind the cursor
                waveColor: '#00A2FF', //color of waveform after the cursor
                scrollParent: this.full,
                height: this.height,
                interact: this.mouse,
                audioContext: this.audioCtx,
                plugins: [
                    Timeline.create({
                        container: "#container"+this.idx,
                        secondaryFontColor: '#ffffffbb'
                    })
                ]
            },
            isReady: false
        }
    },
    mounted() {
        this.player.on('ready', ()=>{
            this.toggleReady();
            console.log('ready');
        });
        // this.player.zoom(10);
        this.player.on('seek', ()=>{
            console.log(this.player.getCurrentTime().toFixed(2));
            this.$emit('setTime', this.player.getCurrentTime().toFixed(2));
        });
    },
    // watch: {
    //     url: function() {
    //         this.player.on('ready', ()=>{
    //             this.toggleReady();
    //             console.log('ready');
    //         });
    //         this.player.zoom(10);
    //         this.player.on('seek', ()=>{
    //             console.log(this.player.getCurrentTime().toFixed(2));
    //             this.$emit('setTime', this.player.getCurrentTime().toFixed(2));
    //         });
    //     }
    // },
    computed: {
        player() {
            return this.$refs.waveform.waveSurfer;
        },
    },
    methods: {
        play() {
            this.player.playPause();
        },
        toggleReady() {
            this.isReady = true;
        }
    }
}
</script>

<style>
.hide {
    max-height: 0px;
}
</style>
