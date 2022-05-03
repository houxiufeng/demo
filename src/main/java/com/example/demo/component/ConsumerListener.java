package com.example.demo.component;

import com.example.demo.service.ESUserService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * 消费者1（监听topic1队列）
 */
@Component
public class ConsumerListener {

    private static Logger logger = LoggerFactory.getLogger(ConsumerListener.class);

    @KafkaListener(topics = "${spring.kafka.producer.myTopic2}")
    public void listen(ConsumerRecord<?, String> record) {
        System.out.println(record);
        String value = record.value();
        System.out.println("消费者1收到消息:"+ value);
        logger.info("消费者1收到消息:"+ value);
    }

    /**
     * 跟上面的区别是
     * ack手动提交后，才能把消费者offset同步到zk中，不然每次重启后，还是从第一次的offset开始消费，每次会重复消费之前的数据.
     * @param record
     * @param ack
     */
    @KafkaListener(topics = "${spring.kafka.producer.myTopic1}")
    public void listen2(ConsumerRecord<?, String> record, Acknowledgment ack) {
        System.out.println(record);
        String value = record.value();
        System.out.println("消费者1收到消息:"+ value);
        logger.info("消费者1收到消息:"+ value);
        ack.acknowledge();
    }

}
