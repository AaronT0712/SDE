<template>
  <div id="app">
    <BasicLayout />
  </div>
</template>

<style>
#app {
}
</style>

<script setup="ts">
import BasicLayout from "@/layouts/BasicLayout.vue";
import { useRouter } from "vue-router";

const router = useRouter();
router.beforeEach((to, from, next) => {
  if(to.meta?.access === "canAdmin") {

  }
})
</script>
