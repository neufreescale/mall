# streamsets

```shell
ulimit -n 32768


CREATE USER 'diwayou'@'%' IDENTIFIED BY '123456';
GRANT ALL on diwayou.* to 'diwayou'@'%';
GRANT SELECT, REPLICATION CLIENT, REPLICATION SLAVE on *.* to 'diwayou'@'%';
FLUSH PRIVILEGES;
```