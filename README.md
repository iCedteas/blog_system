# 个人博客系统

> 2022.06.14  

---

## 项目设计

### 系统开发及运行环境

- 操作系统：Ubuntu 18.04.6
- Java开发包：JDK 8
- 项目管理工具：Maven 3.5.4
- 项目开发工具：idea
- 数据库：MySQL
- 缓存管理工具：Redis 4.0.1
- 浏览器：谷歌浏览器

### 文件组织结构

> **后端业务文件**：后端业务采用的是三层架构思想，及项目划分为控制层Controller,业务逻辑层Service,数据库访问层Dao进行开发，同时，项目还封装了一些自定义配置类,工具类．

> **前端页面文件**：统一集中在项目的 classpath 类目录下．本项目前端页面使用的是Thymeleaf模板引擎．在resources资源目录下的static和templates文件夹下分别存放了静态资源文件和Thymeleaf模板文件．

> **配置文件**：存放在resource目录．mapper文件夹编写了MyBaits对应的XML映射文件，在i18n文件夹下编写了国际化资源文件．

| com.rbecy |                           |                          |
|-----------|---------------------------|--------------------------|
|           | config - 项目配置类            |                          |
|           | dao - dao层接口              |                          |
|           | model                     |                          |
|           |                           | domain - 实体映射类           |
|           |                           | ResponseData - 响应封装类     |
|           | service - service业务接口及实现类 |                          |
|           | utils - 项目工具类             |                          |
|           | web                       |                          |
|           |                           | admin - 后台控制controller类  |
|           |                           | client - 前台控制controller类 |
|           |                           | interceptor - 自定义拦截器类    |
|           |                           | scheduletask - 定时任务类     |
|           | BlogSystemApplication     |                          |

### 数据库设计

#### 文章详情表 t_article

| 字段名           | 类型       | 长度  | 是否为主键 | 说明           |
|---------------|----------|-----|-------|--------------|
| id            | int      | 11  | 是     | 文章id         |
| title         | varchar  | 50  | 否     | 文章标题         |
| content       | longtext |     | 否     | 文章内容         |
| created       | date     |     | 否     | 创建时间         |
| modified      | date     |     | 否     | 修改时间         |
| categories    | varchar  | 200 | 否     | 文章分类         |
| tags          | varchar  | 200 | 否     | 文章标签         |
| allow_comment | tinyint  | 1   | 否     | 是否允许评论（默认为１） |
| thumbnail     | varchar  | 200 | 否     | 文章缩略图        |

#### 文章评论表 t_comment

| 字段名           | 类型      | 长度  | 是否为主键 | 说明               |
|---------------|---------|-----|-------|------------------|
| id            | int     | 11  | 是     | 评论id             |
| article_id    | int     | 11  | 否     | 评论关联的文章id        |
| created       | date    |     | 否     | 创建时间             |
| ip            | varchar | 200 | 否     | 评论用户所在ip         |
| content       | text    |     | 否     | 评论内容             |
| status        | varchar | 200 | 否     | 评论状态（默认approved） |
| author        | varchar | 200 | 否     | 评论作者名            |

#### 文章统计表 t_statistic

| 字段名          | 类型  | 长度  | 是否为主键 | 说明     |
|--------------|-----|-----|-------|--------|
| id           | int | 11  | 是     | 文章统计id |
| article_id   | int | 11  | 否     | 文章id   |
| hits         | int | 11  | 否     | 文章点击量  |
| comments_num | int | 11  | 否     | 文章评论量  |

#### 用户信息表 t_user

| 字段名      | 类型      | 长度  | 是否为主键 | 说明           |
|----------|---------|-----|-------|--------------|
| id       | int     | 11  | 是     | 用户id         |
| username | varchar | 200 | 否     | 用户名          |
| password | varchar | 200 | 否     | 用户密码（加密后的密码） |
| email    | varchar | 200 | 否     | 用户邮箱         |
| created  | date    |     | 否     | 创建时间         |
| valid    | tinyint | 1   | 否     | 是否为有效用户（默认１） |

#### 用权限表 t_authority

| 字段名       | 类型      | 长度  | 是否为主键 | 说明         |
|-----------|---------|-----|-------|------------|
| id        | int     | 11  | 是     | 用户id       |
| authority | varchar | 200 | 否     | 权限以ROLE_开头 |

#### 用户权限关联表 t_user_authority

| 字段名          | 类型  | 长度  | 是否为主键 | 说明       |
|--------------|-----|-----|-------|----------|
| id           | int | 11  | 是     | 关联主表主键id |
| article_id   | int | 11  | 否     | 文章id     |
| authority_id | int | 11  | 否     | 权限id     |

```mysql
create database blog_system;

use blog_system;

create table t_article
(
    id            int(11) primary key AUTO_INCREMENT,
    title         varchar(50),
    content       longtext,
    created       date,
    modified      date,
    categories    varchar(200),
    tags          varchar(200),
    allow_comment tinyint(1) default 1,
    thumbnail     varchar(200)
);

create table t_comment
(
    id         int(11) primary key AUTO_INCREMENT,
    article_id int(11),
    created    date,
    ip         varchar(200),
    content    text,
    status     varchar(200) default 'approved',
    author     varchar(200)
);

create table t_statistic
(
    id           int(11) primary key,
    article_id   int(11),
    hits         int(11),
    comments_num int(11)
);

create table t_user
(
    id       int(11) primary key,
    username varchar(200),
    password varchar(200),
    email    varchar(200),
    created  date,
    valid    tinyint(1) default 1
);

create table t_authority
(
    id        int(11) primary key,
    authority varchar(200)
);

create table t_user_authority
(
    id           int(11) primary key,
    article_id   int(11),
    authority_id int(11)
);

show tables;
```

