<template>
  <el-card>
    <el-collapse v-model="active">
      <el-collapse-item name="1">
        <template #title>
          <h1>服务信息</h1>
        </template>
        <div>服务名称: {{ svc.name }}</div>
        <div>
          调用地址：<a target="_blank" :href="url">{{ url }}</a>&nbsp; <a href="javascript:void(0)" @click="copy">点击复制</a>
        </div>
        <div>服务描述：</div>
        <div>{{ svc.description }}</div>
      </el-collapse-item>
      <el-collapse-item name="2">
        <template #title>
          <h1>参数列表</h1>
        </template>
        <ele-data-tables :data="parameters">
          <el-table-column prop="parameterName" label="参数名称" />
          <el-table-column prop="required" label="是否必要" />
          <el-table-column prop="description" label="参数说明" />
        </ele-data-tables>
      </el-collapse-item>

      <el-collapse-item name="3">
        <template #title>
          <h1>输出字段</h1>
        </template>
        <ele-data-tables :data="meta.columns">
          <el-table-column prop="columnLabel" label="字段名称" />
          <el-table-column prop="columnTypeName" label="值类型" />
          <el-table-column prop="description" label="参数说明">
            <template #default="{row:{columnLabel}}">
              {{ svc.columns[columnLabel] }}
            </template>
          </el-table-column>
        </ele-data-tables>
      </el-collapse-item>
    </el-collapse>
  </el-card>
</template>

<script lang="ts">
  import Vue from 'vue'
  import { Component, Prop } from 'vue-property-decorator'

  import qs from 'qs'
  import URLS from '../URLS'
  import http from '@/http'

  @Component
  export default class SvcViewer extends Vue {
    active = ['1', '2', '3']
    @Prop({ required: true })
    svcId!: string | number

    get url (): string {
      return `${env.CONTEXT_PATH}service/${this.svc.name}`
    }

    svc = {
      requiredParameters: [],
      parameters: [],
      columns: {},
      name: ''
    }

    meta = {}

    get parameters () {
      return [
        ...this.svc.requiredParameters.map((item: any) => ({
          ...item,
          required: '是'
        })),
        ...this.svc.parameters.map((item: any) => ({
          ...item,
          required: '否'
        }))
      ]
    }

    created (): void {
      http.get(`${URLS.svc}/${this.svcId}`).then(({ data: svc }) => (this.svc = svc))
      http.get(`${URLS.svc}/meta/${this.svcId}`).then(({ data: meta }) => (this.meta = meta))
    }

    copy (): void {
      const p = this.parameters.reduce((acc, { parameterName }) => ({
        ...acc,
        [parameterName]: parameterName
      }), {})
      navigator.clipboard.writeText(`${this.url}?${qs.stringify(p)}`)
    }
  }
</script>

<style scoped>

</style>
