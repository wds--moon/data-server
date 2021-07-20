import Vue, { PluginObject } from 'vue'
import { format, formatDate, formatDatetime, formatTime } from './formatters'

const plugin: PluginObject<unknown> = {
  install (_Vue: typeof Vue) {
    _Vue.filter('formatDate', formatDate)
    _Vue.filter('formatDatetime', formatDatetime)
    _Vue.filter('formatTime', formatTime)
  }
}

export { plugin, formatDatetime, formatDate, format, formatTime }
