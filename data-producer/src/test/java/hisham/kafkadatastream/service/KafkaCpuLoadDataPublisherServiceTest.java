package hisham.kafkadatastream.service;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class KafkaCpuLoadDataPublisherServiceTest {

    @Mock
    private Producer<String, String> producer;
    @Captor
    ArgumentCaptor<ProducerRecord<String, String>> argumentCaptor;

    @Test
    public void testSend() {

        initMocks(this);
        final String testKey = "testKey";
        final double testValue = 1.2;
        final String testTopic = "testTopic";
        PublisherService<Double> publisherService = new KafkaCpuLoadDataPublisherService(producer, testKey, testTopic);

        ProducerRecord<String, String> record = new ProducerRecord<>(testTopic, testKey, String.valueOf(testValue));
        when(producer.send(any())).thenReturn(null);
        publisherService.send(testValue);
        verify(producer).send(argumentCaptor.capture());
        ProducerRecord<String, String> capturedRecord = argumentCaptor.getValue();
        // Verify that the record was constructed properly.
        assertTrue(capturedRecord.equals(record));
    }
}