package br.com.bueno.events.kafkademo.kafka.consumers;

import br.com.bueno.logistics.events.DefinedProduct;
import br.com.bueno.logistics.events.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "${kafka.topic.order_created}")
    public void receive(Order order) {
        log.info("received order={{}}", order);
    }

    @KafkaListener(topics = "${kafka.topic.product_defined}")
    public void receive(DefinedProduct definedProduct) {
        log.info("received product={}", definedProduct);
    }
}
