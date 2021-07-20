import { Route } from 'vue-router'
import { EsModuleComponent } from 'vue/types/options'

export default [{
  path: '/errors/:code',
  name: 'error',
  props: ({
    params,
    query
  }: Route): any => ({ ...params, ...query }),
  component: (): Promise<EsModuleComponent> => import('./views/ErrorPage.vue')
}, {
  path: '*',
  props: (): any => ({ code: '404' }),
  component: (): Promise<EsModuleComponent> => import('./views/ErrorPage.vue')
}]
