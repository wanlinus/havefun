# Spring Security

## HTTP Basic Auth

**项目gradle**

```groovy
plugins {
    id 'org.springframework.boot' version '2.2.2.RELEASE'
    id 'io.spring.dependency-management' version '1.0.8.RELEASE'
    id 'java'
}

group = 'cn.wanli'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    testImplementation('org.springframework.boot:spring-boot-starter-test') {
        exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
    }
    testImplementation 'org.springframework.security:spring-security-test'
}

test {
    useJUnitPlatform()
}

```

```java
@RestController
@SpringBootApplication
public class SecurityDemoApplication {

    @GetMapping
    public String str(){
        return "hello spring security";
    }

    public static void main(String[] args) {
        SpringApplication.run(SecurityDemoApplication.class, args);
    }

}
```

启动项目默认用户名为`user`, 密码在终端中显示

自定义用户名和密码在配置文件中设置 application.yaml

```yaml
spring:
  security:
    user:
      name: wanli
      password: 123456
```

## Form 表单认证

**自定义表单登陆页**

