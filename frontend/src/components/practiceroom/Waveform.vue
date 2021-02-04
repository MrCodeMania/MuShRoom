<template>
  <div>
      <vue-wave-surfer 
        ref="waveform"
        :src="url"
        :options="options"
      ></vue-wave-surfer>
  </div>
</template>

<script>
export default {
    props: ['url', 'height'],
    data() {
        return {
            options: {
                barWidth: 2,
                normalize: true,
                cursorColor: '#00ff0000',
                scrollParent: false,
                height: this.height
            }
        }
    },
    mounted() {
        this.player.on('ready', ()=>{
            console.log('ready');
        })
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