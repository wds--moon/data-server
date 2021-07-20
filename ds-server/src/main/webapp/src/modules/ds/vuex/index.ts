import http from '@/http'
import URLS from '../URLS'
import { AxiosResponse } from 'axios'

export default {
  namespaced: true,
  actions: {
    listLabels (): Promise<AxiosResponse> {
      return http.get(URLS.label, { params: { size: 1000 } })
    }
  }
}
