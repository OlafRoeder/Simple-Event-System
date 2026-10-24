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

## Build requirements

Build with a stable JDK 27 and the checked-in Gradle wrapper. Set JAVA_HOME to JDK
27; select the same Gradle JVM in the IDE. The daemon and compiler toolchains are
pinned to Java 27. Library classes retain their Java 11 bytecode baseline; JUnit
6 tests run on Java 27.

```sh
./gradlew clean build --no-daemon
```

The build uses Gradle 9.8.2, FreeFair Lombok 9.8.2, Lombok 1.18.48, SLF4J 2.0.20,
Log4j 2.26.1, JUnit 6.1.3, and Awaitility 4.3.0. Lombok 1.18.48 adds JDK 27
compiler support: https://projectlombok.org/changelog . Gradle's Java compatibility
matrix is at https://docs.gradle.org/current/userguide/compatibility.html .

The project version matches the existing 0.2 source-dependency tag. Consumers of
published Git tags need a new release tag before they receive these build fixes.
