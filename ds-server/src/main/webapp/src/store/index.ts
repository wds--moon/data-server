import Vue from 'vue'
import Vuex, { Store } from 'vuex'

Vue.use(Vuex)
export default new Store<{ title: string }>({
  state: {
    title: '应用'
  },
  mutations: {
    title (state, payload) {
      if (payload.title) {
        state.title = payload.title
      }
    }
  }
})
