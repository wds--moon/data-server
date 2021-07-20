import _Vue, { PluginObject } from 'vue'
import { AxiosInstance, AxiosRequestConfig } from 'axios'

export interface PluginConfig {
  httpInstance: AxiosInstance
}

export interface Sort {
  prop: string,
  order?: string
}

export interface Pagination {
  page: number,
  size: number,
  total: number
}

interface AjaxFunction {
  (draw: number, pagination: Pagination, sererParams: { [key: string]: unknown }, sort: Sort[],): AxiosRequestConfig
}

export declare class DataList extends _Vue {
  data?: any[]
  http?: AxiosInstance
  ajax?: string | AjaxFunction | object
  serverParams?: { [key: string]: any }
  debounceTime?: number
  sort?: Sort
}

export const config: PluginConfig

declare const plugin: PluginObject<PluginConfig>
export default plugin
