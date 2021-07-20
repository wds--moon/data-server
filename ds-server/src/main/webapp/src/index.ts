import Vue from 'vue'
import EleDataTables from 'element-datatables'
import ElementUi from 'element-ui'
import App from '@/App.vue'
import router from '@/router'
import store from '@/store'
import http, { plugin as httpPlugin } from '@/http'
import { dateFormatterPlugin } from '@/plugins'
import ErrorModule from './modules/error'
import Templates from './modules/templates'
import Security from './modules/security'
import Cnn from './modules/cnn'
import Svc from './modules/ds'
import 'element-ui/lib/theme-chalk/index.css'

Vue.use(ElementUi)
Vue.use(EleDataTables, { httpInstance: http })

const rootConfig = {
  router,
  store
}
Vue.use(dateFormatterPlugin)
Vue.use(httpPlugin, rootConfig)
Vue.use(Templates, rootConfig)
Vue.use(ErrorModule, rootConfig)
Vue.use(Cnn, rootConfig)
Vue.use(Security, rootConfig)
Vue.use(Svc, rootConfig)
// Vue.use(TraceModule, rootConfig)
// Vue.use(SecurityModule, rootConfig)
// Vue.use(Code, rootConfig)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
