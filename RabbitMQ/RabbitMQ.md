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

## Exchange 交换机 转发器

一方面是接收生产者的消息, 另一方面是向队列推送消息

### Fanout Exchange

 不处理路由键, 只需要简单的将队列绑定到exchange上, 一个发送到exchange的消息就会被转发到该exchange绑定的私有队列上

![20170512103945228](images/20170512103945228.png)

### Direct Exchange

会处理路由键, 也就是按需索取. 需要将一个队列绑定到exchange上, 要求该消息与一个特定的路由键**完全匹配**. 消息才会进入到该队列中

![20170512105615019](images/20170512105615019.png)

### Topic Exchange

这种模式和Direct模式原理一样, 都是根据路由键进行消息的路由, 但是这种支持路由键的模糊匹配, 此时队列需要绑定在一个模式上/ `#`匹配一个或多个词, `*`匹配一个词

![20170512111129762](images/20170512111129762.png)

## Routing(Direct Exchange)

订阅模式将消息广播发送到许多接受者, 这里我们将向其添加订阅子集的功能

![](https://www.rabbitmq.com/img/tutorials/python-four.png)

绑定是交换和队列之间的关系。可以简单地理解为：队列对来自此交换的消息感兴趣。绑定可以采取额外的`routingKey`参数.

```bash
/**
 *
 * 同时发送error 和 info两种消息
 * @author wanli
 * @date 2019-11-26 11:19
 */
public class RoutingSender {

    private static final Logger logger = LoggerFactory.getLogger(RoutingSender.class);
    static final String ROUTING_EXCHANGE_NAME = "routing_exchange_name";
    static final String ROUTING_KEY_ERROR = "error";
    static final String ROUTING_KEY_INFO = "info";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        final Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.exchangeDeclare(ROUTING_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        for (int i = 0; i < 10; i++) {
            channel.basicPublish(ROUTING_EXCHANGE_NAME, ROUTING_KEY_ERROR, null,
                    "发送error消息".getBytes(StandardCharsets.UTF_8));
            logger.info("send error msg");
            channel.basicPublish(ROUTING_EXCHANGE_NAME, ROUTING_KEY_INFO, null,
                    "发送info消息".getBytes(StandardCharsets.UTF_8));
            logger.info("send info msg");
            Thread.sleep(1000);
        }
        channel.close();
        connection.close();
    }
}

/**
 * 只接收error消息
 *
 * @author wanli
 * @date 2019-11-26 11:28
 */
public class RoutingReceiver1 {
    private static final Logger logger = LoggerFactory.getLogger(RoutingReceiver1.class);
    private static final String ROUTING_QUEUE_1 = "routing_queue_1";

    public static void main(String[] args) throws IOException, TimeoutException {
        final Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.exchangeDeclare(ROUTING_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        channel.queueDeclare(ROUTING_QUEUE_1, false, false, false, null);
        channel.basicQos(1);

        channel.queueBind(ROUTING_QUEUE_1, ROUTING_EXCHANGE_NAME, ROUTING_KEY_ERROR );
        channel.basicConsume(ROUTING_QUEUE_1, false, (consumerTag, message) -> {
            logger.info("receive1 message {}", new String(message.getBody(), StandardCharsets.UTF_8));
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        },consumerTag -> {});
    }
}

/**
 * 同时接收info和error消息
 *
 * @author wanli
 * @date 2019-11-26 11:28
 */
public class RoutingReceiver2 {
    private static final Logger logger = LoggerFactory.getLogger(RoutingReceiver2.class);
    private static final String ROUTING_QUEUE_2 = "routing_queue_2";

    public static void main(String[] args) throws IOException, TimeoutException {
        final Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.exchangeDeclare(ROUTING_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        channel.queueDeclare(ROUTING_QUEUE_2, false, false, false, null);
        channel.queueBind(ROUTING_QUEUE_2, ROUTING_EXCHANGE_NAME, ROUTING_KEY_ERROR);
        channel.queueBind(ROUTING_QUEUE_2, ROUTING_EXCHANGE_NAME, ROUTING_KEY_INFO);

        channel.basicQos(1);
        channel.basicConsume(ROUTING_QUEUE_2, false, (consumerTag, message) -> {
            logger.info("receive2 message {}", new String(message.getBody(), StandardCharsets.UTF_8));
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        }, consumerTag -> {
        });
    }
}
```

## Topics(Topic Exchange)

将路由键和某个模式匹配

![](https://www.rabbitmq.com/img/tutorials/python-five.png)

```java
public class TopicsSender {
    private static final Logger logger = LoggerFactory.getLogger(TopicsSender.class);
    static final String TOPICS_EXCHANGE_NAME = "topics_exchange_name";

    public static void main(String[] args) throws IOException, TimeoutException {
        final Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.basicQos(1);

        channel.exchangeDeclare(TOPICS_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.basicPublish(TOPICS_EXCHANGE_NAME, "wanli.haha",null, "topic wanli.haha msg".getBytes(StandardCharsets.UTF_8) );
        channel.basicPublish(TOPICS_EXCHANGE_NAME, "vhsj.haha", null, "topic vhsj.haha msg".getBytes(StandardCharsets.UTF_8));
        channel.basicPublish(TOPICS_EXCHANGE_NAME, "wanli.hehe", null, "topic wanli.hehe msg".getBytes(StandardCharsets.UTF_8));
        logger.info("Three pieces of message were sent");
        channel.close();
        connection.close();
    }
}

public class TopicReceiver1 {
    private static final Logger logger = LoggerFactory.getLogger(TopicReceiver1.class);
    private static final String TOPIC_RECEIVER_1 = "topic_1";

    public static void main(String[] args) throws IOException, TimeoutException {
        final Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();

        channel.exchangeDeclare(TOPICS_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.queueDeclare(TOPIC_RECEIVER_1, false, false, false, null);
        // 接收以wanli.路由键开头的消息
        channel.queueBind(TOPIC_RECEIVER_1, TOPICS_EXCHANGE_NAME, "wanli.#");

        channel.basicConsume(TOPIC_RECEIVER_1, false, (consumerTag, message) -> {
            logger.info("receive wanli.# msg [{}]", new String(message.getBody(), StandardCharsets.UTF_8));
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        }, consumerTag -> {
        });
    }
}

public class TopicReceiver2 {
    private static final Logger logger = LoggerFactory.getLogger(TopicReceiver2.class);
    private static final String TOPIC_RECEIVER_2 = "topic_2";

    public static void main(String[] args) throws IOException, TimeoutException {
        final Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();

        channel.exchangeDeclare(TOPICS_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.queueDeclare(TOPIC_RECEIVER_2, false, false, false, null);
        //接收以.haha结尾的路由键的消息
        channel.queueBind(TOPIC_RECEIVER_2, TOPICS_EXCHANGE_NAME, "#.haha");

        channel.basicConsume(TOPIC_RECEIVER_2, false, (consumerTag, message) -> {
            logger.info("receive #.haha msg [{}]", new String(message.getBody(), StandardCharsets.UTF_8));
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        }, consumerTag -> {
        });
    }
}
```

## RabbitMQ的消息确认机制(事务 + confirm)

在RabbitMQ中通过持久化数据解决rabbit服务器异常的数据丢失问题, 在消息发出去后客户端有没有正常消费消息是服务器不知道的, 解决办法有两种: **AMQP事务机制**, **Comfirm**

### AMQP事务机制

RabbitMQ中与事务机制有关的方法有三个:

1. txSelect(): 将channel设置成transation模式
2. txCommit(): 用于提交事务
3. txTollback(): 用于回滚事务

在通过txSelect开启事务之后，我们便可以发布消息给broker代理服务器了，如果txCommit提交成功了，则消息一定到达了broker了，如果在txCommit执行之前broker异常崩溃或者由于其他原因抛出异常，这个时候我们便可以捕获异常通过txRollback()回滚事务了。

```java
//消息生产者
public class TxSender {
    private static final Logger logger = LoggerFactory.getLogger(TxSender.class);
    static final String TX_QUEUE_NAME = "tx_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        final Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(TX_QUEUE_NAME, false, false, false, null);
        channel.txSelect();
        try {
            channel.basicPublish("", TX_QUEUE_NAME, null, "send tx message 1".getBytes(StandardCharsets.UTF_8));
            channel.basicPublish("", TX_QUEUE_NAME, null, "send tx message 2".getBytes(StandardCharsets.UTF_8));
            channel.txCommit();
            logger.info("commit");
        } catch (Exception e) {
            logger.info("rollback");
            channel.txRollback();
            e.printStackTrace();
        }
        channel.close();
        connection.close();
    }
}

//消息消费者
public class TxReceiver {
    private static final Logger logger = LoggerFactory.getLogger(TxReceiver.class);

    public static void main(String[] args) throws IOException, TimeoutException {
        final Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(TX_QUEUE_NAME, false, false, false, null);

        channel.basicConsume(TX_QUEUE_NAME, true, (consumerTag, message) -> {
            logger.info("Receive tx message:[{}]", new String(message.getBody(), StandardCharsets.UTF_8));
        }, consumerTag -> {
        });
    }
}
```

通过wireshark抓包,可以发现使用事务channel从打开到关闭的时间差为:18ms

![image-20191130134139381](images/image-20191130134139381.png)

再对比不使用事务的情况, channel从打开到关闭的时间差为:10ms

![image-20191130134207098](images/image-20191130134207098.png)

仅仅发送两条数据的时间差都有8ms.再看使用事务的情况, 比不使用事务多了四个消息Tx.Select ->, Tx.Select-OK <-,  Tx.Commit ->, Tx.Commit <-.

对于回滚的抓包情况

```java
try {
    channel.basicPublish("", TX_QUEUE_NAME, null, "send tx message 1".getBytes(StandardCharsets.UTF_8));
    channel.basicPublish("", TX_QUEUE_NAME, null, "send tx message 2".getBytes(StandardCharsets.UTF_8));
    int x = 1 / 0;
    channel.txCommit();
} catch (Exception e) {
    channel.txRollback();
}
```

![image-20191130134235226](images/image-20191130134235226.png)

代码中先发送两条数据, 然后抛出异常再发送回滚信息.事务确实能解决生产者和broker之间消息的确认问题. 只有消息成功被broker接收,事务才能提交成功, 否则就是有回滚.但是事务机制会比不使用事务多四个步骤, 导致吞吐量有所降低.



### Confirm模式

生产者将信道(channel)设置成`confirm`, 一旦信道进入confirm模式, 所有在该信道上面发布的消息都会被指派一个唯一的ID(从1开始), 一旦消息被投送到所有匹配的队列之后, broker就会发送一个确认给生产者(包含消息的唯一ID), 这就使得生产者知道消息已经正确进入队列了, 如果消息和队列是可持久化的, 那么确认消息会将消息写入磁盘之后发出, broker回传给生产者的确认消息中deliver-tag域包含了确认消息的序列号, 此外broker也可以设置basic.ack的multiple域, 表示到这个序列号之前的所有消息已经得到处理. Confirm模式的最大好处在于他的异步.

开启confirm模式`channel.confirmSelect()`, 他有三种策略

1. 普通

   ```java
   channel.confirmSelect();
   channel.basicPublish("", CONFIRM_QUEUE, null, "confirm msg".getBytes(StandardCharsets.UTF_8));
   channel.waitForConfirmsOrDie(1000);
   ```

   和前面的示例一样发送消息, 并通过`waitForConfirmsOrDie()`方法等待对其确认. 确认消息后该方法立即返回. 如果未在超时时间内没有确认消息那么该方法将抛出错误, 通常遇到错误处理办法是记录错误信息和重试.再看wireshark的抓包数据

   ![image-20191130143015895](images/image-20191130143015895.png)

   在发送客户端发送数据后, broker会返回一个确认响应

2. 批量

   ```java
   channel.confirmSelect();
   channel.basicPublish("", CONFIRM_QUEUE, null, "cf msg1".getBytes(StandardCharsets.UTF_8));
   channel.basicPublish("", CONFIRM_QUEUE, null, "cf msg2".getBytes(StandardCharsets.UTF_8));
   channel.basicPublish("", CONFIRM_QUEUE, null, "cf msg3".getBytes(StandardCharsets.UTF_8));
   if (!channel.waitForConfirms()) {
       logger.info("msg send failed");
   }
   ```

   ![image-20191130144422489](images/image-20191130144422489.png)

   使用批量发送多条消息, 最后再接收ack. 批量对比单个消息发送极大的提高了吞吐量. 一个缺点是我们不知道发生故障是到底出了什么问题, 因此我们可能必须将整个批处理保存在内存中以记录有意义的内容或重新发布消息

3. 异步

   在一开始说过, confirm的最大优势是使用异步, 但是前面两种发送消息都是同步的.异步confirm模式的编程实现最复杂.Channel对象提供的ConfirmListener()回调方法, 提供两个参数, 一个用于消息确认, 一个用于消息未确认

   ```java
   channel.addConfirmListener((deliveryTag, multiple) -> {
       //消息确认的代码
   }, (deliveryTag, multiple) -> {
       // 消息未确认的代码
   });
   ```

   deliveryTag: 标识已经确认或未确认消息的数字

   multiple: false->确认/拒绝一条消息; true->确认/拒绝序列号较低的所有消息

   ```java
   public static void async() throws IOException, TimeoutException {
       final Connection connection = ConnectionUtils.getConnection();
       final Channel ch = connection.createChannel();
       ConcurrentNavigableMap<Long, String> outstandingConfirms = new ConcurrentSkipListMap<>();
       ConfirmCallback cleanOutstandingConfirms = (deliveryTag, multiple) -> {
           if (multiple) {
               //清除tag以下的值
               ConcurrentNavigableMap<Long, String> confirmed = outstandingConfirms.headMap(deliveryTag, true);
               confirmed.clear();
           } else {
               outstandingConfirms.remove(deliveryTag);
           }
       };
       String sendMsg = "async msg";
       //用一个容器记录需要发送的内容
       outstandingConfirms.put(ch.getNextPublishSeqNo(), sendMsg);
       ch.basicPublish("", CONFIRM_QUEUE, null, sendMsg.getBytes(StandardCharsets.UTF_8));
       ch.addConfirmListener(cleanOutstandingConfirms, (deliveryTag, multiple) -> {
           //这里只打印日志, 通常情况还会有重试
           logger.error("Message with body [{}] has been nack-ed, Sequence number: [{}], multiple:[{}]", sendMsg, deliveryTag, multiple);
           cleanOutstandingConfirms.handle(deliveryTag, multiple);
       });
       ch.close();
       connection.close();
   }
   ```

   ## RPC

   在微服务架构开发中经常会遇到如下场景:

   service_1 有实例A, B, C, seriver_2有实例D, E, F. 当service_1的一个实例(假如是A)通过MQ发送消息给service_2的一个实例(假如是D), D在处理完消息后需要将结果返回给A而不是B或C. 使用以上的模式就会非常的麻烦. 幸好RabbitMQ给我们提供了RPC这样一个模型.

   ![](https://www.rabbitmq.com/img/tutorials/python-six.png)

   - 对于RPC请求, 客户端发送一条消息, 该消息具有两个属性: `replayTo`(设置为仅为该请求创建的匿名互斥队列),和`correlationId`(设置为每个请求的唯一值)
   - Server端使用`replayTo`字段中的队列来完成工作并将带有结果的消息发送回客户端
   - 客户端收到答复消息他会检查`correlationId`, 如果匹配他才会将相应返回给程序

   ```java
   public class RpcSender {
       private static final Logger logger = LoggerFactory.getLogger(RpcSender.class);
   
       public static final String RPC_QUEUE_NAME = "rpc_queue";
   
       public static void main(String[] args) throws Exception {
           final Connection connection = ConnectionUtils.getConnection();
           final Channel channel = connection.createChannel();
   
           channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
           final String callbackQueue = channel.queueDeclare().getQueue();
   
           final String corrId = UUID.randomUUID().toString();
   
           AMQP.BasicProperties props = new AMQP.BasicProperties()
                   .builder()
                   .correlationId(corrId)
                   .replyTo(callbackQueue)
                   .build();
   
           channel.basicPublish("", RPC_QUEUE_NAME, props, "rpc".getBytes(StandardCharsets.UTF_8));
           channel.basicConsume(callbackQueue, true, (consumerTag, message) -> {
               if (message.getProperties().getCorrelationId().equals(corrId)) {
                   logger.info("callback queue:[{}] msg:[{}]", callbackQueue, new String(message.getBody(), StandardCharsets.UTF_8));
               }
           }, consumerTag -> {
           });
   
           //等待返回, 这里简单处理了
           Thread.sleep(1000);
           channel.close();
           connection.close();
       }
   }
   
   //消费者
   public class RecReceiver {
   
       private static final Logger logger = LoggerFactory.getLogger(RecReceiver.class);
   
       public static void main(String[] args) throws Exception {
           final Connection connection = ConnectionUtils.getConnection();
           final Channel channel = connection.createChannel();
   
           channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
           channel.basicQos(1);
   
           channel.basicConsume(RPC_QUEUE_NAME, false, (consumerTag, message) -> {
               AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder()
                       .correlationId(message.getProperties().getCorrelationId()).build();
               logger.info("receive msg: [{}]", new String(message.getBody(), StandardCharsets.UTF_8));
               channel.basicPublish("", message.getProperties().getReplyTo(), replyProps, "response".getBytes(StandardCharsets.UTF_8));
               channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
           }, consumerTag -> {
           });
       }
   }
   ```

   