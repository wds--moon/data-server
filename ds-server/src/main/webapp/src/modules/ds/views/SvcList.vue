<template>
  <el-card>
    <el-row>
      <el-col :span="18">
        <el-form :inline="true">
          <el-form-item>
            <el-select v-model="searchObj.label"
                       placeholder="请选择分类标签"
                       multiple
                       clearable
                       filterable>
              <el-option v-for="label in labels" :key="label" :value="label" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-input v-model="searchObj.search"
                      type="text"
                      placeholder="名称或描述"
                      clearable />
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="6">
        <el-button style="float: right;" type="primary" @click="$router.push({name:'svc',params:{id:'new'}})">
          新增服务
        </el-button>
      </el-col>
    </el-row>
    <ele-data-tables ref="table" :ajax="tableUrl" :server-params="searchObj">
      <el-table-column prop="name" label="服务名称">
        <template #default="{row}">
          <router-link :to="{name:'svcView',params:{svcId:row.id}}">{{ row.name }}</router-link>&nbsp;
        </template>
      </el-table-column>
      <el-table-column label="服务标签">
        <template #default="{row:{labels=[]}}">
          <span v-for="label in labels"
                :key="label"
                style="border:solid 1px lawngreen;margin-right: 5px;">
            {{ label }}
          </span>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="服务描述" />
      <el-table-column label="操作" width="100">
        <template #default="{row}">
          <router-link :to="{name:'svc',params:{id:row.id}}">编辑</router-link>&nbsp;
          <a href="javascript:void(0)" @click="delSvc(row)">删除</a>
        </template>
      </el-table-column>
    </ele-data-tables>
  </el-card>
</template>
<script lang="ts">
  import { Component } from 'vue-property-decorator'
  import Vue from 'vue'
  // import { UrlCfg } from '@/services'
  import { namespace } from 'vuex-class'
  import URLS from '../URLS'
  import http from '@/http'
  import { AxiosResponse } from 'axios'

  const svcModule = namespace('svc')

  @Component
  export default class SvcList extends Vue {
    tableUrl = URLS.svc

    searchObj = {}

    @svcModule.Action('listLabels')
    listLabels!: () => Promise<AxiosResponse>

    labels = []

    created (): void {
      this.listLabels().then(({ data: { content } }: any) => (this.labels = content))
    }

    delSvc (svc: { id: string }): Promise<unknown> {
      return this.$confirm('此操作将永久删除该服务, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => http.delete(`${URLS.svc}/${svc.id}`))
        .then(() => {
          (this.$refs.table as any).reloadData()
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    }
  }
</script>
<style lang="less">
</style>
