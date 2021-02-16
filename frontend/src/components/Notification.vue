<template>
  <div
    :class="[
      `alert-${color}`,
      {
        'mb-4': position.includes('top'),
        'mt-4': position.includes('bottom'),
      },
    ]"
    class="position-relative alert w-100 alert-dismissible"
    role="alert"
  >
    <!-- Message -->
    {{ message }}

    <!-- Close button -->
    <!-- <div
      class="close"
      data-dismiss="alert"
      aria-label="Close"
      @click="$emit('remove')"
    >
      <span aria-hidden="true">&times;</span>
    </div> -->
    <button icon color="white" @click="$emit('remove')"></button>

    <!-- Loading bar -->
    <div
      class="loading-bar position-absolute bg-white"
      :style="{
        width: percentageElapsed + '%',
      }"
    />
  </div>
</template>

<script>
import ToastMixin from "vue-my-toasts/src/mixins/ToastMixin";
import TimerMixin from "vue-my-toasts/src/mixins/TimerMixin";

export default {
  mixins: [ToastMixin, TimerMixin],
  props: {
    title: {
      type: [String, Boolean],
      required: false,
      default: false,
    },
  },
  computed: {
    color() {
      switch (this.type) {
        case "base":
          return "info";
        case "warning":
          return "warning";
        case "error":
          return "danger";
        case "success":
          return "success";
        default:
          return "info";
      }
    },
  },
};
</script>

<style scoped>
.alert {
  margin-bottom: 0;
  margin-top: 15px;
  padding: 10px 10px 10px 10px;
  background-color: black;
  color: white;
  padding: 0;
  display: flex;
  right: 16px;
  width: 300px;
  bottom: 20px;
  border-radius: 5px;
}

.loading-bar {
  height: 0.33rem;
  opacity: 75%;
  bottom: 0;
  left: 0;
}
</style>