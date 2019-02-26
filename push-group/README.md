# Push Group

提供群组推送相关api

## 基本功能
* 加入群组功能
* 退出群组功能
* 消息群发功能
* 私聊消息功能

## 协议说明

群组消息依赖与推送基本的消息指令,只是在body消息体设计相关的协议既可,同时提供http接口与二进制协议

### 消息体

* 信令名称
CONTACT 属于交互信令

* 请求消息体

```json
{
    "from":"source_token",
    "to":"des_token",
    "group":"comsince",
    "type":0,  //单聊消息
    "messageType":0,
    "message":"消息内容，可以自定义，方便用扩展图片，视频类消息"
}
```

* 响应消息体

```json
{
    "from":"source_token",
    "group":"comsince",
    "type":0,
    "message":"消息内容"
}
```

## 业务说明


## 部署指南

* 使用`mvn clean package -Dmaven.test.skip=true`打包springboot工程，结果时一个完整的jar包，可以使用java -jar push-api.jar启动
* 工程结构

```json
/opt/web/push-group
├── jvm.ini
├── push-group //可执行启动脚本
└── log
   └── push-group.log //存放日志
└── lib
   └── push-group-1.0-SNAPSHOT.jar //可运行的jar
   
```

* 启动服务
```bash
# 启动服务
./push-api start
# 停止服务
./push-api stop
```