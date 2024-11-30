<template>
  <div id="userLayout">
    <a-layout style="min-height: 100vh">
      <!-- 顶部 -->
      <a-layout-header class="header">用户布局</a-layout-header>
      <!-- 动态页面 -->
      <a-layout-content class="content">
        <router-view />
      </a-layout-content>
      <!-- 页尾 -->
      <a-layout-footer class="footer">
        <a href="https://www.linkedin.com/in/yuhan-aaron-tan/" target="_blank">
          OJ by TYH
        </a>
      </a-layout-footer>
    </a-layout>
  </div>
</template>

<style scoped>
#userLayout {
}

#userLayout .header {
  margin-bottom: 13px;
  box-shadow: #eee 1px 1px 1px;
}

#userLayout .content {
  background: linear-gradient(to right, #bbb, #fff);
  margin-bottom: 16px;
  padding: 20px;
}

#userLayout .footer {
  background: #efefef;
  padding: 16px;
  position: sticky;
  bottom: 0;
  left: 0;
  right: 0;
  text-align: center;
}
</style>

<script setup lang="ts"></script>
