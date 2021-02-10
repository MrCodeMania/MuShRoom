<template>
  <div>
      <div id="container"></div>
      <vue-wave-surfer 
        ref="waveform"
        :src="url"
        :options="options"
      ></vue-wave-surfer>
  </div>
</template>

<script>
import Timeline from 'wavesurfer.js/dist/plugin/wavesurfer.timeline';
export default {
    props: ['url', 'height', 'mouse', 'audioCtx'],
    data() {
        return {
            options: {
                barWidth: 2,
                normalize: false,
                cursorColor: '#00ff0000',
                scrollParent: false,
                height: this.height,
                interact: this.mouse,
                audioContext: this.audioCtx,
                plugins: [
                    Timeline.create({
                        container: "#container",
                        secondaryFontColor: '#000'
                    })
                ]
            }
        }
    },
    watch: {
        url: function() {
            this.player.on('ready', ()=>{
                console.log('ready');
            });
            this.player.zoom(10);
            this.player.on('seek', ()=>{
                console.log(this.player.getCurrentTime().toFixed(2));
                this.$emit('setTime', this.player.getCurrentTime().toFixed(2));
            });
        }
    },
    computed: {
        player() {
            return this.$refs.waveform.waveSurfer;
        }
    },
    methods: {
        play() {
            this.player.playPause();
        }
    }
}
</script>

<style>

</style>