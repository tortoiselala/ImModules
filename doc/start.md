权限目前分为两类

- 匿名
- 认证用户

对这两种用户分别发放不同的token

需要的api如下：
getPublicKey（获取RSA公钥，客户端用于加密传输账户密码）

token?grant_type(获取授权token)
 grant_type=password, refresh_token

logout

getRoles

规定权限授予单位是接口

认证过程

1. 客户端携带账户信息请求授权服务器的令牌
2. 授权服务器鉴定信息并发放令牌
3. 客户端携带令牌向资源服务器请求资源
4. 资源服务器收到请求并鉴定令牌可用性
5. 客户端令牌可能过期

关于用户角色的定义，用户角色包含一个可访问API集合，
角色拥有名称和API集合两个属性


