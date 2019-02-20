# Push API

提供推送的http接口

## 部署指南

* 使用`mvn clean package -Dmaven.test.skip`打包springboot工程，结果时一个完整的jar包，可以使用java -jar push-api.jar启动
* 工程结构

```json
/opt/web/push-api
├── jvm.ini
├── push-api //可执行启动脚本
└── log
   └── push-api.log //存放日志
└── lib
   └── push-api-1.0-SNAPSHOT.jar //可运行的jar
   
```

* 启动服务
```bash
# 启动服务
./push-api start
# 停止服务
./push-api stop
```