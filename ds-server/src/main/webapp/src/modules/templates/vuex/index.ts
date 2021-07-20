import { BreadcrumbItem, TemplateState } from '../types'

export default {
  namespaced: true,
  state: {
    breadcrumbItems: []
  },
  mutations: {
    breadcrumbItems (state: TemplateState, payload: BreadcrumbItem[] | string[]): void {
      state.breadcrumbItems = payload
    }
  },
  getters: {
    breadcrumbItems ({ breadcrumbItems }: TemplateState): BreadcrumbItem[] {
      return breadcrumbItems.map((item: BreadcrumbItem | string) => {
        return typeof (item) === 'string' ? {
          title: item
        } : item
      })
    }
  }
}
