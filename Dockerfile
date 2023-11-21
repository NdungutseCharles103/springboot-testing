# Use a Maven image to build and package the application
FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /app
COPY . .
# clean package and skip tests
RUN mvn clean package -DskipTests

#pull cockroachdb cert
RUN curl --create-dirs -o $HOME/.postgresql/root.crt 'https://cockroachlabs.cloud/clusters/73a60cd8-555c-41a3-8c5a-5efa30628deb/cert'
RUN curl --create-dirs -o /root/.postgresql/root.crt 'https://cockroachlabs.cloud/clusters/73a60cd8-555c-41a3-8c5a-5efa30628deb/cert'
RUN curl --create-dirs -o /etc/ssl/certs/root.crt 'https://cockroachlabs.cloud/clusters/73a60cd8-555c-41a3-8c5a-5efa30628deb/cert'

# Create the final image with the packaged JAR
FROM openjdk:17
WORKDIR /app
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
ENV JDBC_DATABASE_URL=${JDBC_DATABASE_URL}
ENV JDBC_DATABASE_USERNAME=${JDBC_DATABASE_USERNAME}
ENV JDBC_DATABASE_PASSWORD=${JDBC_DATABASE_PASSWORD}
# Copy the packaged JAR
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar","--spring.profiles.active=prod"]