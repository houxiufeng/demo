package com.example.demo.service;

public interface KafkaService {
    void send(String topic, String msg);
}
