# Quick Intro to Spring Cloud Configuration #

## Overview ##

**Spring Cloud Config** is Spring’s client / server approach for storing and serving distributed configurations across multiple applications and environments.

> **Spring Cloud Config**是一种采用服务器 / 客户端架构，提供配置文件存储和提取的形式
>
> 一般用于集中管理多个应用（和与之对应的多个环境）

This configuration store is ideally versioned under Git version control and can be modified at application runtime. While it fits very well in Spring applications using all the supported configuration file formats together with constructs like Environment, PropertySource or @Value, it can be used in any environment running any programming language.

> 这些配置文件被集中到一起，放到**Git**下面进行管理
>
> 这样集中管理配置文件的形式非常适用于基于**Spring**框架开发的App（如果使用得当，文件即环境；文件可以在程序运行时进行更改，那么环境也可以在程序运行时进行变更）

In this write-up, we’ll focus on an example of how to setup a *Git*-backed config server, use it in a simple *REST* application server and setup a secure environment including encrypted property values.

## Project Setup and Dependencies ##

To get ready for writing some code, we create two new *Maven* projects first. The server project is relying on the *spring-cloud-config-server* module, as well as the *spring-boot-starter-security* and *spring-boot-starter-web* bundles:

> 在正式写代码之前，我们先明确一下我们的依赖项
>
> 服务端的项目需要依赖于**spring-cloud-config-server**
>
> 辅助的依赖项是：**spring-boot-starter-security** & **spring-boot-starter-web**

```pom
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
    <version>1.1.2.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
    <version>1.4.0.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>1.4.0.RELEASE</version>
</dependency>
```

However for the client project we’re going to only need the *spring-cloud-starter-config* and the *spring-boot-starter-web* modules:

> 而客户端的项目需要依赖于**spring-cloud-starter-config**
>
> 辅助的依赖项是：**spring-boot-starter-web**

```pom
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
    <version>1.1.2.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>1.4.0.RELEASE</version>
</dependency>
```

所以我们的两位主角出场了：**spring-cloud-config-server** & **spring-cloud-starter-config**

## A Config Server Implementation ##

The main part of the application is a config class – more specifically a `@SpringBootApplication` – which pulls in all the required setup through the auto-configure annotation `@EnableConfigServer`:

```java
@SpringBootApplication
@EnableConfigServer
public class ConfigServer {
     
    public static void main(String[] arguments) {
        SpringApplication.run(ConfigServer.class, arguments);
    }
}
```

可以想像：一旦借助**spring-cloud-config-server**的帮助，服务器的实现并不需要很多代码

主要是一些配置工作，且这些配置工作中的绝大部分都可以用`@EnableConfigServer`注解来完成

Now we need to configure the server *port* on which our server is listening and a *Git*-url which provides our version-controlled configuration content. The latter can be used with protocols like *http*, *ssh* or a simple *file* on a local filesystem.

> 我们还缺一些什么呢？
>
> 服务器应该监听的端口
>
> 服务器应该从哪里去下载配置文件呢？换言之，*Git-url*

There are also some *placeholder variables and search patterns* for configuring the *repository-url* available; but this is beyond the scope of our article. If you are interested, the official documentation is a good place to start.

> *Git-url* 的水很深，这篇文章就没有深入下去（欢迎我们阅读官方文档）

We also need to set a username and a password for the *Basic-Authentication* in our *application.properties* to avoid an auto-generated password on every application restart:

> 我们还需要为服务器设定账户和密码，以避免密码随机初始化
>
> 在客户端会用到？

```xml
server.port=8888
spring.cloud.config.server.git.uri=ssh://localhost/config-repo
spring.cloud.config.server.git.clone-on-start=true
security.user.name=root
security.user.password=s3cr3t
```

## A Git Repository as Configuration Storage ##

To complete our server, we have to initialize a *Git* repository under the configured url, create some new properties files and popularize them with some values.

> 下一步自然是创建对应的文件夹

The name of the configuration file is composed like a normal Spring *application.properties*, but instead of the word ‘application’ a configured name, e.g. the value of the property *‘spring.application.name’* of the client is used, followed by a dash and the active profile. For example:

> 配置文件依然用*properties*结尾，不过以配置的名字开头
>
> 比如说：*a-simple-configuration.properties*

![1](1.jpg)

