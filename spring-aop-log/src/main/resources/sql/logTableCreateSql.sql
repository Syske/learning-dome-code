-- orcale 脚本

create table UPMS_LOG
(
  log_id      VARCHAR2(32) not null,
  description VARCHAR2(100),
  username    VARCHAR2(100),
  start_time  NUMBER(20),
  spend_time  NUMBER(11),
  base_path   VARCHAR2(500),
  uri         VARCHAR2(500),
  url         VARCHAR2(500),
  method      VARCHAR2(10),
  parameter   CLOB,
  user_agent  VARCHAR2(500),
  ip          VARCHAR2(30),
  result_     CLOB,
  permissions VARCHAR2(100),
  createtime  VARCHAR2(19),
  user_id     VARCHAR2(36),
  appid       VARCHAR2(32)
)
tablespace TBS_RHPT
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table
comment on table UPMS_LOG
  is '操作日志';
-- Add comments to the columns
comment on column UPMS_LOG.log_id
  is '编号';
comment on column UPMS_LOG.description
  is '操作描述';
comment on column UPMS_LOG.username
  is '操作用户';
comment on column UPMS_LOG.start_time
  is '操作时间';
comment on column UPMS_LOG.spend_time
  is '消耗时间';
comment on column UPMS_LOG.base_path
  is '根路径';
comment on column UPMS_LOG.uri
  is 'URI';
comment on column UPMS_LOG.url
  is 'URL';
comment on column UPMS_LOG.method
  is '请求类型';
comment on column UPMS_LOG.parameter
  is '入参参数';
comment on column UPMS_LOG.user_agent
  is '用户标识';
comment on column UPMS_LOG.ip
  is 'IP地址';
comment on column UPMS_LOG.result_
  is '返回结果';
comment on column UPMS_LOG.permissions
  is '权限值';
comment on column UPMS_LOG.createtime
  is '生成时间';
comment on column UPMS_LOG.user_id
  is '用户id';
comment on column UPMS_LOG.appid
  is '应用标志';
-- Create/Recreate primary, unique and foreign key constraints
alter table UPMS_LOG
  add constraint PK_UPMS_LOG primary key (LOG_ID)
  using index
  tablespace TBS_RHPT
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );


-- mysql

CREATE TABLE `upms_log` (
  `log_id` varchar(32) NOT NULL COMMENT '编号',
  `description` varchar(100) DEFAULT NULL COMMENT '操作描述',
  `username` varchar(100) DEFAULT NULL COMMENT '操作用户',
  `start_time` bigint(20) DEFAULT NULL COMMENT '操作时间',
  `spend_time` int(11) DEFAULT NULL COMMENT '消耗时间',
  `base_path` varchar(500) DEFAULT NULL COMMENT '根路径',
  `uri` varchar(500) DEFAULT NULL COMMENT 'URI',
  `url` varchar(500) DEFAULT NULL COMMENT 'URL',
  `method` varchar(10) DEFAULT NULL COMMENT '请求类型',
  `parameter` text COMMENT '入参参数',
  `user_agent` varchar(500) DEFAULT NULL COMMENT '用户标识',
  `ip` varchar(30) DEFAULT NULL COMMENT 'IP地址',
  `result_` text COMMENT '返回结果',
  `permissions` varchar(100) DEFAULT NULL COMMENT '权限值',
  `createtime` varchar(19) DEFAULT NULL COMMENT '生成时间',
  `user_id` varchar(36) DEFAULT NULL COMMENT '用户id',
  `appid` varchar(32) DEFAULT NULL COMMENT '应用标志',
  PRIMARY KEY (`log_id`),
  KEY `log_id` (`log_id`,`description`,`createtime`,`appid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志';