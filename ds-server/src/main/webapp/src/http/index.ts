import http from './http'
import _Vue, { PluginObject } from 'vue'
import store from './vuex'
import { PluginOptions } from './types'
import httpOptions from './pluginOptions'

const plugin: PluginObject<PluginOptions> = {
  install (Vue: typeof _Vue, options?: PluginOptions): void {
    options?.store?.registerModule('http', store)
    httpOptions.store = options?.store
  }
}

export default http

export { plugin, store, httpOptions }
