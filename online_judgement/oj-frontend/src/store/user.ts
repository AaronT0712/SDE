import { StoreOptions } from "vuex";

export default {
  namespaced: true,
  state: () => ({
    loginUser: {
      userName: "未登录",
      role: "notlogin",
    },
  }),
  actions: {
    // TODO: 改成从远程请求，获取登录信息
    getLoginUser({ commit, state }, payload) {
      commit("updateUser", { userName: "TYH", role: "admin" });
    },
  },
  mutations: {
    updateUser(state, payload) {
      state.loginUser = payload;
    },
  },
} as StoreOptions<any>;
