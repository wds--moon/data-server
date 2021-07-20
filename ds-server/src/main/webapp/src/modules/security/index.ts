import routes from './routes'
import _Vue from 'vue'
import { ModuleOptions } from '../types'
import vuex from './vuex'

export default {
  install (Vue: typeof _Vue, {
    router,
    store
  }: ModuleOptions): void {
    routes.forEach(route => router.addRoute(route))
    store.registerModule('security', vuex)
  }
}
