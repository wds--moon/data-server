import { config, DataList } from '@/lib/data-list'
import axios from 'axios'
import _Vue from 'vue'

export { DataList, config }

export default {
  install (Vue: typeof _Vue, { httpInstance } = { httpInstance: axios.create() }) {
    config.httpInstance = httpInstance
    Vue.component('DataList', DataList)
  }
}

