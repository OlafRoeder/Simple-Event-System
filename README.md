# Simple-Event-System

A simple event system for Java. See EventDemo for examples.

Basic usage:

```java
SimpleEventSystem simpleEventSystem = new SimpleEventSystem();

simpleEventSystem.addEventRunnable(SomeEventType .class, ()-> doStuff());
simpleEventSystem.publishEvent(new SomeEventType());
```

[!] This event system is meant to be a very simple, yet functioning implementation, but it is probably not useful in
production. If you are looking for a production ready event system, I recommend
either https://github.com/greenrobot/EventBus or https://github.com/ReactiveX/RxJava, depending on your needs.