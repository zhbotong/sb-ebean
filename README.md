# sb-ebean
springboot-ebean 脚手架


 Getting Started

jdk:zulu-jdk 12

why zulu : 下载速度快

## 类文件介绍
Controlle 中 方法该返回什么就还是返回什么 GlobalResponseAdvice和GlobalException做了自动处理
如果有自定义需求,返回ResponseResult也是ok的

aop-GlobalException:全局异常处理

aop-GlobalResponseAdvice:全局响应处理

aop-WebAcpect:请求、响应日志记录

config-EbeanFactoryBean:Ebean注入

config-WebConfig:拦截器、跨域、请求响应参数格式化等等

interceptor-AuthHandlerInterceptor: 认证拦截器

domain-param-RequestPage 分页参数

domain-ResponseResult 统一返回结果

utils-json vertx-core json utils


## 配置文件
ebean.mf:ebean 包扫描

ebean.properties 数据库配置文件


## 项目运行

IDEA中：
    安装插件 Lombok、Ebean11
    选项栏 Build->Ebean xxx Enhancement 选中
    File-> setting -> Build,Execution,Deployment -> Complier ->Annotation Processors -> Enable annotation processing


打包:
    ./gradlew clean build

## 热部署
    springboot 的 devtool 支持热部署,不过需要文件先编译成class文件
    所以开启idea的自动编译就好
    File-Settings-Compiler勾选 Build Project automatically
    快捷键 ctrl + shift + alt + / 选择Registry,勾上 Compiler autoMake allow when app running
