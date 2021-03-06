package com.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class SimpleProducer_for {
  private final static Logger logger = LoggerFactory.getLogger(SimpleProducer.class);
  private final static String TOPIC_NAME = "test";
  private final static String BOOTSTRAP_SERVERS = "{your public_ip}:9092";

  public static void main(String[] args) {
    Properties configs = new Properties();
    configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
    configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

    KafkaProducer<String, String> producer = new KafkaProducer<>(configs);


    for (int index = 0; index < 10; index++) {
      String data = "This is record " + index;
      ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, data);
      try {
        producer.send(record);
        System.out.println("Send to " + TOPIC_NAME + " | data : " + data);
        Thread.sleep(1000);
      } catch (Exception e) {
        System.out.println(e);
      }
    }
  }
}
