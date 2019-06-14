FROM openjdk:8-jre-alpine

# Don't run as root
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Set the working directory
WORKDIR /usr/local/spring-boot

# Copy the required files
ARG APP_JAR
COPY --chown=appuser:appgroup target/${APP_JAR} ./application.jar
COPY --chown=appuser:appgroup src/bin/start.sh ./

# Make all files only readable by the 'appuser' user and 'appgroup' group
# Make start.sh executable
RUN chmod 440 ./application.jar && \
    chmod 550 ./start.sh

# Make the standard Tomcat, debugging and JMX ports available
EXPOSE 8080 8000 9090 9091

CMD ["./start.sh"]