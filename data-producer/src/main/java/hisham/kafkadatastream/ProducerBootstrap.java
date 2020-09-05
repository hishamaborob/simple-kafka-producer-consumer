package hisham.kafkadatastream;

import hisham.kafkadatastream.service.KafkaCpuLoadDataPublisherService;
import hisham.kafkadatastream.service.PublisherService;
import hisham.kafkadatastream.task.CpuLoadDataCollectionTask;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.log4j.Logger;

import java.net.InetAddress;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProducerBootstrap {

    private static final Logger LOGGER = Logger.getLogger(ProducerBootstrap.class);

    public static void main(String[] args) throws Exception {
        try {
            Producer<String, String> producer = createProducer();
            final String serviceId = InetAddress.getLocalHost().getHostAddress();
            PublisherService<Double> publisherService = new KafkaCpuLoadDataPublisherService(
                    producer, serviceId, System.getProperty("topic")
            );
            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            executor.scheduleAtFixedRate(new CpuLoadDataCollectionTask(publisherService), 5, 5, TimeUnit.SECONDS);

            Runtime.getRuntime().addShutdownHook(new Thread(executor::shutdownNow));
            Runtime.getRuntime().addShutdownHook(new Thread(producer::close));
        } catch (Exception e) {
            LOGGER.error("One or more system properties are missing. Or something went wrong");
            LOGGER.error(e);
            System.exit(1);
        }
    }

    // TODO: replace properties keys with constants.
    private static Producer<String, String> createProducer() {

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
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(props);
    }
}
