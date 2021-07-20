import routes from './routes'
import vuex from './vuex'
import _Vue from 'vue'
import { ModuleOptions } from '../types'

export default {
  install (Vue: typeof _Vue, { router, store }: ModuleOptions): void {
    routes.forEach(route => router.addRoute(route))
    store.registerModule('svc', vuex)
  }
}
