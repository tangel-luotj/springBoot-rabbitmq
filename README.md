# springBoot-Rabbitmq
springBoot整合rabbitmq
#项目结构介绍
* config:配置文件
* consumer:消费者
* ui:放置接口暴露请求类
* provider:生产者
* utils:工具包（注:ConnectionUtil为获取rmq连接的工具包）

##初衷:结合练习提升自己、提供给rabbitmq初学者学习

项目前期准备
* 安装rabbitmq，启动管控台（执行命令rabbitmq-plugins enable rabbitmq_management）
* 能成功访问管控台（作者采用docker的方式启动rmq,可参考命令: docker run -d --name my-rabbit -p 5672:5672 -p 15672:15672）
* 管控台访问地址:http://localhost:15672  账号:guest 密码:guest
* 测试项目获取连接是否成功

### 1、简单队列
* 描述:消费者通过声明的队列获取生产者通过该队列发送的消息，若消费者一直处于启动状态，生产者发送的消息会直接被消费，若消费者未启动，生成者发送的消息将等待消费者的启动而被消费
* consumer和provider路径下的simple目录下放置的分别是对应的消费者类和生产者类
* SimpleMsgProvider为消息生产者
* SimpleMsgConsumer为消息消费者，获取提供者提供未进行消费的消息
- 隐患:消息可能没有被消费者正确处理,已经从队列中消失了,造成消息的丢失

```
    测试用例1(消费者类、生产者类未启动的条件下实施)
        * 启动消费者(SimpleMsgConsumer),确保启动并创建队列成功，可查看rmq管控台Queues下是否产生"testQueue"队列
        * 队列生成，启动生产者(SimpleMsgProvider),启动成功，查看消费者类控制台，本实例输出消费消息"hello , this is my first msg!!"

    测试用例2(消费者类、生产者类未启动的条件下实施)
        * 查看rmq管控台Queues下是否产生"testQueue"队列
        * 队列生成，启动生产者(SimpleMsgProvider),启动成功
        * 查看rmq管控台Queues下的"testQueue"队列，可查看到产生了一条未被消费的消息
        * 启动消费者(SimpleMsgConsumer)，查看消费者类控制台，本实例输出消费消息"hello , this is my first msg!!"
```

### 2、Work模式-工作队列
* 描述:多个消费者的情况下，生产者发送一条消息会被其中一个空闲的消费者获取被消费(倘若所有的消费者都空闲的情况下，需要考虑消息的轮询/公平分发)
* cousumer包下放置了两个消费类(WorkMsgConsumer1&&WorkMsgConsumer2),provider包下放置有一个生产类(WorkMsgProvider1)
* 隐患:高并发情况下,默认会产生某一个消息被多个消费者共同使用
* 优点:不需要知道分配强度的情况下，一堆生成消息的消息将给到消费者进行争抢

```
    测试用例(消费者类启动、生产者类未启动的条件下实施)
        * 启动消费者(WorkMsgConsumer1&&WorkMsgConsumer2),确保启动并创建队列成功，可查看rmq管控台Queues下是否产生"workQueue1"队列
        * 队列生成，启动生产者(WorkMsgProvider1),启动成功，分别查看两个消费者类控制台
        * 可以查看到，消费者休眠时间少的消费者启动类，消费的消息占了半壁江山,可以印证，空闲的消费者将会抢夺更多的消息
```