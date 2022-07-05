<template>
  <div>
    <el-menu
        style="width: 200px; min-height: calc(100vh - calc(100vh /15 ) - 1px) ; height: 100% ; max-width: calc(100vw - 200px)"
        default-active="2"
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
        :router="true">
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>导航一</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="index">选项1</el-menu-item>
          <el-menu-item index="index">选项2</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-menu-item index="navigation1">
        <i class="el-icon-menu"></i>
        <span slot="title">导航二</span>
      </el-menu-item>
      <el-menu-item index="navigation2" >
        <i class="el-icon-document"></i>
        <span slot="title">导航三</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "Aside",
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    }
  }
}
</script>

<style scoped>

</style>