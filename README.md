# DDD 学习项目Smart RM

## 代码架构

代码包结构使用洋葱架构

适配器层 -> 应用层 -> 领域层 -> 基础架构层

* 适配器层：隔离其他领域、外部系统和基础架构具体实现
* 应用层：整合外部与领域的业务
* 领域层：实现领域内部逻辑，对外提供统一接口
* 基础架构层：对接基础设施，MQ，数据库，缓存等组件