## CPU Load Data Pipeline
### Simple Kafka producer and consumer

##### Overview

- A simple Java 8 maven project with two modules.
- Producer module collects information about CPU load and pushes to a Kafka topic.
- Consumer module consumes data about CPU load from Kafka topic.
- Saving data into Postgresql is not implemented. Please check comments in the code.
- There isn't many features to be tested here. Only basic test is provided for some classes.

####Build and Run

App configuration:
```
-Dtopic="xxx"
-DkafkaHost="localhost:20797"
-DtruststoreAbsolutePath="/home/xxx/client.truststore.jks"
-DtruststorePassword="xxx"
-DkeystoreAbsolutePath="/home/xxx/client.keystore.p12"
-DkeystorePassword="xxx"
-DsslKeyPassword="xxx"
```

In the parent directory:
```
mvn clean package assembly:single
```

Run producer:
```
java -jar -Dtopic="xxx" -DkafkaHost="localhost:20797" -DtruststoreAbsolutePath="/home/xxx/client.truststore.jks" -DtruststorePassword="xxx" -DkeystoreAbsolutePath="/home/xxx/client.keystore.p12" -DkeystorePassword="xxx" -DsslKeyPassword="xxx" data-producer/target/data-producer-1.0-jar-with-dependencies.jar
```

Run consumer:
```
java -jar -Dtopic="xxx" -DkafkaHost="localhost:20797" -DtruststoreAbsolutePath="/home/xxx/client.truststore.jks" -DtruststorePassword="xxx" -DkeystoreAbsolutePath="/home/xxx/client.keystore.p12" -DkeystorePassword="xxx" -DsslKeyPassword="xxx" data-consumer/target/data-consumer-1.0-jar-with-dependencies.jar
```
