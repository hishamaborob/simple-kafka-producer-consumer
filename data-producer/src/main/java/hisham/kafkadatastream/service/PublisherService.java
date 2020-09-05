package hisham.kafkadatastream.service;

public interface PublisherService<E> {

    void send(E data);
}
