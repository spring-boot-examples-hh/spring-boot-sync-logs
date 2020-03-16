## spring-boot-sync-logs

基于spring-boot 2.x 的 Logstash+ Kafka基于AOP 实时同步日志到es DEMO。

## 开发环境

JDK1.8、Maven、Intellij IDEA、

## 涉及相关技术
springboot、kafka、elasticsearch、logstash 使用

## 启动说明
1、项目前需要准备的环境：kafka、elasticsearch、logstash（此处安装部署环境省略）
2、启动kafka内置的zookeeper：进入C:\faster\software\kafka_2.12-2.4.0\bin目录 执行\windows\zookeeper-server-start.bat  .\config\zookeeper.properties
2、启动kafka:进入C:\faster\software\kafka_2.12-2.4.0\bin目录 执行：windows\kafka-server-start.bat .\config\server.properties
3、启动elasticsearch：进入C:\faster\software\elasticsearch\elasticsearch-5.6.3\bin 目录 执行：elasticsearch.bat
4、启动logstash：进入C:\faster\software\elasticsearch\logstash-5.6.3\bin 目录 执行： .\logstash -f .\logstash_log.conf
5、在resources/application.yml项目配置信息
6、运行Application main方法启动项目。
7、项目访问地址：http://localhost:8080/list


## 版本区别(spring-boot 1.x and 2.x)

作者： roylao

欢迎关注： 