drop database if exists imm;
create database imm character set utf8;

-- ----------------
-- 用户表
-- ----------------
drop table if exists imm.user;
create table imm.user  (
                           uid varchar(30) character set utf8 not null comment 'uid,用户账号,主键',
                           username varchar(32) character set utf8 not null default '' comment '用户名(nick_name)',
                           password varchar(50) character set utf8 not null default '' comment '密码(MD5(密码+盐))',
                           salt varchar(16) character set utf8 not null default '' comment '盐',
                           real_name varchar(30) character set utf8 not null default '' comment '用户真名',
                           phone varchar(20) character set utf8 not null default '' comment '电话号码(唯一)',
                           email varchar(50) character set utf8 not null default '' comment '邮件地址(唯一)',
                           sex tinyint(4) not null default 1 comment '性别(1.男 2.女)',
                           status tinyint(4) not null default 1 null comment '账户状态(1.正常 2.锁定 3.删除 4.非法)',
                           create_time int(11) not null default 0 comment '创建时间',
                           update_time int(11) not null  default 0 comment '更新时间',
                           create_where tinyint(4) not null default 2 comment '创建来源(1.web 2.android 3.ios 4.win 5.macos 6.ubuntu)',
                           primary key (uid),
                           unique index phone(phone),
                           unique index email(email)
) engine = InnoDB character set = utf8 comment '用户信息表' ROW_FORMAT = Compact;

drop table if exists imm.account_log;
create table imm.account_log  (
                                  id int(11)  not null auto_increment comment '用户账户操作日志主键',
                                  log_name varchar(255) character set utf8 default null comment '日志名称(login,register,logout)',
                                  uid varchar(30) character set utf8 default null comment '用户id',
                                  create_time int(11) null default 0 comment '创建时间',
                                  succeed tinyint(4) null default null comment '是否执行成功(0失败1成功)',
                                  message varchar(255) character set utf8 default null comment '具体消息',
                                  ip varchar(255) character set utf8 default null comment '登录ip',
                                  PRIMARY KEY (ID)
) ENGINE = InnoDB
  auto_increment = 100
  character set = utf8
    comment = '登录注册登出记录';

drop table if exists imm.operation_log;
create table imm.operation_log  (
                                    id int(11) not null auto_increment comment '用户操作日志主键',
                                    log_name varchar(255) character set utf8 default null comment '日志名称',
                                    uid varchar(30) character set utf8 default null comment '用户id',
                                    api varchar(255) character set utf8 default null comment 'api名称',
                                    method varchar(255) character set utf8 default null comment '方法名称',
                                    create_time int(11) default 0 comment '创建时间',
                                    succeed tinyint(4) default null comment '是否执行成功(0失败1成功)',
                                    message varchar(255) character set utf8 default null comment '具体消息备注',
                                    PRIMARY KEY (ID) USING BTREE
) engine = InnoDB
  auto_increment = 610
  character set = utf8
    comment = '操作日志';


drop table if exists imm.role;
create table imm.role  (
                           id int(11) not null auto_increment comment '角色id',
                           code varchar(30) character set utf8 not null comment '角色编码',
                           name varchar(30) character set utf8 default null comment '角色名称',
                           status smallint(4) null default 1 comment '状态   1:正常、9：禁用',
                           create_time int(11) default 0 comment '创建时间',
                           update_time int(11) default 0 comment '更新时间',
                           primary key (id) using btree
) engine = InnoDB
  auto_increment = 105
  character set = utf8
    comment = '角色表';






