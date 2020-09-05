package hisham.kafkadatastream.service;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;

import static java.lang.String.format;

public class KafkaCpuLoadDataPublisherService implements PublisherService<Double> {

    private static final Logger LOGGER = Logger.getLogger(KafkaCpuLoadDataPublisherService.class);

    private Producer<String, String> producer;
    private String serviceId;
    private String topic;

    public KafkaCpuLoadDataPublisherService(Producer<String, String> producer, String serviceId, String topic) {

        this.producer = producer;
        this.serviceId = serviceId;
        this.topic = topic;
    }

    @Override
    public void send(Double data) {

        ProducerRecord<String, String> record = new ProducerRecord<>(topic, serviceId, data.toString());
        LOGGER.info(format("Sending record %s", record.toString()));
        producer.send(record);
    }
}
