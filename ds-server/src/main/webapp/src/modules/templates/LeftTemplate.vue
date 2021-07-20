<template>
  <el-container class="left-template" @keydown.native="refreshOperationTime" @click.native="refreshOperationTime">
    <el-header class="header" height="90px">
      <div class="header-left">
        <img class="logo" src="@/asserts/images/logo.png">
        <i class="split" />
        <span class="title">{{ title }}</span>
      </div>
      <div class="header-right">
        <a href="javascript:void(0)" class="btn-logout">
          <img src="@/asserts/images/logout.png">
          <div class="label">退出</div>
        </a>
      </div>
    </el-header>
    <el-container v-loading="loading" style="height: 100%;overflow-y: hidden">
      <el-aside width="220px" class="template-aside">
        <el-menu background-color="#2d2e42"
                 text-color="#ffffff"
                 class="aside-menu"
                 :collapse="menuCollapse">
          <menu-item v-for="(menu,index) in menuTree"
                     :key="index"
                     :item="menu" />
        </el-menu>
      </el-aside>
      <el-main class="content-wrapper">
        <el-breadcrumb class="breadcrumb" separator="/">
          <el-breadcrumb-item v-for="(item,index) in breadcrumbItems"
                              :key="index"
                              :to="item.to">
            {{ item.title }}
          </el-breadcrumb-item>
        </el-breadcrumb>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>
<script lang="ts">
  import Vue from 'vue'
  import { Component, Watch } from 'vue-property-decorator'
  import { namespace } from 'vuex-class'
  import MenuItem from './MenuItem.vue'
  import { MenuDto, MenuTreeItem } from '@/types/service'
  import { ErrorState } from '@/http/types'
  import { BreadcrumbItem } from '@/modules/templates/types'

  const securityModel = namespace('security')
  const httpModel = namespace('http')
  const templateModule = namespace('template')
  const ONE_MINUTE = 60 * 1000
  @Component({
    components: {
      MenuItem
    }
  })
  export default class LeftTemplate extends Vue {
    menuCollapse = false

    /**
     * 记录最后操作时间
     */
    lastOperationTime: number = new Date().getTime()
    /**
     * 保活定时器，用于保存interval的引用，以便clear
     */
    interval = 0

    @httpModel.Getter('loading')
    loading!: boolean

    @httpModel.State('error')
    error!: ErrorState

    @securityModel.Getter('menuTree')
    menuTree?: MenuDto[]

    @securityModel.Getter('findByName')
    findMenuByName!: (name: string) => MenuTreeItem | undefined

    /**
     * 加载菜单信息
     */
    @securityModel.Action('loadMenus')
    loadMenus!: () => Promise<MenuItem[]>

    /**
     * 加载当前用户信息
     */
    @securityModel.Action('loadUserInfo')
    loadCurrentUser!: () => Promise<unknown>

    @templateModule.Getter('breadcrumbItems')
    breadcrumbItems!: BreadcrumbItem[]

    @securityModel.Action('logout')
    logoutAction!: () => Promise<undefined>

    /**
     * 获取系统标题
     */
    get title (): string {
      return this.findMenuByName('root')?.title ?? ''
    }

    created (): void {
      this.loadMenus()
      // this.loadCurrentUser()
      // 每隔一分钟向后台发送一次保活请求
      // this.interval = window.setInterval(() => {
      //   if ((new Date().getTime() - this.lastOperationTime) < ONE_MINUTE) {
      //     // todo 会话保持
      //     // axios.create().get(`${urls.session}`)
      //   } else if ((new Date().getTime() - this.lastOperationTime) > ONE_MINUTE * 12) {
      //     this.$alert('<p>检测到你长时间未操作系统，会话已经过期，需要重新登录</p>', '会话超时',
      //       {
      //         dangerouslyUseHTMLString: true
      //       }
      //     ).finally(() => this.logout())
      //     // 登录超时后，清除定时器
      //     window.clearInterval(this.interval)
      //   }
      //   // 检测当前时间是否在指定时间之内
      // }, ONE_MINUTE)
    }

    destroyed (): void {
      // 组件销毁时，清除定时器
      window.clearInterval(this.interval)
    }

    logout (): void {
      this.logoutAction()
        .finally(() => this.$router.replace({
          name: 'login',
          query: {
            redirect: window.location.href
          }
        }))
    }

    @Watch('error', {
      immediate: false,
      deep: true
    })
    onErrorChange (newValue: ErrorState): void {
      if (newValue.count > 0) {
        this.$message.error(newValue.message)
      }
    }

    // 刷新最后操作时间
    refreshOperationTime (): void {
      // 激活保持
      this.lastOperationTime = new Date().getTime()
    }
  }
</script>
<style lang="less">

  .el-table a {
    margin-right: 5px;
  }

  .left-template {
    .content-wrapper {
      .el-card {
        min-width: 820px;

        &.search-card {
          margin-bottom: 10px;

          .el-card__body {
            padding-bottom: 0;
          }

          form {
            margin-left: unset !important;
            margin-right: unset !important;
          }
        }

        &.edit-card {
          form {
            max-width: 1200px;
            margin: 0 auto;

            .form-button {
              width: 200px;
              height: 60px;
              border-radius: 30px;
              font-size: 20px;
              margin-left: 10px;
              margin-right: 10px;
            }
          }
        }
      }
    }
  }
</style>

<style lang="less" scoped>

  .left-template {
    height: 100%;

    .header {
      display: flex;
      align-items: center;
      padding: 0 25px;
      //border-bottom: solid 1px #eceaea;
      justify-content: space-between;
      background: #386BE1;
      color: white;

      .header-left {
        display: flex;

        align-items: center;

        img.logo {
          width: 90px;
          height: 90px;
        }

        i.split {
          margin: 0 25px;
          height: 45px;
          width: 1px;
          display: inline-block;
          background: white;
        }

        span.title {
          padding-left: 15px;
          font-size: 30px;
        }
      }

      .header-right {
        margin-right: 75px;

        a.btn-logout {
          text-align: center;
          display: inline-block;
          text-decoration: none;

          .label {
            font-size: 12px;
            color: white;
          }
        }
      }
    }

    .content-wrapper {
      background: #f8f8f8;
      padding: 0 10px 10px 10px;

      .breadcrumb {
        height: 60px;
        //display: flex;
        //align-items: center;
        line-height: 60px;
        //margin-top: -30px;
      }
    }

    .template-aside {
      display: flex;
      flex-direction: column;
      background: #2d2e42;
      // 暴力的将超出部分隐藏起来
      overflow-x: hidden;

      .menu-control {
        color: white;
        border-right: solid 1px #2d2e42;
        text-align: center;
        cursor: pointer;
      }

      .aside-menu {
        flex: 1 1 auto;
        border-right: solid 1px #2d2e42;
      }
    }
  }
</style>
