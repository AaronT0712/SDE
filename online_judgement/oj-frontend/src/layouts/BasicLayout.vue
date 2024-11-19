<template>
  <div id="basicLayout">
    <a-layout style="height: 400px">
      <!-- 顶部 -->
      <a-layout-header class="header">
        <global-header />
      </a-layout-header>
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
#basicLayout {
}

#basicLayout .header {
  background: red;
  margin-bottom: 16px;
}

#basicLayout .content {
  background: linear-gradient(to right, #bbb, #fff);
  margin-bottom: 16px;
}

#basicLayout .footer {
  background: #efefef;
  padding: 16px;
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  text-align: center;
}
</style>
<script setup lang="ts">
import GlobalHeader from "@/components/GlobalHeader.vue";
</script>
