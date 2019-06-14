# Docker Java Microservice Starter

This project is a template for creating a Docker-based microservices using OpenJDK and Spring Boot.

## Building

The standard Maven goal `package` creates a container image for the service.

```
$ mvn package
```

## Running

Run with ...

```
$ docker run -p 8080:8080 local/com.github.grantjforrester/docker-java-uservice-starter:<version>
```

## Exposed Ports

- `8000`:  Standard port available for remote Java debugging when enabled.
- `8080`:  Exposed application service endpoints.
- `9090`:  Recommended port available for JMX remote management if required.

## Enable HTTPS/TLS in application

Users can enable HTTPS in the service by specifying Docker environment variables to set the following Spring configuration properties.

```yaml
server:
  ssl:
    key-store: file:/run/secrets/tls/starter-keystore.jks
    key-store-password: password
    key-store-type: JKS
    key-alias: gateway
    ciphers: TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384,TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384,TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256,TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384,TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384,TLS_ECDH_RSA_WITH_AES_256_CBC_SHA,TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256,TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256,TLS_ECDH_RSA_WITH_AES_128_CBC_SHAßß
    enabled-protocols: TLSv1.2
```

See the [Spring User Guide on Externalized Configuration](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-config-relaxed-binding) for information on setting Spring configuration properties using environment variables.

## Other Environment Variables

Other supported Docker environment variables are:

- `JRE_OPTS`: Options that will be passed into the Java command line e.g. `java $JRE_OPTS -jar application.jar`.

## Debugging

Run a bash shell in the container with

```
$ docker run -it local/com.github.grantjforrester/docker-java-uservice-starter /bin/ash
```

Run the container with remote debugging enabled with

```
$ export JDWP_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n"
$ docker run \
    -p 8080:8080 -p 8000:8000 \
    -e JRE_OPTS="$JDWP_OPTS" \
    local/com.github.grantjforrester/docker-java-uservice-starter:<version>
```

## Monitoring and Management

Spring Actuator endpoints are enabled

- `/actuator/health` (no authentication required)
- `/actuator/info` (authentication required)

Enable management through Java Management eXtensions (JMX) with

```
$ export JMX_OPTS="-Dcom.sun.management.jmxremote \
     -Dcom.sun.management.jmxremote.port=9090 \
     -Dcom.sun.management.jmxremote.rmi.port=9090 \
     -Dcom.sun.management.jmxremote.local.only=false \
     -Dcom.sun.management.jmxremote.authenticate=false \
     -Dcom.sun.management.jmxremote.ssl=false\
     -Djava.rmi.server.hostname=<hostname or ipaddress>"
$ docker run \
    -p 8080:8080 -p 9090:9090 \
    -e JRE_OPTS="$JMX_OPTS" \
    local/com.github.grantjforrester/docker-java-uservice-starter:<version>
```
