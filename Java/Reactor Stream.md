# Reactor Stream

四种角色

1. Publisher: 发布者
2. Subscriber: 订阅者
3. Subscription: 订阅控制器
4. Processor: 处理器 发布者|订阅者



Reactive Streams实现:

- Java 9 Flow API
- RxJava: Reactive Extension Java
- Reactor: Reactor Framework



Mono: 异步0-1元素序列, Future<Optional<?>>

Flux: 异步0-N元素序列, Furute<Collection<?>>