# 聊天记录服务
im服务商提供了3天的消息存储服务，
为了延长聊天记录存储，使用本地数据库存储，
设计如下：

使用聊天记录服务器每隔指定时间（TIME_INTERVAL），
拉取一次im服务商存储的消息，
并存储到本地

存储方案设计如下：
当用户登录时，首先检查用户上次登录时间，
如果本次登录与上次登录相差超过TIME_INTERVAL
则从本地数据库拉取，否则从IM服务器拉取，
拉取范围从当前开始到上次登录的那一刻

服务聊天记录持久化方案，
持久化方案如下：
IM服务器->MQ->MySQL

间隔TIME_INTERVAL之后，从IM服务器拉取消息放入MQ中，
消费者从MQ消费存入MySQL，由于读写分离的存在，
会存在这种情况，假设刚刚进行了
IM记录的拉取，用户恰好登录，从读数据库读取，可能会导致
部分聊天记录读不到，所以有如下设计：
MQ存入的顺序是，时间戳越小的消息越早存入MQ，
当用户登录拉取消息时，尝试拉取多次。

MQ选取RabbitMQ


# 参考资料
http://www.52im.net/forum.php?mod=viewthread&tid=1230&highlight=%B4%E6%B4%A2