<template>
  <a-row id="globalHeader" align="center" :wrap="false">
    <a-col flex="auto">
      <a-menu
        mode="horizontal"
        :selected-keys="selectedKeys"
        @menu-item-click="doMenuClick"
      >
        <a-menu-item
          key="0"
          :style="{ padding: 0, marginRight: '38px' }"
          disabled
        >
          <div class="title-bar">
            <img class="headerPic" src="../assets/HeaderPic.png" />
            <div class="headerTitle">Online Judgement</div>
          </div>
        </a-menu-item>
        <!--遍历routes的页面，然后展示出来-->
        <a-menu-item v-for="item in visibleRoutes" :key="item.path">
          {{ item.name }}
        </a-menu-item>
      </a-menu>
    </a-col>
    <a-col flex="100px">
      <div>
        {{ store.state.user?.loginUser?.userName ?? 未登录 }}
      </div>
    </a-col>
  </a-row>
</template>

<script setup lang="ts">
import { routes } from "../router/routes";
import { useRouter, useRoute } from "vue-router";
import { computed, ref } from "vue";
import { useStore } from "vuex";
import checkAccess from "@/access/checkAccess";
import ACCESS_ENUM from "@/access/accessEnum";

// 默认主页
const selectedKeys = ref(["/"]);

// 获取路由文件
const router = useRouter();
const route = useRoute();
// 获取用户登录信息 (保存在vuex中)
const store = useStore();

const loginUser = store.state.user.loginUser;

// 展示在菜单的路由数组
const visibleRoutes = computed(() => {
  return routes.filter((item, index) => {
    if (item.meta?.hideInMenu) {
      return false;
    }

    // 获取当前用户登陆状态
    const loginUser = store.state.user.loginUser;

    // 根据权限过滤菜单loginUser
    if (!checkAccess(loginUser, item?.meta?.access as string)) {
      return false;
    }
    return true;
  });
});

// 路由跳转时，更新选中的菜单项
router.afterEach((to, from, next) => {
  selectedKeys.value = [to.path];
});

// 点击菜单，根据路由跳转
const doMenuClick = (key: string) => {
  router.push({ path: key });
};

// 测试登录状态变化
// setTimeout(() => {
//   store.dispatch("user/getLoginUser", {
//     userName: "TYH",
//     userRole: ACCESS_ENUM.ADMIN,
//   });
// }, 1000);
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.title-bar {
  display: flex;
  align-items: center;
}

.headerTitle {
  color: #444;
  margin-left: 20px;
}

.headerPic {
  height: 40px;
}
</style>
