<template>
  <el-form ref="cnnForm"
           :model="cnn"
           label-width="100px"
           :rules="rules">
    <h3>连接信息</h3>
    <el-row>
      <el-col :span="12">
        <el-form-item label="数据库类型" prop="dbType">
          <el-select v-model="cnn.dbType" style="width: 100%;">
            <el-option value="SQLSERVER"/>
            <el-option value="MySQL8"/>
            <el-option value="PostgreSQL"/>
            <el-option value="Oracle"/>
            <el-option value="DM"/>
            <el-option value="Kingbase"/>
            <el-option value="GBase"/>
          </el-select>
        </el-form-item>
      </el-col>

      <el-col :span="6">
        <el-form-item label="主机" prop="host">
          <el-input v-model="cnn.host" clearable/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="端口" prop="port">
          <el-input v-model.number="cnn.port" clearable/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="cnn.username" clearable/>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="密码" prop="password">
          <el-input v-model="cnn.password" type="password" clearable/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="连接名称" prop="name">
          <el-input v-model="cnn.name" clearable/>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="默认数据库" prop="database">
          <el-input v-model="cnn.database" clearable/>
        </el-form-item>
      </el-col>
    </el-row>

    <el-form v-for="(prop,index) in properties"
             ref="propForm"
             :key="index"
             label-width="100px"
             :model="properties[index]"
             :rules="propRules">
      <el-row>
        <el-col :span="8">
          <el-form-item :label="`属性${index+1}`" prop="key">
            <el-select v-model="prop.key" style="width: 250px;" filterable>
              <el-option v-for="property in propertiesToUser" :key="property" :value="property"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="16">
          <el-form-item label="属性值" prop="value">
            <el-input v-model="prop.value" class="input-with-select">
              <template #append>
                <el-button type="danger"
                           icon="el-icon-delete"
                           @click="delProperty(index)"/>
              </template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-row>
      <el-col>
        <el-form-item>
          <el-button type="success" @click="addProperty">添加属性</el-button>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <el-form-item label="备注" prop="remark">
          <el-input v-model="cnn.remark" type="textarea"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <el-form-item label-width="0" style="text-align: center">
          <el-button type="info" @click="test">测试</el-button>
          <el-button type="primary" @click="submit()">提交</el-button>
          <el-button type="danger" @click="back()">取消</el-button>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>
<script lang="ts">
import Vue from 'vue'
import {Component, Prop} from 'vue-property-decorator'
import URLS from '../URLS'
import properties from './properties'
import http from '@/http'
import {Rules} from 'async-validator'
import {AxiosResponse} from 'axios'
import {DbTypes, DmDataSourceDto} from '@/types/service/service-ds'

// import Connection, { DbType, Property } from '../types/Connection'

@Component({})
export default class CnnForm extends Vue {
  @Prop({default: 'new'})
  id!: string | number

  cnn: DmDataSourceDto = {
    dbType: DbTypes.MySQL8,
    port: 3306
  }

  properties: { key: string, value?: string }[] = []

  get propertiesToUser(): string[] {
    // 已经使用的属性
    const {properties: prop = {}} = this.cnn
    const propUsed = Object.keys(prop)
    if (this.cnn?.dbType !== undefined) {
      return properties[this.cnn.dbType].filter(p => !propUsed.includes(p))
    } else {
      return []
    }
  }

  get request(): DmDataSourceDto {
    const request: DmDataSourceDto = JSON.parse(JSON.stringify(this.cnn))
    if (request.properties) {
      request.properties = (this.properties).reduce((acc, {key, value = ''}) => ({
        ...acc,
        [key]: value
      }), ({} as { [key: string]: string }))
    }
    return request
  }

  // 指示值是否改变
  change = false

  rules: Rules = {
    name: [{
      required: true,
      message: '请输入连接名称',
      trigger: 'blur'
    }, {
      max: 200,
      message: '内容超过系统长度限制'
    }],
    dbType: [{
      required: true,
      message: '数据类型不能为空',
      trigger: 'change'
    }],
    host: [{
      required: true,
      message: '主机地址不能为空',
      trigger: 'blur'
    }, {
      max: 100,
      message: '主机名称过长'
    }],
    port: [{
      type: 'integer',
      min: 1,
      max: 65535,
      required: true,
      message: '请输入正确的端口号',
      trigger: 'blur'
    }],
    username: [{
      required: true,
      message: '用户名不能为空',
      trigger: 'blur'
    }, {
      max: 200,
      message: '内容超过系统长度限制'
    }],
    password: [{
      required: true,
      message: '密码不能为空',
      trigger: 'blur'
    }, {
      max: 200,
      message: '内容超过系统长度限制'
    }],
    database: [{
      max: 200,
      message: '内容超过系统长度限制'
    }],
    remark: [{
      max: 4000,
      message: '备注长度不能超过4000个字符'
    }]
  }

  propRules: Rules = {
    key: [{
      required: true,
      message: '属性名不能为空'
    }],
    value: [{
      required: true,
      message: '属性值不能为空'
    }]
  }

  addProperty(): void {
    this.properties.push({key: '', value: ''})
  }

  delProperty(index: number): void {
    this.properties?.splice(index, 1)
  }

  checkState(params: DmDataSourceDto): Promise<AxiosResponse> {
    return http.post(`${URLS.cnn}/state`, params)
  }

  created(): void {
    if (this.id !== 'new') {
      http.get(`${URLS.cnn}/${this.id}`).then(({data}) => {
        this.cnn = data
      })
    }
  }

  back(): void {
    this.$router.go(-1)
  }

  test(): Promise<unknown> {
    return this.validate()
      .then(() => this.checkState(this.request))
      .then(({data: {result, message}}) => {
        if (result) {
          this.$alert('测试成功', '连接测试', {type: 'success'})
        } else {
          this.$alert(`连接测试失败,${message}`, '连接测试', {type: 'error'})
        }
      })
  }

  validate(): Promise<boolean[]> {
    return Promise.all([
      (this.$refs.cnnForm as any).validate(),
      ...((this.$refs.propForm as any[])?.map(v => v.validate()) ?? [])
    ])
  }

  submit(): Promise<unknown> {
    return this.validate()
      .then(() => this.checkState(this.request))
      .then(({data: {result}}: { data: { result: boolean } }) => {
        if (result) {
          return this.id === 'new'
            ? http.post(`${URLS.cnn}`, this.request)
            : http.put(`${URLS.cnn}/${this.id}`, this.request)
        } else {
          this.$message.error('连接校验失败，不能保存')
          return Promise.reject(new Error('连接校验失败，不能保存'))
        }
      }).then(this.back)
  }
}
</script>
<style lang="less">
</style>
