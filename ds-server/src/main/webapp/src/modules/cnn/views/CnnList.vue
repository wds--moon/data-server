<template>
  <el-card>
    <el-form inline>
      <el-row>
        <el-col>
          <el-form-item>
            <el-input v-model="serverParams.keyword" placeholder="请输入名称" />
          </el-form-item>
          <el-form-item class="float-right">
            <el-button type="primary" @click="$router.push({name:'cnn',params:{id:'new'}})">新增连接</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <ele-data-tables :ajax="tableUrl" :server-params="serverParams">
      <el-table-column prop="name" label="连接名称" width="150" />
      <el-table-column prop="dbType" label="数据库类型" width="100" />
      <el-table-column prop="host" label="连接地址" />
      <el-table-column prop="database" label="默认数据库" />
      <el-table-column prop="username" label="用户名" width="150" />
      <el-table-column label="操作" width="100">
        <template #default="{row:{id}}">
          <router-link :to="`/cnn/${id}`">编辑</router-link>
        </template>
      </el-table-column>
    </ele-data-tables>
  </el-card>
</template>
<script lang="ts">
  import Vue from 'vue'
  import { Component } from 'vue-property-decorator'
  import URLS from '../URLS'

  @Component
  export default class CnnList extends Vue {
    tableUrl = URLS.cnn
    serverParams = {
      keyword: ''
    }
  }
</script>
<style lang="less">
</style>