## Querying the Configuration ##

Now we’re able to start our server. The *Git*-backed configuration API provided by our server can be queried using the following paths:

```
/{application}/{profile}[/{label}]
/{application}-{profile}.yml
/{label}/{application}-{profile}.yml
/{application}-{profile}.properties
/{label}/{application}-{profile}.properties
```

我们的服务器提供如上所示的访问路径

In which the *{label}* placeholder refers to a Git branch, *{application}* to the client’s application name and the *{profile}* to the client’s current active application profile.

> **label**是分支名称
>
> **application**是客户端应用的名称
>
> **profile**是现在活跃的配置文件（这是什么意思？）

So we can retrieve the configuration for our planned config client running under development profile in branch *master* via:

> 示例如下：

```shell
curl http://root:s3cr3t@localhost:8888/config-client/development/master
```

前面配置的用户名和密码果然是留给客户端使用的

我们不妨化简一下路径：

```
/{application}/{profile}/master
profile = development or production
```

只使用这种最简单也最明了的路径

## The Client Implementation ##

Next, let’s take care of the client. This will be a very simple client application, consisting of a *REST* controller with one *GET* method.

The configuration, to fetch our server, must be placed in a resource file named *bootstrap.application*, because this file (like the name implies) will be loaded very early while the application starts:

> 把有关配置服务器的配置放到何处呢？**boostrap.application**
>
> 作者是不是写错了啊？应该是 **boostrap.properties** ？
>
> 这个名字（ boostrap ）暗示我们它会在应用启动之前被载入

```java
@SpringBootApplication
@RestController
public class ConfigClient {
     
    @Value("${user.role}")
    private String role;
 
    public static void main(String[] args) {
        SpringApplication.run(ConfigClient.class, args);
    }
 
    @RequestMapping(
      value = "/whoami/{username}", 
      method = RequestMethod.GET, 
      produces = MediaType.TEXT_PLAIN_VALUE)
    public String whoami(@PathVariable("username") String username) {
        return String.format("Hello! 
          You're %s and you'll become a(n) %s...\n", username, role);
    }
}
```

这段 Java 程序本身没有探讨的价值，我们关心的是`@Value("${user.role}")`能起作用吗？

In addition to the application name, we also put the active profile and the connection-details in our b*ootstrap.properties*:

```properties
spring.application.name=config-client
spring.profiles.active=development
spring.cloud.config.uri=http://localhost:8888
spring.cloud.config.username=root
spring.cloud.config.password=s3cr3t
```

To test, if the configuration is properly received from our server and the *role value*gets injected in our controller method, we simply curl it after booting the client:

> 如何测试我们的服务器和客户端是否正常工作呢？

```shell
curl http://localhost:8080/whoami/Mr_Pink
```

![2](2.jpg)

## 其余 ##

其余的部分关注在配置文件的加密传输：提供了对称加密和非对称加密两种方式

我们关注与依赖项的功能而不关注如何加密，所以这部分内容我们略过

更多的内容请关注[原文章](Quick Intro to Spring Cloud Configuration | Baeldung.pdf)

# GitHub READMD #

Spring Cloud Config provides server and client-side support for externalized configuration in a distributed system. With the Config Server you have a central place to manage external properties for applications across all environments. The concepts on both client and server map identically to the Spring `Environment` and `PropertySource` abstractions, so they fit very well with Spring applications, but can be used with any application running in any language. As an application moves through the deployment pipeline from dev to test and into production you can manage the configuration between those environments and be certain that applications have everything they need to run when they migrate. The default implementation of the server storage backend uses git so it easily supports labelled versions of configuration environments, as well as being accessible to a wide range of tooling for managing the content. It is easy to add alternative implementations and plug them in with Spring configuration.

# 总结 #

**spring-cloud-starter-config**依赖项是为了集中管理配置文件而存在的，在**MicroServiceDemo**项目中，有两种做法：

+ 用一个容器集中管理配置文件（添加**spring-cloud-starter-config**依赖项）
+ 每个应用有自己单独的配置文件（不添加**spring-cloud-starter-config**依赖项）

需要说明的是，在使用**spring-cloud-dependencies**依赖项之后，诸如**spring-cloud-starter-config**的依赖项都不需要特别标注版本

![3](3.jpg)

![4](4.jpg)