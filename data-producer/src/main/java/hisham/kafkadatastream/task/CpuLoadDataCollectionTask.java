package hisham.kafkadatastream.task;

import hisham.kafkadatastream.service.PublisherService;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

public class CpuLoadDataCollectionTask implements Runnable {

    private OperatingSystemMXBean operatingSystemMXBean;
    private PublisherService<Double> publisherService;

    public CpuLoadDataCollectionTask(PublisherService publisherService) {

        this.publisherService = publisherService;
        this.operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
    }

    @Override
    public void run() {

        // TODO: for more accuracy send the current time.
        publisherService.send(operatingSystemMXBean.getSystemLoadAverage());
    }
}
