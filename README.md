# Aegiron SaaS Platform

> 🛡️ **Aegiron** —— 一个面向企业级、多租户场景的 SaaS 技术底座示例项目

Aegiron SaaS Platform 是一个以 **Spring Cloud 微服务体系** 为核心、围绕 **多租户（Multi-Tenant）架构设计** 展开的后端技术项目。该项目强调：

* 可演进的企业级架构
* 清晰的请求链路与边界
* 可作为技术作品 / 面试项目长期维护

---

## 一、项目目标

### 1️⃣ 技术目标

* 构建一套 **可运行、可扩展、可演示** 的多租户 SaaS 后端架构
* 覆盖主流企业级技术栈：

    * Spring Cloud Alibaba
    * Spring Security
    * Gateway
    * MyBatis-Plus
    * Nacos
    * JWT

### 2️⃣ 架构目标

* 网关统一入口
* 认证、授权、业务服务解耦
* 多租户数据隔离（行级）
* 请求链路可追踪

---

## 二、整体架构
Aegiron SaaS Platform 采用 网关 + 认证中心 + 业务服务 的经典微服务架构，并将 多租户、认证、安全、链路追踪 作为横切能力统一治理。

### 架构总览
```

```

---

## 三、核心设计理念

---

## 四、模块说明

### 📦 模块结构

---


## 五、里程碑规划

| 里程碑 | 目标           |
| --- | ------------ |
| M0  | 技术底座 + 多租户核心 |
| M1  | 租户注册 / 子域名   |
| M2  | 菜单 / 权限模型    |
| M3  | 审计日志 / 运维能力  |

---

## 六、分支管理规范

### 分支结构

```
main      # 稳定可演示
└── develop   # 日常开发集成
```

### 需求分支命名规范

```
<type>/<milestone>-<issue>-<desc>
```

示例：

```
feature/M0-08-jwt-login
doc/M0-17-architecture-readme
refactor/M0-18-structure-cleanup
```

---

## 七、Commit Message 规范

```
feat(M0-08): implement jwt login
fix(M0-14): fix tenant context leakage
refactor(M0-18): optimize package structure
doc(M0-17): add architecture diagram
```

---

## 八、技术栈版本

### 核心框架
* **JDK**: 17
* **Spring Boot**: 3.3.5
* **Spring Cloud**: 2023.0.3 (Leyton)
* **Spring Cloud Alibaba**: 2022.0.0.0

### 中间件与工具
* **MySQL**: 8.0.33
* **Nacos**: 2.x (服务发现与配置中心)
* **MyBatis Plus**: 3.5.7
* **Lombok**: 1.18.30
* **Hutool**: 5.8.25
* **Knife4j**: 4.4.0 (API文档)

## 九、快速开始

### 前置要求
1. JDK 17+
2. Maven 3.6+
3. MySQL 8.0+
4. Nacos 2.x

### 启动步骤

#### 1. 启动 Nacos
```bash
# 下载并启动 Nacos
# Windows: startup.cmd -m standalone
# Linux/Mac: sh startup.sh -m standalone
# 访问 http://localhost:8848/nacos (默认账号/密码: nacos/nacos)
```

#### 2. 创建数据库
```sql
-- 创建认证服务数据库
CREATE DATABASE aegiron_platform DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### 3. 修改配置文件
根据实际情况修改各模块的 `{application.name}-{profile}.yml` 中的信息：


#### 4. 启动服务
按以下顺序启动服务：
```bash

```

#### 5. 访问服务


### 服务端口说明
| 服务 | 端口 | 说明 |
|------|------|------|
|  |  |  |


---

## 十、项目定位说明

> Aegiron 并非一个完整的业务系统，而是一个 **企业级 SaaS 技术样板工程**。

它关注的是：

* 架构是否合理
* 多租户是否严谨
* 代码是否具备长期维护价值

---

## 十一、作者

* Domain: **aegiron.cn**
* 项目：Aegiron SaaS Platform
* 昵称：**panda**
* 邮箱：**<EMAIL>zhangzb1024@163.com**
* 社区：**https://github.com/admin-zhang/aegiron-saas-platform**
* 博客：**https://blog.icoders.club**



