<template>
  <el-card>
    <el-form ref="svcForm"
             :model="svc"
             :rules="rules"
             label-width="100px">
      <el-collapse v-model="active">
        <el-collapse-item name="1">
          <template #title>
            <h1>服务信息</h1>
          </template>
          <el-row>
            <el-col :span="12">
              <el-form-item label="服务名称" prop="name">
                <el-input v-model="svc.name" placeholder="请输入服务名称，200字符以内" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="服务连接：" prop="connectionId">
                <el-select v-model="svc.connectionId"
                           placeholder="请选择服务连接"
                           style="width: 100%;"
                           @change="getMeta()">
                  <el-option v-for="cnn in connections"
                             :key="cnn.name"
                             :label="cnn.name"
                             :value="cnn.id">
                    <span>{{ cnn.name }}</span>
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col>
              <el-form-item label="标签">
                <el-select v-model="svc.labels"
                           allow-create
                           default-first-option
                           filterable
                           multiple
                           style="width: 100%;">
                  <el-option v-for="label in labels" :key="label" :value="label" />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col>
              <el-form-item label="描述">
                <el-input v-model="svc.description" type="textarea" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col>
              <el-form-item label="SQL语句" prop="sql">
                <el-input v-model="svc.sql" type="textarea" @blur="getMeta()" />
              </el-form-item>
            </el-col>
          </el-row>
        </el-collapse-item>

        <el-collapse-item name="5">
          <template #title>
            <h1>必要参数</h1>
          </template>
          <ele-data-tables :data="svc.requiredParameters">
            <el-table-column label="参数名称" prop="parameterName" />
            <el-table-column label="参数说明">
              <template #default="{row}">
                <el-form-item label-width="0px">
                  <el-input v-model="row.description" />
                </el-form-item>
              </template>
            </el-table-column>
          </ele-data-tables>
        </el-collapse-item>

        <el-collapse-item name="2">
          <template #title>
            <h1>可选参数</h1>
          </template>
          <ele-data-tables :data="svc.parameters">
            <el-table-column label="列名称" prop="column" />
            <el-table-column label="比较符">
              <template #default="{ row }">
                <el-form-item label-width="0px">
                  <el-select v-model="row.operator">
                    <el-option value="=" />
                    <el-option value=">" />
                    <el-option value="<" />
                    <el-option value=">=" />
                    <el-option value="<=" />
                    <el-option value="!=" />
                    <el-option label="部分包含" value="like" />
                    <el-option label="选项之一" value="in" />
                  </el-select>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="参数名称">
              <template #default="{row}">
                <el-form-item label-width="0px">
                  <el-input v-model="row.parameterName" />
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="参数说明">
              <template #default="{row}">
                <el-form-item label-width="0px">
                  <el-input v-model="row.description" />
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template #default="{row}">
                <a href="javascript:void(0);" @click="removeParameter(row)">移除</a>
              </template>
            </el-table-column>
          </ele-data-tables>
        </el-collapse-item>
        <el-collapse-item name="3">
          <template #title>
            <h1>默认排序</h1>
          </template>

          <ele-data-tables :data="svc.orders">
            <el-table-column label="列名称" prop="column" />
            <el-table-column label="排序方式">
              <template #default="{row}">
                <el-form-item label-width="0px">
                  <el-select v-model="row.direction" style="width:100%;">
                    <el-option label="升序" value="ASC" />
                    <el-option label="降序" value="DESC" />
                  </el-select>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template #default="{row}">
                <a href="javascript:void(0);" @click="removeOrder(row)">移除</a>
              </template>
            </el-table-column>
          </ele-data-tables>
        </el-collapse-item>

        <el-collapse-item name="4">
          <template #title>
            <h1>输出字段</h1>
          </template>
          <ele-data-tables :data="meta.columns">
            <el-table-column label="列名称" prop="columnLabel" />
            <el-table-column label="数据类型" prop="columnTypeName" />
            <el-table-column label="说明">
              <template #default="{row:{columnLabel}}">
                <el-input v-model="svc.columns[columnLabel]" />
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template #default="scope">
                <a href="javascript:void(0);" @click="addToParameter(scope.row)">添加到筛选</a>&nbsp;
                <a href="javascript:void(0);" @click="addToOrder(scope.row)">添加到排序</a>
              </template>
            </el-table-column>
          </ele-data-tables>
        </el-collapse-item>
      </el-collapse>
      <el-row>
        <el-col :span="24">
          <el-form-item label-width="0" style="text-align: center">
            <el-button type="primary" @click="submit()">提交</el-button>
            <el-button type="danger" @click="goback()">取消</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </el-card>
