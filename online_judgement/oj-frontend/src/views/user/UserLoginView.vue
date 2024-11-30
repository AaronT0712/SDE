<template>
  <div id="userLoginView">
    <a-form
      style="max-width: 480px; margin: 0 auto"
      label-align="left"
      auto-label-width
      :model="form"
      @submit="handleSubmit"
    >
      <!--输账号-->
      <!--这里的field, 要和后端的对其-->
      <a-form-item field="userAccount" label="账号">
        <!--将用户输入的内容实时同步到 form.userAccount 中，就是下方的 form-->
        <a-input v-model="form.userAccount" placeholder="请输入账号" />
      </a-form-item>
      <!--输密码-->
      <a-form-item field="userPassword" tooltip="密码不少于 8 位" label="密码">
        <a-input-password
          v-model="form.userPassword"
          placeholder="请输入密码"
        />
      </a-form-item>
      <!--登录按钮-->
      <a-form-item>
        <a-button type="primary" html-type="submit" style="width: 120px"
          >登录
        </a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<!-- 这些都是从Arco Design复制下来的 -->
<script setup lang="ts">
import { errorMessages } from "@vue/compiler-core";
import { reactive } from "vue";
import { UserControllerService, UserLoginRequest } from "../../../generated";
import message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";
import * as repl from "node:repl";
import { useStore } from "vuex";
import ACCESS_ENUM from "@/access/accessEnum";

/**
 * 表单信息
 */
const form = reactive({
  userAccount: "",
  userPassword: "",
} as UserLoginRequest); // 变成下方 post函数所需要的参数类型

/**
 * 提交表单，实现登录
 * @param data
 */
const router = useRouter(); // 路由：用于跳转
const store = useStore(); // 用于读取登录信息

const handleSubmit = async () => {
  const res = await UserControllerService.userLoginUsingPost(form);
  // 登录成功后：提示 + 更新用户登录信息 + 跳转页面
  if (res.code === 0) {
    alert("登陆成功: " + JSON.stringify(form));
    // 更新用户登录信息
    await store.dispatch("user/getLoginUser", res.data); // 要加上res.data才可以生效
    // 跳转
    router.push({
      path: "/",
      replace: true,
    });
  } else {
    message.error("登录失败: " + res.message);
  }
};
</script>
