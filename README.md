# mysql-mock

用 Java 来模拟 MySQL 服务; 可用于单元测试环境。

> 造轮子, 并学习实现原理。

## 替代项

- TestContainers(推荐) : [https://testcontainers.com/](https://testcontainers.com/)
- Embedded MySql : [wix-embedded-mysql](https://github.com/wix-incubator/wix-embedded-mysql)
- h2 database: [h2database](https://github.com/h2database/h2database)

## 规划

后续考虑分成3个模块:

- driver 模块, 充当轻量级 JdbcDriver 层
- network-server 模块, Socket网络服务与端口监听
- engine 模块, 核心引擎, 处理;

其他模块, 比如存储, 暂时不考虑;






## 背景信息

类似于 spring-boot-starter-test 中嵌入的 RedisServer

> https://www.baeldung.com/spring-embedded-redis

