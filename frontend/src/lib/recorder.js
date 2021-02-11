
import WavEncoder from './wav-encoder'
import { convertTimeMMSS } from './utils'

export default class {
  constructor (options = {}) {
    this.beforeRecording = options.beforeRecording
    this.pauseRecording  = options.pauseRecording
    this.afterRecording  = options.afterRecording
    this.micFailed       = options.micFailed
    this.format          = options.format

    this.encoderOptions = {
      bitRate    : options.bitRate,
      sampleRate : options.sampleRate
    }

    this.bufferSize = 4096
    this.records    = []

    this.isPause     = false
    this.isRecording = false

    this.duration = 0
    this.volume   = 0

    this.wavSamples = []

    this._duration = 0
  }

  start () {
    const constraints = {
      video: false,
      audio: {
        channelCount: 1,
        echoCancellation: false
      }
    }

    this.beforeRecording && this.beforeRecording('start recording')
  
    navigator.mediaDevices
             .getUserMedia(constraints)
             .then(this._micCaptured.bind(this))
             .catch(this._micError.bind(this))

    this.isPause     = false
    this.isRecording = true

 
  }

  stop () {
    this.stream.getTracks().forEach((track) => track.stop())
    this.input.disconnect()
    this.processor.disconnect()
    this.context.close()

    let record = null

    if (this._isMp3()) {
      record = this.lameEncoder.finish()
    } else {
      console.log(123);
      let wavEncoder = new WavEncoder({
        bufferSize : this.bufferSize,
        sampleRate : this.input.context.sampleRate||this.encoderOptions.sampleRate,
        samples    : this.wavSamples
      })
      record = wavEncoder.finish()
      this.wavSamples = []
    }

    record.duration = convertTimeMMSS(this.duration)
    this.records.push(record)

    this._duration = 0
    this.duration  = 0

    this.isPause     = false
    this.isRecording = false

    this.afterRecording && this.afterRecording(record)
  }

  pause () {
    this.stream.getTracks().forEach((track) => track.stop())
    this.input.disconnect()
    this.processor.disconnect()

    this._duration = this.duration
    this.isPause = true

    this.pauseRecording && this.pauseRecording('pause recording')
  }

  recordList () {
    return this.records
  }

  lastRecord () {
    return this.records.slice(-1).pop()
  }

  _micCaptured(stream) {
    var filter;
    var compressor;
    this.context    = new(window.AudioContext || window.webkitAudioContext)()
    this.duration   = this._duration
    //compressor
    compressor = this.context.createDynamicsCompressor();
    compressor.threshold.value = -50;
    compressor.knee.value = 40;
    compressor.ratio.value = 12;
    compressor.reduction.value = -20;
    compressor.attack.value = 0;
    compressor.release.value = 0.25;
    ////
    filter
    filter = this.context.createBiquadFilter();
    filter.Q.value = 8.30;
    filter.frequency.value = 355;
    filter.gain.value = 3.0;
    filter.type = 'bandpass';
    filter.connect(compressor);

    this.input = this.context.createMediaStreamSource(stream)
    this.processor  = this.context.createScriptProcessor(this.bufferSize, 1, 1)
    this.stream     = stream
    console.log(compressor, filter);
    console.log(this.input);
    
    this.processor.onaudioprocess = (ev) => {
      const sample = ev.inputBuffer.getChannelData(0)
      let sum = 0.0

      if (this._isMp3()) {
        this.lameEncoder.encode(sample)
      } else {
        this.wavSamples.push(new Float32Array(sample))
      }

      for (let i = 0; i < sample.length; ++i) {
        sum += sample[i] * sample[i]
      }

      this.duration = parseFloat(this._duration) + parseFloat(this.context.currentTime.toFixed(2))
      this.volume = Math.sqrt(sum / sample.length).toFixed(2)
    }

    this.input.connect(this.processor)
    this.processor.connect(this.context.destination)
  }

  _micError (error) {
    this.micFailed && this.micFailed(error)
  }

  _isMp3 () {
    return this.format.toLowerCase() === 'mp3'
  }
}
