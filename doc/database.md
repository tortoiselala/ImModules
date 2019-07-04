# 数据库开发
数据库采用主从数据库的方式应对读多写少的场景


采用mysql作为数据存储方式，存储表如下
t_user(用户表)


| 字段名       | 字段描述 | 数据类型 | 是否主键 | 可空 |
| --------------- | ------------ | ---------- | -------- | ---- |
| id              | ID           | bigint 20  | 1        |      |
| username        | 用户名    | varchar 50 |          |      |
| password        | 密码       | varchar 70 |          |      |
| last_login_time | 上次登录时间 | datetime   |          |      |
| create_time     | 创建时间 | datetime   |          |      |
| cancelled       | 是否注销 | tiny 4     |          |      |

t_group(群组表)
用来存储群组的详细信息，群组表跟用户表示多对多映射，因此需要中间表存储

| 字段名   | 字段描述 | 数据类型 | 是否主键 | 可空 |
| ----------- | -------- | ---------- | -------- | ---- |
| id          | ID       | bigint 20  | 1        |      |
| name        | 群名称 | varchar 50 |          |      |
| type        | 群组类型 | tiny 4     |          |      |
| creator     | 群创建人 | bigint 20  |          |      |
| owner       | 群主   | bigint 20  |          |      |
| create_time | 创建时间 | datetime   |          |      |
| delete_time | 删除时间 | datetime   |          |      |
| deleted     | 是否删除 | tinyint 4  |          |      |

t_group_member(群组成员表)

| 字段名  | 字段描述 | 数据类型 | 是否主键 | 可空 |
| ---------- | -------- | ---------- | -------- | ---- |
| id         | ID       | bigint 20  | 1        |      |
| group_id   | 群组id | bigint 20  | 1        |      |
| name       | 群昵称 | varchar 50 |          |      |
| enter_time | 进群时间 | datetime   |          |      |

t_chatlogs(聊天记录表)

| 字段名 | 字段描述 | 数据类型 | 是否主键 | 可空 |
| --------- | -------- | --------- | -------- | ---- |
| id        | ID       | bigint 20 | 1        |      |
| group_id  | 所属群ID | bigint 20 |          |      |
| chat_type | 聊天类型 | tinyint 4 |          |      |
| sender    | 发送者 | bigint 20 |          |      |
| receiver  | 接受者 | bigint 20 |          |      |
| type      | 信息类型 | tinyint 4 |          |      |
| content   | 内容   | text      |          |      |
| time      | 时间   | int       |          |      |

t_message(动态及通知表)

| 字段名   | 字段描述 | 数据类型 | 是否主键 | 可空 |
| ----------- | -------- | ----------- | -------- | ---- |
| id          | ID       | bigint 20   | 1        |      |
| type        | 类型   | tinyint 4   |          |      |
| title       | title    | varchar 100 |          |      |
| content     | 内容   | text        |          |      |
| create_time | 创建时间 | int         |          |      |
| delete_time | 删除时间 | int         |          |      |
| deleted     | 是否删除 | tinyint 4   |          |      |

 
 
 

