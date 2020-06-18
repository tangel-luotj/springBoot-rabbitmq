# springBoot-Rabbitmq
springBoot整合rabbitmq
#项目结构介绍
* config:配置文件
* consumer:消费者
* ui:放置接口暴露请求类
* provider:提供者
* utils:工具包（注:ConnectionUtil为获取rmq连接的工具包）

##初衷:结合练习提升自己、提供给rabbitmq初学者学习

项目前期准备
* 安装rabbitmq，并能成功访问管控台（作者采用docker的方式启动rmq,可参考命令: docker run -d --name my-rabbit -p 5672:5672 -p 15672:15672）
* 管控台访问地址:http://localhost:15672  账号:guest 密码:guest
* 测试项目获取连接是否成功

### 1、简单队列
* consumer和provider路径下的simple目录下放置的分别是对应的消费者类和提供者类
* SimpleMsgProvider为消息提供者
* SimpleMsgConsumer为消息消费者，获取提供者提供未进行消费的消息

```
    测试用例1(消费者类、提供者类未启动的条件下实施)
        * 启动消费者(SimpleMsgConsumer),确保启动并创建队列成功，可查看rmq管控台Queues下是否产生"testQueue"队列
        * 队列生成，启动提供者(SimpleMsgProvider),启动成功，查看消费者类控制台，本实例输出消费消息"hello , this is my first msg!!"

    测试用例2(消费者类、提供者类未启动的条件下实施)
        * 查看rmq管控台Queues下是否产生"testQueue"队列
        * 队列生成，启动提供者(SimpleMsgProvider),启动成功
        * 查看rmq管控台Queues下的"testQueue"队列，可查看到产生了一条未被消费的消息
        * 启动消费者(SimpleMsgConsumer)，查看消费者累控制台，本实例输出消费消息"hello , this is my first msg!!"
```

### 2、