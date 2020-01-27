package br.com.bueno.events.kafkademo.controller;

import br.com.bueno.events.kafkademo.kafka.producers.OrderCreatedProducer;
import br.com.bueno.logistics.events.Address;
import br.com.bueno.logistics.events.Order;
import br.com.bueno.logistics.events.OrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class ControllerMessage {

    private static final Logger log = LoggerFactory.getLogger(ControllerMessage.class);

    @Autowired
    private OrderCreatedProducer orderCreatedProducer;

    @Value("${kafka.topic.order_created}")
    private String topic;

    @GetMapping("/order")
    public ResponseEntity<String> kafkaProducerDemo() {
        log.info("Send message");
        Order order = Order.newBuilder()
                .setOrderId(1L)
                .setCompany("TETD")
                .setOrderType("A")
                .setAddress(Address.newBuilder()
                        .setCity("Araraquara")
                        .setFederationUnit("SP")
                        .build())
                .setItems(Arrays.asList(
                        OrderItem.newBuilder()
                                .setItemId(20L)
                                .setCodeSap("950151")
                                .setNetworkOperator("VIVO")
                                .setIdtMobireader(12)
                                .build(),
                        OrderItem.newBuilder()
                                .setItemId(10L)
                                .setCodeSap("900442")
                                .setNetworkOperator("CLARO")
                                .setIdtMobireader(11)
                                .build()))
                .build();
        orderCreatedProducer.sendMessage(topic, order);
        return ResponseEntity.ok().body(order.toString());
    }


}
