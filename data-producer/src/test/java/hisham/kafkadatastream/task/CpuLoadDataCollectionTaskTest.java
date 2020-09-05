package hisham.kafkadatastream.task;

import hisham.kafkadatastream.service.PublisherService;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CpuLoadDataCollectionTaskTest {

    @Mock
    private PublisherService<Double> publisherService;

    @Test
    public void testCpuDataCollection() {

        initMocks(this);
        doNothing().when(publisherService).send(anyDouble());
        Runnable cpuLoadDataCollectionTask = new CpuLoadDataCollectionTask(publisherService);
        cpuLoadDataCollectionTask.run();
        // Verify that the scheduled task ran once and a double value (current cpu load) was published
        verify(publisherService, times(1)).send(anyDouble());
    }
}