# RabbitMQ

[TOC]

## 安装

```bash
docker run -d -p 5672:5672 -p 15672:15672 --name rabbitmq rabbitmq:management
```

[访问](http://10.0.0.20:15672) 登陆, 默认用户名和密码guest, guest

新建用户

![image-20191122122931967](images/image-20191122122931967.png)

新建用户在用户列表发现没有访问virtual host的权限,在rabbitmq中 Virtual Hosts相当于数据库, 访问全系需要单独设置

![image-20191122123133692](images/image-20191122123133692.png)

这里点击右侧Virtual Hosts, 找到`/`, 点击进入设置权限

![image-20191122123431578](images/image-20191122123431578.png)

![image-20191122123536114](images/image-20191122123536114.png)

## 简单消息队列

![](https://www.rabbitmq.com/img/tutorials/python-one.png)

P: 生产者, C: 消费者

```java
//首先创建一个连接工厂
public final class ConnectionUtils {
    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("10.0.0.20");
        factory.setPort(5672);
        factory.setVirtualHost("/hello");
        factory.setUsername("wanli");
        factory.setPassword("123456");
        return factory.newConnection();
    }
}

public class SimpleSender {
    private static final Logger logger = LoggerFactory.getLogger(SimpleSender.class);
    static final String QUEUE_NAME = "queue_1";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建一个通道
        try (Connection connection = ConnectionUtils.getConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String msg = "hello simple";
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            logger.info("send rabbit message:[{}]", msg);
        }
    }
}

public class SimpleReceiver {
    private static final Logger logger = LoggerFactory.getLogger(SimpleReceiver.class);
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //监听队列
        channel.basicConsume(QUEUE_NAME, true, (consumerTag, message) -> {
            logger.info("Received message:[{}]", new String(message.getBody(), StandardCharsets.UTF_8));
        }, consumerTag -> {
        });
        // 这里不暂停可能还没开始收消息就退出程序了
        Thread.sleep(1000);
        channel.close();
        connection.close();
    }
}
```

耦合性高, 生产者和消费者一对一使用 work queue解决

## Work Queue

![](https://www.rabbitmq.com/img/tutorials/python-two.png)

work queue和简单消息队列一样,  只是多一个消费者实例,

### 轮训分发

不足:  处理慢的实例并不会少消费消息s

```java
public class WorkerSender {
    static final String WORKER_QUEUE = "worker_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(WORKER_QUEUE, false, false, false, null);
        for (int i = 0; i < 50; i++) {
            final String s = "worker: " + i;
            channel.basicPublish("", WORKER_QUEUE, null, s.getBytes(StandardCharsets.UTF_8));
            Thread.sleep(100);
        }
        channel.close();
        connection.close();
    }
}

public class WorkerReceiver1 {

    private static final Logger logger = LoggerFactory.getLogger(WorkerReceiver1.class);

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        final Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(WORKER_QUEUE, false, false, false, null);
        channel.basicConsume(WORKER_QUEUE, true, (consumerTag, message) -> {
            logger.info("worker received 1 message [{}]", new String(message.getBody(), StandardCharsets.UTF_8));
        }, consumerTag -> {
        });

        Thread.sleep(10000000);
        channel.close();
        connection.close();
    }
}


public class WorkerReceiver2 {

    private static final Logger logger = LoggerFactory.getLogger(WorkerReceiver2.class);

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        final Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(WORKER_QUEUE, false, false, false, null);
        channel.basicConsume(WORKER_QUEUE, true, (consumerTag, message) -> {
            logger.info("worker received 2 message [{}]", new String(message.getBody(), StandardCharsets.UTF_8));
        }, consumerTag -> {
        });

        Thread.sleep(10000000);
        channel.close();
        connection.close();
    }
}
```

### 公平分发

按劳分配

```java
public class WorkerSender {
    static final String WORKER_QUEUE = "worker_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(WORKER_QUEUE, false, false, false, null);
        //每个消费者发送确认消息之前, 消息队列不发送下一个消息到消息队列, 一次只处理一个消息
        channel.basicQos(1);
        for (int i = 0; i < 500; i++) {
            final String s = "worker: " + i;
            channel.basicPublish("", WORKER_QUEUE, null, s.getBytes(StandardCharsets.UTF_8));
            Thread.sleep(100);
        }
        channel.close();
        connection.close();
    }
}

public class WorkerReceiver1 {

    private static final Logger logger = LoggerFactory.getLogger(WorkerReceiver1.class);

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        final Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(WORKER_QUEUE, false, false, false, null);
        channel.basicQos(1);
        channel.basicConsume(WORKER_QUEUE, false, (consumerTag, message) -> {
            logger.info("worker received 1 message [{}]", new String(message.getBody(), StandardCharsets.UTF_8));
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, consumerTag -> {
        });
        Thread.sleep(10000000);
        channel.close();
        connection.close();
    }
}

public class WorkerReceiver2 {

    private static final Logger logger = LoggerFactory.getLogger(WorkerReceiver2.class);

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        final Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.basicQos(1);
        channel.queueDeclare(WORKER_QUEUE, false, false, false, null);
        channel.basicConsume(WORKER_QUEUE, false, (consumerTag, message) -> {
            logger.info("worker received 2 message [{}]", new String(message.getBody(), StandardCharsets.UTF_8));
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        }, consumerTag -> {
        });
        Thread.sleep(10000000);
        channel.close();
        connection.close();
    }
}
```

### 消息应答和消息持久化

1. 消息应答

   1. 

   ```java
   boolean autoAck = true;
   channel.basicConsume(QUEUE_NAME, autoAck, consumer);
   ```

   `boolean autoAck = true` 自动确认模式, 一旦rabbitmq将消息分发给消费者, 就会从内存中删除, 如果在这个时候杀死消费者, 那么将会丢失正在处理的消息

   `boolean autoAck = false` 手动模式, 如果有一个消费者就收交付给其他消费者. 处理完成后发送确认消息

2. 消息持久化

   将程序中`boolean durable`从false改成true是不可以的, rabbitmq不允许重新定义一个已存在的队列, 新建的队列可以

## 订阅模式

![](https://www.rabbitmq.com/img/tutorials/python-three.png)

X: 交换机

1. 一个生产者, 多个消费者
2. 每个消费者都有自己的队列
3. 生产者没有直接把消息发送到队列, 而是发送到交换机 exchange
4. 每个队列都要绑定到交换机
5. 生产者发送的消息, 经过交换机发到队列, 实现一个消息被多个消费者消费

rabbitmq只有队列有存储能力, 交换机没有

```java
// 消息发送者
public class PsSender {
    private static final Logger logger = LoggerFactory.getLogger(PsSender.class);
    /**
     * 定义交换机
     */
    static final String EXCHANGE_NAME = "demo_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        final String msg = "send public subscribe message";
        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes(StandardCharsets.UTF_8));
        logger.info("public/subscribe send message:[{}]", msg);

        channel.close();
        connection.close();
    }
}

// 消息接受者, 这里的接受者1, 接受者2和1的区别就只有PS_QUEUE_NAME不一样
public class PsReceiver1 {

    private static final Logger logger = LoggerFactory.getLogger(PsReceiver1.class);
    private static final String PS_QUEUE_NAME = "publish_subscribe_queue_name1";

    public static void main(String[] args) throws IOException, TimeoutException {

        final Connection connection = ConnectionUtils.getConnection();

        final Channel channel = connection.createChannel();
        //队列申明
        channel.queueDeclare(PS_QUEUE_NAME, false, false, false, null);
        //绑定队列到交换机
        channel.queueBind(PS_QUEUE_NAME, EXCHANGE_NAME, "");
        channel.basicQos(1);
        
        channel.basicConsume(PS_QUEUE_NAME, false, (consumerTag, message) -> {
            logger.info("ps receiver1 message:[{}]", new String(message.getBody(), StandardCharsets.UTF_8));
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        }, consumerTag -> {
        });
    }
}
```

先运行连个接受者,再运行发送者. 会看到连个接受者都能接收到一样的消息

