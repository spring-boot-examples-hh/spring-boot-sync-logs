package com.roylao.common;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @description Kafka消息提供者类
 * @author roylao
 * @create 2020-3-15 17:03:19
 */
@Component
@Slf4j
public class KafkaSender<T> {

    @Value("${logstash.log.topic: logstash_log}")
    private String log_topic;

    @Autowired(required = false)
    private KafkaTemplate<String, Object> kafkaTemplate;


    /**
     * kafka 发送消息
     *
     * @param obj 消息对象
     */

    public void send(T obj) {
        String jsonObj = JSON.toJSONString(obj);

//        org.apache.kafka.clients.producer.ProducerRecord
        // 发送消息
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(log_topic, jsonObj);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

            @Override
            public void onFailure(Throwable throwable) {
                log.info("Produce: The message failed to be sent:" + throwable.getMessage());
            }


            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                // TODO 业务处理
                log.info("Produce: The message was sent successfully:");
                log.info("Produce: >>>>>>>>>>>>>>>>>>> result: " + stringObjectSendResult.toString());
            }
        });
    }
}
