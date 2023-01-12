# Simple-Event-System
A simple event system for Java. See EventDemo for examples.

Basic usage:

```java
SimpleEventSystem simpleEventSystem = new SimpleEventSystem();
simpleEventSystem.addEventRunnable(SomeEventType.class, () -> doStuff());
simpleEventSystem.publishEvent(new SomeEventType());
```
