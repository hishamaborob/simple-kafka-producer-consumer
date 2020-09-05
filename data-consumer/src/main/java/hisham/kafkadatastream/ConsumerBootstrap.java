package hisham.kafkadatastream;

import hisham.kafkadatastream.service.ConsumerService;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConsumerBootstrap {

    private static final Logger LOGGER = Logger.getLogger(ConsumerBootstrap.class);

    public static void main(String[] args) {

        try {
            Consumer<String, String> consumer = createConsumer();
            ConsumerService consumerService = new ConsumerService(consumer);

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(consumerService);
            Runtime.getRuntime().addShutdownHook(new Thread(consumerService::stop));
            executorService.shutdown();
        } catch (Exception e) {
            LOGGER.error("One or more system properties are missing. Or something went wrong");
            LOGGER.error(e);
            System.exit(1);
        }
    }

    // TODO: replace properties keys with constants.
    public static Consumer<String, String> createConsumer() {

        Properties props = new Properties();
        props.put("bootstrap.servers", System.getProperty("kafkaHost"));
        props.put("security.protocol", "SSL");
        props.put("ssl.endpoint.identification.algorithm", "");
        props.put("ssl.truststore.location", System.getProperty("truststoreAbsolutePath"));
        props.put("ssl.truststore.password", System.getProperty("truststorePassword"));
        props.put("ssl.keystore.type", "PKCS12");
        props.put("ssl.keystore.location", System.getProperty("keystoreAbsolutePath"));
        props.put("ssl.keystore.password", System.getProperty("keystorePassword"));
        props.put("ssl.key.password", System.getProperty("sslKeyPassword"));
        props.put("group.id", "random-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        Consumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(System.getProperty("topic")));
        return consumer;
    }
}
