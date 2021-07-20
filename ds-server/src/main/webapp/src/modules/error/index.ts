import routes from './routes'
import _Vue from 'vue'
import { ModuleOptions } from '../types'

export default {
  install (Vue: typeof _Vue, {
    router
  }: ModuleOptions): void {
    routes.forEach(route => router.addRoute(route))
    // store.registerModule('system', vuex)
  }
}
