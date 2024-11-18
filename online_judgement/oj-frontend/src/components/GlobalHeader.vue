<template>
  <div id="globalHeader">
    <a-menu mode="horizontal" :default-selected-keys="['1']">
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
      <a-menu-item key="1">Home</a-menu-item>
      <a-menu-item key="2">Solution</a-menu-item>
      <a-menu-item key="3">Cloud Service</a-menu-item>
      <a-menu-item key="4">Cooperation</a-menu-item>
    </a-menu>
  </div>
</template>

<script lang="ts"></script>

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
