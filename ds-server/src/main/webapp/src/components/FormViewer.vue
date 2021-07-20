<template>
  <div class="form-viewer">
    <slot name="default" />
  </div>
</template>

<script lang="ts">
  import Vue, { PropType } from 'vue'

  /**
   * 表单预览组件
   */
  export default Vue.extend({
    name: 'FormViewer',
    provide () {
      return {
        labelWidth: this.labelWidth,
        labelAlign: this.labelAlign
      }
    },
    props: {
      labelWidth: {
        type: [String, Number] as PropType<string | number>,
        required: false,
        default: () => '100px'
      },
      labelAlign: {
        type: [String] as PropType<string>,
        required: false,
        default: () => 'left'
      }
    }
  })
</script>

<style scoped>

</style>
