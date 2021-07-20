import { Store } from 'vuex'

export interface ErrorState {
  count: number,
  message: string
}

export interface HttpState {
  error: ErrorState,
  requestKey: number
  requestCount: number,
  requests: {
    [key: string]: any
  }
}

export interface PluginOptions<T = any> {
  store?: Store<T>
}
