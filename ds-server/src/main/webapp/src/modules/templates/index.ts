import { ModuleOptions } from '@/modules'
import vuex from './vuex'

export default {
  install (vue: unknown, { store }: ModuleOptions): void {
    store.registerModule('template', vuex)
  }
}
