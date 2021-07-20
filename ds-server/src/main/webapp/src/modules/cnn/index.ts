import routes from './routes'
import storeModule from './vuex'
import _Vue from 'vue'
import { ModuleOptions } from '../types'

export default {
  install (Vue: typeof _Vue, {
    router,
    store
  }: ModuleOptions<unknown>): void {
    routes.forEach(route => router.addRoute(route))
    store.registerModule('cnn', storeModule)
  }
}
