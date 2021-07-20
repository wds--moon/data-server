<template>
  <div class="view-item">
    <div class="view-item-label" :style="style" style="padding-right: 20px">
      <slot name="label">{{ label }}</slot>
    </div>
    <div class="view-item-body">
      <slot name="default" />
    </div>
  </div>
</template>

<script lang="ts">
  import Vue, { PropType } from 'vue'

  export default Vue.extend({
    name: 'ViewItem',
    inject: {
      injectLabelWidth: {
        from: 'labelWidth'
      },
      injectLabelAlign: {
        from: 'labelAlign'
      }
    },
    props: {
      labelWidth: {
        type: [String, Number] as PropType<string | number>,
        required: false,
        default: () => ''
      },
      labelAlign: {
        type: [String] as PropType<string>,
        required: false,
        default: () => ''
      },
      label: {
        type: [String] as PropType<string>,
        required: false,
        default: () => ''
      }
    },
    computed: {
      style () {
        const style = {
          textAlign: (this as any).injectLabelAlign as string,
          width: 'auto'
        }
        // 计算标签宽度
        let labelWidth = this.injectLabelWidth
        // 如果属性指定了宽带，属性优先
        if (this.labelWidth !== '') {
          labelWidth = this.labelWidth
        }
        // 根据最终的结果是字符串还是数据计算最终的style
        if (typeof (labelWidth) === 'string') {
          style.width = labelWidth
        } else {
          style.width = `${labelWidth}px`
        }
        if (this.labelAlign !== '') {
          style.textAlign = this.labelAlign as string
        }
        return style
      }
    }
  })
</script>

<style scoped lang="less">
  .view-item {
    display: flex;
    margin-bottom: 10px;

    .view-item-body {
      flex: 1;
    }
  }
</style>