</template>
<script lang="ts">
  import { Component, Prop, Vue } from 'vue-property-decorator'
  import { namespace } from 'vuex-class'
  import { AxiosResponse } from 'axios'
  import http from '@/http'
  import URLS from '../URLS'
  import { ColumnMetaData, Parameter } from '@/types/service/service-ds'

  const svcModule = namespace('svc')

  @Component
  export default class SvcForm extends Vue {
    @Prop({ default: 'new' })
    id!: string

    active = ['1', '2', '3', '4', '5']

    // 解析出的列
    meta = {
      columns: []
    }

    connections = []
    svc: any = {
      columns: {},
      orders: [],
      // 服务必要参数
      requiredParameters: []
    }

    labels = []

    rules = {
      name: [
        {
          required: true,
          message: '服务名称不能为空'
        }, {
          max: 200,
          message: '服务名称不能超过200个字符'
        }],
      connectionId: [
        {
          required: true,
          message: '数据连接不能为空'
        }],
      sql: [
        {
          required: true,
          message: 'SQL语句不能为空'
        }]
    }

    @svcModule.Action('listLabels')
    listLabels!: () => Promise<AxiosResponse>

    created (): void {
      http.get(URLS.connection).then(({ data }) => (this.connections = data))
      http.get(URLS.label, { params: { size: 1000 } }).then(({ data: { content } }) => (this.labels = content))
      if (this.id !== 'new') {
        http.get(`${URLS.svc}/${this.id}`).then(({ data }) => (this.svc = data)).then(this.getMeta)
      }
    }

    addToParameter (row: ColumnMetaData): void {
      if (this.svc.parameters) {
        // nothing
      } else {
        this.$set(this.svc, 'parameters', [])
      }
      this.svc.parameters.push({
        column: row.columnLabel,
        operator: '=',
        parameterName: row.columnLabel
      })
    }

    addToOrder (row: ColumnMetaData): void {
      if (this.svc.orders) {
        // do nothing
      } else {
        this.$set(this.svc, 'orders', [])
      }
      this.svc.orders.push({
        column: row.columnLabel,
        direction: 'ASC'
      })
    }

    removeOrder (row: ColumnMetaData): void {
      const index = this.svc.orders.findIndex((item: any) => item === row)
      this.svc.orders.splice(index, 1)
    }

    removeParameter (row: Parameter): void {
      const index = this.svc.parameters.findIndex((item: any) => item === row)
      this.svc.parameters.splice(index, 1)
    }

    submit (): void {
      (this.$refs.svcForm as any).validate().then(() => {
        if (!this.svc.orders || this.svc.orders.length < 1) {
          this.$message.error('必须至少指定一个排序列')
          return Promise.reject(new Error('必须至少指定一个排序列'))
        } else {
          return this.id === 'new'
            ? http.post('/svcs', this.svc)
            : http.put(`/svcs/${this.id}`, this.svc)
        }
      }).then(() => this.$router.go(-1))
    }

    getMeta (): void {
      if (this.svc.connectionId && this.svc.sql) {
        http.post('/svcs/meta', this.svc).then(({ data }) => {
          this.reCalRequiredParameters(data.parameters)
          this.meta = data
        })
      }
    }

    reCalRequiredParameters (parameters: Parameter[]): void {
      if (!this.svc.requiredParameters) {
        this.svc.requiredParameters = []
      }
      // 清理已有参数列表
      this.svc.requiredParameters = this.svc.requiredParameters.filter(
        ({ parameterName }: any) => parameters.includes(parameterName))
      // 重新添加新的参数
      const newParameters = parameters.filter(
        (parameter: any) => this.svc.requiredParameters.findIndex(({ parameterName }: any) => parameterName === parameter) < 0)
        .map((parameter: any) => ({
          parameterName: parameter,
          description: ''
        }))
      this.svc.requiredParameters.push(...newParameters)
    }

    goback (): void {
      this.$router.go(-1)
    }
  }
</script>
<style lang="less">
</style>
