# sharding-jdbc-sample
实测基于sharding-jdbc实现数据分片&amp;读写分离



## 一、基础设施搭建

1、自定义配置文件

1.1 mysql-master.cnf

```
[client]
default-character-set=utf8mb4
[mysql]
default-character-set=utf8mb4
[mysqld]
character-set-server=utf8mb4
sql_mode=STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION

log-bin=mysql-bin
server-id=1
binlog-do-db=ds0
expire_logs_days=7
```

1.2 mysql-slave.cnf

```
[client]
default-character-set=utf8mb4
[mysql]
default-character-set=utf8mb4
[mysqld]
character-set-server=utf8mb4
sql_mode=STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION

server-id=2
```



2、拉取镜像

```shell
docker pull mysql:5.6.43
```



3、启动容器

```shell
docker run -d \
-p 3306:3306 \
-e MYSQL_ROOT_PASSWORD=songshu \
-v {自定义配置文件路径}/mysql-master.cnf:/etc/mysql/conf.d/mysql.cnf \
--name mysql-master \
--restart=always \
mysql:5.6.43
```

```shell
docker run -d \
-p 3307:3306 \
-e MYSQL_ROOT_PASSWORD=songshu \
-v {自定义配置文件路径}/mysql-slave.cnf:/etc/mysql/conf.d/mysql.cnf \
--name mysql-slave \
--restart=always \
mysql:5.6.43
```



## 二、配置主从同步

1、在主库创建用于主从同步的账户

```sql
GRANT REPLICATION SLAVE ON *.* TO 'sync'@'%' IDENTIFIED BY 'songshu';
FLUSH PRIVILEGES;
```



2、查看主库状态

```sql
mysql> SHOW MASTER STATUS;
+------------------+----------+--------------+------------------+-------------------+
| File             | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set |
+------------------+----------+--------------+------------------+-------------------+
| mysql-bin.000002 |  1303827 | ds0          |                  |                   |
+------------------+----------+--------------+------------------+-------------------+
1 row in set (0.00 sec)
```



3、在从库执行同步命令

```sql
CHANGE MASTER TO master_host = '宿主机IP地址',
master_user = 'sync',
master_password = 'songshu',
master_log_file = '步骤2中的File值',
master_log_pos = 步骤2中的Position值;
START SLAVE;
```



4、查看从库状态

```shell
mysql> SHOW SLAVE STATUS\G;
*************************** 1. row ***************************
               Slave_IO_State: Waiting for master to send event
                  Master_Host: 172.16.196.1
                  Master_User: sync
                  Master_Port: 3306
                Connect_Retry: 60
              Master_Log_File: mysql-bin.000002
          Read_Master_Log_Pos: 1303827
               Relay_Log_File: mysqld-relay-bin.000350
                Relay_Log_Pos: 283
        Relay_Master_Log_File: mysql-bin.000002
             Slave_IO_Running: Yes
            Slave_SQL_Running: Yes
...
1 row in set (0.00 sec)
```

**说明**：如果`Slave_IO_Running`和`Slave_SQL_Running`的值均为`Yes`，说明主从同步正常，否则说明同步失败。



## 三、运行测试程序

1、在主库执行项目目录下的脚本文件：`./sql/ds0.sql`



2、sharding-jdbc配置文件：`sharding-jdbc.properties`

[各配置项详细说明请参照官方文档（中文）](https://shardingsphere.apache.org/document/current/cn/manual/sharding-jdbc/configuration/config-spring-boot/)



3、程序入口：`ShardingJdbcApplication`

