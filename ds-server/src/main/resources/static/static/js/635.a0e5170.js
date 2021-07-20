(self.webpackChunkdm_webapp=self.webpackChunkdm_webapp||[]).push([[635],{64635:function(e,t,r){"use strict";r.r(t),r.d(t,{default:function(){return m}});var a=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("el-card",[r("el-form",{ref:"svcForm",attrs:{model:e.svc,rules:e.rules,"label-width":"100px"}},[r("el-collapse",{model:{value:e.active,callback:function(t){e.active=t},expression:"active"}},[r("el-collapse-item",{attrs:{name:"1"},scopedSlots:e._u([{key:"title",fn:function(){return[r("h1",[e._v("服务信息")])]},proxy:!0}])},[e._v(" "),r("el-row",[r("el-col",{attrs:{span:12}},[r("el-form-item",{attrs:{label:"服务名称",prop:"name"}},[r("el-input",{attrs:{placeholder:"请输入服务名称，200字符以内"},model:{value:e.svc.name,callback:function(t){e.$set(e.svc,"name",t)},expression:"svc.name"}})],1)],1),e._v(" "),r("el-col",{attrs:{span:12}},[r("el-form-item",{attrs:{label:"服务连接：",prop:"connectionId"}},[r("el-select",{staticStyle:{width:"100%"},attrs:{placeholder:"请选择服务连接"},on:{change:function(t){return e.getMeta()}},model:{value:e.svc.connectionId,callback:function(t){e.$set(e.svc,"connectionId",t)},expression:"svc.connectionId"}},e._l(e.connections,(function(t){return r("el-option",{key:t.name,attrs:{label:t.name,value:t.id}},[r("span",[e._v(e._s(t.name))])])})),1)],1)],1)],1),e._v(" "),r("el-row",[r("el-col",[r("el-form-item",{attrs:{label:"标签"}},[r("el-select",{staticStyle:{width:"100%"},attrs:{"allow-create":"","default-first-option":"",filterable:"",multiple:""},model:{value:e.svc.labels,callback:function(t){e.$set(e.svc,"labels",t)},expression:"svc.labels"}},e._l(e.labels,(function(e){return r("el-option",{key:e,attrs:{value:e}})})),1)],1)],1)],1),e._v(" "),r("el-row",[r("el-col",[r("el-form-item",{attrs:{label:"描述"}},[r("el-input",{attrs:{type:"textarea"},model:{value:e.svc.description,callback:function(t){e.$set(e.svc,"description",t)},expression:"svc.description"}})],1)],1)],1),e._v(" "),r("el-row",[r("el-col",[r("el-form-item",{attrs:{label:"SQL语句",prop:"sql"}},[r("el-input",{attrs:{type:"textarea"},on:{blur:function(t){return e.getMeta()}},model:{value:e.svc.sql,callback:function(t){e.$set(e.svc,"sql",t)},expression:"svc.sql"}})],1)],1)],1)],1),e._v(" "),r("el-collapse-item",{attrs:{name:"5"},scopedSlots:e._u([{key:"title",fn:function(){return[r("h1",[e._v("必要参数")])]},proxy:!0}])},[e._v(" "),r("ele-data-tables",{attrs:{data:e.svc.requiredParameters}},[r("el-table-column",{attrs:{label:"参数名称",prop:"parameterName"}}),e._v(" "),r("el-table-column",{attrs:{label:"参数说明"},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[r("el-form-item",{attrs:{"label-width":"0px"}},[r("el-input",{model:{value:a.description,callback:function(t){e.$set(a,"description",t)},expression:"row.description"}})],1)]}}])})],1)],1),e._v(" "),r("el-collapse-item",{attrs:{name:"2"},scopedSlots:e._u([{key:"title",fn:function(){return[r("h1",[e._v("可选参数")])]},proxy:!0}])},[e._v(" "),r("ele-data-tables",{attrs:{data:e.svc.parameters}},[r("el-table-column",{attrs:{label:"列名称",prop:"column"}}),e._v(" "),r("el-table-column",{attrs:{label:"比较符"},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[r("el-form-item",{attrs:{"label-width":"0px"}},[r("el-select",{model:{value:a.operator,callback:function(t){e.$set(a,"operator",t)},expression:"row.operator"}},[r("el-option",{attrs:{value:"="}}),e._v(" "),r("el-option",{attrs:{value:">"}}),e._v(" "),r("el-option",{attrs:{value:"<"}}),e._v(" "),r("el-option",{attrs:{value:">="}}),e._v(" "),r("el-option",{attrs:{value:"<="}}),e._v(" "),r("el-option",{attrs:{value:"!="}}),e._v(" "),r("el-option",{attrs:{label:"部分包含",value:"like"}}),e._v(" "),r("el-option",{attrs:{label:"选项之一",value:"in"}})],1)],1)]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"参数名称"},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[r("el-form-item",{attrs:{"label-width":"0px"}},[r("el-input",{model:{value:a.parameterName,callback:function(t){e.$set(a,"parameterName",t)},expression:"row.parameterName"}})],1)]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"参数说明"},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[r("el-form-item",{attrs:{"label-width":"0px"}},[r("el-input",{model:{value:a.description,callback:function(t){e.$set(a,"description",t)},expression:"row.description"}})],1)]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[r("a",{attrs:{href:"javascript:void(0);"},on:{click:function(t){return e.removeParameter(a)}}},[e._v("移除")])]}}])})],1)],1),e._v(" "),r("el-collapse-item",{attrs:{name:"3"},scopedSlots:e._u([{key:"title",fn:function(){return[r("h1",[e._v("默认排序")])]},proxy:!0}])},[e._v(" "),r("ele-data-tables",{attrs:{data:e.svc.orders}},[r("el-table-column",{attrs:{label:"列名称",prop:"column"}}),e._v(" "),r("el-table-column",{attrs:{label:"排序方式"},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[r("el-form-item",{attrs:{"label-width":"0px"}},[r("el-select",{staticStyle:{width:"100%"},model:{value:a.direction,callback:function(t){e.$set(a,"direction",t)},expression:"row.direction"}},[r("el-option",{attrs:{label:"升序",value:"ASC"}}),e._v(" "),r("el-option",{attrs:{label:"降序",value:"DESC"}})],1)],1)]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[r("a",{attrs:{href:"javascript:void(0);"},on:{click:function(t){return e.removeOrder(a)}}},[e._v("移除")])]}}])})],1)],1),e._v(" "),r("el-collapse-item",{attrs:{name:"4"},scopedSlots:e._u([{key:"title",fn:function(){return[r("h1",[e._v("输出字段")])]},proxy:!0}])},[e._v(" "),r("ele-data-tables",{attrs:{data:e.meta.columns}},[r("el-table-column",{attrs:{label:"列名称",prop:"columnLabel"}}),e._v(" "),r("el-table-column",{attrs:{label:"数据类型",prop:"columnTypeName"}}),e._v(" "),r("el-table-column",{attrs:{label:"说明"},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row.columnLabel;return[r("el-input",{model:{value:e.svc.columns[a],callback:function(t){e.$set(e.svc.columns,a,t)},expression:"svc.columns[columnLabel]"}})]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("a",{attrs:{href:"javascript:void(0);"},on:{click:function(r){return e.addToParameter(t.row)}}},[e._v("添加到筛选")]),e._v(" \n              "),r("a",{attrs:{href:"javascript:void(0);"},on:{click:function(r){return e.addToOrder(t.row)}}},[e._v("添加到排序")])]}}])})],1)],1)],1),e._v(" "),r("el-row",[r("el-col",{attrs:{span:24}},[r("el-form-item",{staticStyle:{"text-align":"center"},attrs:{"label-width":"0"}},[r("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.submit()}}},[e._v("提交")]),e._v(" "),r("el-button",{attrs:{type:"danger"},on:{click:function(t){return e.goback()}}},[e._v("取消")])],1)],1)],1)],1)],1)};a._withStripped=!0;var l,o=r(56739),n=r(93459),s=r(8078),c=r(54671),i=(l=function(e,t){return(l=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var r in t)Object.prototype.hasOwnProperty.call(t,r)&&(e[r]=t[r])})(e,t)},function(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Class extends value "+String(t)+" is not a constructor or null");function r(){this.constructor=e}l(e,t),e.prototype=null===t?Object.create(t):(r.prototype=t.prototype,new r)}),u=function(e,t,r,a){var l,o=arguments.length,n=o<3?t:null===a?a=Object.getOwnPropertyDescriptor(t,r):a;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)n=Reflect.decorate(e,t,r,a);else for(var s=e.length-1;s>=0;s--)(l=e[s])&&(n=(o<3?l(n):o>3?l(t,r,n):l(t,r))||n);return o>3&&n&&Object.defineProperty(t,r,n),n},p=(0,n.uD)("svc"),v=function(e){function t(){var t=null!==e&&e.apply(this,arguments)||this;return t.active=["1","2","3","4","5"],t.meta={columns:[]},t.connections=[],t.svc={columns:{},orders:[],requiredParameters:[]},t.labels=[],t.rules={name:[{required:!0,message:"服务名称不能为空"},{max:200,message:"服务名称不能超过200个字符"}],connectionId:[{required:!0,message:"数据连接不能为空"}],sql:[{required:!0,message:"SQL语句不能为空"}]},t}return i(t,e),t.prototype.created=function(){var e=this;s.ZP.get(c.Z.connection).then((function(t){var r=t.data;return e.connections=r})),s.ZP.get(c.Z.label,{params:{size:1e3}}).then((function(t){var r=t.data.content;return e.labels=r})),"new"!==this.id&&s.ZP.get(c.Z.svc+"/"+this.id).then((function(t){var r=t.data;return e.svc=r})).then(this.getMeta)},t.prototype.addToParameter=function(e){this.svc.parameters||this.$set(this.svc,"parameters",[]),this.svc.parameters.push({column:e.columnLabel,operator:"=",parameterName:e.columnLabel})},t.prototype.addToOrder=function(e){this.svc.orders||this.$set(this.svc,"orders",[]),this.svc.orders.push({column:e.columnLabel,direction:"ASC"})},t.prototype.removeOrder=function(e){var t=this.svc.orders.findIndex((function(t){return t===e}));this.svc.orders.splice(t,1)},t.prototype.removeParameter=function(e){var t=this.svc.parameters.findIndex((function(t){return t===e}));this.svc.parameters.splice(t,1)},t.prototype.submit=function(){var e=this;this.$refs.svcForm.validate().then((function(){return!e.svc.orders||e.svc.orders.length<1?(e.$message.error("必须至少指定一个排序列"),Promise.reject(new Error("必须至少指定一个排序列"))):"new"===e.id?s.ZP.post("/svcs",e.svc):s.ZP.put("/svcs/"+e.id,e.svc)})).then((function(){return e.$router.go(-1)}))},t.prototype.getMeta=function(){var e=this;this.svc.connectionId&&this.svc.sql&&s.ZP.post("/svcs/meta",this.svc).then((function(t){var r=t.data;e.reCalRequiredParameters(r.parameters),e.meta=r}))},t.prototype.reCalRequiredParameters=function(e){var t,r=this;this.svc.requiredParameters||(this.svc.requiredParameters=[]),this.svc.requiredParameters=this.svc.requiredParameters.filter((function(t){var r=t.parameterName;return e.includes(r)}));var a=e.filter((function(e){return r.svc.requiredParameters.findIndex((function(t){return t.parameterName===e}))<0})).map((function(e){return{parameterName:e,description:""}}));(t=this.svc.requiredParameters).push.apply(t,function(e,t){for(var r=0,a=t.length,l=e.length;r<a;r++,l++)e[l]=t[r];return e}([],function(e,t){var r="function"==typeof Symbol&&e[Symbol.iterator];if(!r)return e;var a,l,o=r.call(e),n=[];try{for(;(void 0===t||t-- >0)&&!(a=o.next()).done;)n.push(a.value)}catch(e){l={error:e}}finally{try{a&&!a.done&&(r=o.return)&&r.call(o)}finally{if(l)throw l.error}}return n}(a)))},t.prototype.goback=function(){this.$router.go(-1)},u([(0,o.fI)({default:"new"})],t.prototype,"id",void 0),u([p.Action("listLabels")],t.prototype,"listLabels",void 0),u([o.wA],t)}(o.w3),d=(0,r(51900).Z)(v,a,[],!1,null,null,null);d.options.__file="src/modules/ds/views/Svc.vue";var m=d.exports}}]);