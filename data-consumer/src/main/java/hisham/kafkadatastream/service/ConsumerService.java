package hisham.kafkadatastream.service;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.log4j.Logger;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.String.format;

/**
 * No test for this class.
 * Ideally, I would have provided a mock or a test consumer connected to embedded broker
 * or something similar, to verify that records has be consumed and passes into the other
 * service for processing and persisting correctly.
 */
public class ConsumerService implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(ConsumerService.class);

    private Consumer<String, String> consumer;
    private AtomicBoolean running = new AtomicBoolean(true);

    public ConsumerService(Consumer<String, String> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void run() {

        while (running.get()) {

            try {
                ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(5000));

                consumerRecords.forEach(record -> {
                    LOGGER.info(format("Received data %s %s", record.key(), record.value()));
                });
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        LOGGER.info("Consumer polling stopped");
    }

    public void stop() {

        running.compareAndSet(true, false);
    }
}
