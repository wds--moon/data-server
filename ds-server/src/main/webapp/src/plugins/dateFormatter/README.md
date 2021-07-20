使用dayjs格式化日期和时间

向vue中注入两个filter

* formatDate
* formatDatetime

它们没有本质的区别，仅仅是默认格式化参数不一致，当两个filter都指定参数时，格式化结果是一致的

使用方法如下

```vue

<template>
    <div>
        <!--格式化为 2020-02-02 13:14:00 样式-->
        {{ date|formatDatetime }}
        <!--格式化为 2020-02-02 样式-->
        {{ date|formatDate }}
        <!--格式化为 2020-02-02 样式-->
        {{ date|formatDatetime('YYYY-MM-DD') }}
        <!--格式化为 2020-02-02 样式-->
        {{ date|formatDate('YYYY-MM-DD') }}
    </div>
</template>

<script>
    export default {
        data () {
            return {
                date: new Date()
            }
        }
    }
</script>

<style scoped>
</style>
```

相关资料查看[dayjs参考](https://github.com/iamkun/dayjs)
