package br.com.bueno.events.kafkademo.kafka.producers;

import br.com.bueno.logistics.events.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class OrderCreatedProducer {

    private static final Logger LOG = LoggerFactory.getLogger(OrderCreatedProducer.class);

    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    public void sendMessage(String topic, Order message) {
        LOG.info("Sending to topic={{}} payload={{}} ", topic, message);

        ListenableFuture<SendResult<String, Order>> future = kafkaTemplate.send(topic, message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Order>>() {

            @Override
            public void onSuccess(SendResult<String, Order> message) {
                OrderCreatedProducer.LOG.info("Message send with success to topic {{}}", topic);
            }

            @Override
            public void onFailure(Throwable throwable) {
                OrderCreatedProducer.LOG.error("Unable to send message= " + message, throwable);
            }
        });
    }
}
